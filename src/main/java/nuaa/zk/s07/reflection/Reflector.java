package nuaa.zk.s07.reflection;

import nuaa.zk.s07.reflection.invoke.Invoker;
import nuaa.zk.s07.reflection.invoke.MethodInvoker;
import nuaa.zk.s07.reflection.invoke.SetFieldInvoker;
import nuaa.zk.s07.reflection.property.PropertyNamer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ReflectPermission;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/25 21:26
 */
public class Reflector {
    private static boolean classCacheEnabled = true;
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    //缓存  配合classCacheEnabled使用
    private static final Map<Class<?>, Reflector> REFLECTOR_MAP = new ConcurrentHashMap<>();
    private Class<?> type;
    //拥有get方法的属性列表
    private String[] readablePropertyNames = EMPTY_STRING_ARRAY;
    //拥有set方法的属性列表
    private String[] writeablePropertyNames = EMPTY_STRING_ARRAY;

    //类中的get方法列表
    private Map<String, Invoker> getMethods = new HashMap<>();
    //类中的set方法列表
    private Map<String, Invoker> setMethods = new HashMap<>();
    //get方法的返回值类型列表
    private Map<String, Class<?>> getTypes = new HashMap<>();
    //set方法的参数类型列表
    private Map<String, Class<?>> setTypes = new HashMap<>();

    //默认构造器
    private Constructor<?> defaultConstructor;
    //get和set方法的并集
    private Map<String, String> caseInsensitivePropertyMap = new HashMap<>();

    public Reflector(Class<?> clazz) {
        this.type = clazz;
        //获取默认构造器
        addDefaultConstructor(clazz);
        //获取get方法列表 并处理 与get方法相关的属性
        addGetMethods(clazz);
        //获取set方法列表 同上
        addSetMethods(clazz);
        //获取field字段
//        addFields(clazz);
        readablePropertyNames = getMethods.keySet().toArray(new String[getMethods.keySet().size()]);
        writeablePropertyNames = setMethods.keySet().toArray(new String[setMethods.keySet().size()]);
        for (String readablePropertyName : readablePropertyNames) {
            caseInsensitivePropertyMap.put(readablePropertyName.toUpperCase(Locale.ENGLISH), readablePropertyName);
        }

        for (String writeablePropertyName : writeablePropertyNames) {
            caseInsensitivePropertyMap.put(writeablePropertyName.toUpperCase(Locale.ENGLISH), writeablePropertyName);
        }
    }

    private void addSetMethods(Class<?> clazz) {
        Map<String,List<Method>> conflictingSetters = new HashMap<>();
        Method[] methods = getClassMethods(clazz);
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && methodName.length() > 3){
                if (method.getParameterCount() == 1){
                    methodName = PropertyNamer.methodToProperty(methodName);
                    addMethodConflict(conflictingSetters,methodName,method);
                }
            }
        }
        resolveSetterConflicts(conflictingSetters);
    }

    private void addGetMethods(Class<?> clazz) {
        Map<String, List<Method>> conflictingGetters = new HashMap<>();
        Method[] methods = getClassMethods(clazz);
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && methodName.length() > 3) {
                if (method.getParameterCount() == 0) {
                    methodName = PropertyNamer.methodToProperty(methodName);
                    addMethodConflict(conflictingGetters, methodName, method);
                }
            } else if (methodName.startsWith("is") && methodName.length() > 2) {
                if (method.getParameterCount() == 0) {
                    methodName = PropertyNamer.methodToProperty(methodName);
                    addMethodConflict(conflictingGetters, methodName, method);
                }
            }
        }
        //找出真正属于该类的方法(主要对于父子类中的方法重写  参数扩大 返回值缩小)
        resolveGetterConflicts(conflictingGetters);
    }
    private void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
        for (String propName : conflictingSetters.keySet()) {
            List<Method> setters = conflictingSetters.get(propName);
            Method firstMethod = setters.get(0);
            if (setters.size() == 1) {
                addSetMethod(propName, firstMethod);
            } else {
                Class<?> expectedType = getTypes.get(propName);
                if (expectedType == null) {
                    throw new RuntimeException("Illegal overloaded setter method with ambiguous type for property "
                            + propName + " in class " + firstMethod.getDeclaringClass() + ".  This breaks the JavaBeans " +
                            "specification and can cause unpredicatble results.");
                } else {
                    Iterator<Method> methods = setters.iterator();
                    Method setter = null;
                    while (methods.hasNext()) {
                        Method method = methods.next();
                        if (method.getParameterTypes().length == 1
                                && expectedType.equals(method.getParameterTypes()[0])) {
                            setter = method;
                            break;
                        }
                    }
                    if (setter == null) {
                        throw new RuntimeException("Illegal overloaded setter method with ambiguous type for property "
                                + propName + " in class " + firstMethod.getDeclaringClass() + ".  This breaks the JavaBeans " +
                                "specification and can cause unpredicatble results.");
                    }
                    addSetMethod(propName, setter);
                }
            }
        }
    }

    private void addSetMethod(String propName, Method method) {
        if (isValidPropertyName(propName)){
            setMethods.put(propName, new MethodInvoker(method));
            setTypes.put(propName,method.getParameterTypes()[0]);
        }
    }


    private void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters) {
        for (String propName : conflictingGetters.keySet()) {
            List<Method> getters = conflictingGetters.get(propName);
            Iterator<Method> iterator = getters.iterator();
            Method firstMethod = iterator.next();
            if (getters.size() == 1) {
                //无同名方法
                addGetMethod(propName, firstMethod);
            } else {
                Method getter = firstMethod;
                //有重名方法
                Class<?> getterType = firstMethod.getReturnType();
                while (iterator.hasNext()) {
                    Method method = iterator.next();
                    Class<?> methodType = method.getReturnType();
                    //不合法
                    if (methodType.equals(getterType)) {
                        throw new RuntimeException("Illegal overloaded getter method with ambiguous type for property "
                                + propName + " in class " + firstMethod.getDeclaringClass()
                                + ".  This breaks the JavaBeans " + "specification and can cause unpredicatble results.");
                    } else if (methodType.isAssignableFrom(getterType)) {
                        //返回值是子类 可以
                        // OK getter type is descendant
                    } else if (getterType.isAssignableFrom(methodType)) {
                        //子类(当前类)中的重写方法应该是getter
                        getter = method;
                        getterType = methodType;
                    } else {
                        throw new RuntimeException("Illegal overloaded getter method with ambiguous type for property "
                                + propName + " in class " + firstMethod.getDeclaringClass()
                                + ".  This breaks the JavaBeans " + "specification and can cause unpredicatble results.");
                    }
                }
                addGetMethod(propName, getter);
            }
        }
    }

    private void addGetMethod(String name, Method method) {
        if (isValidPropertyName(name)) {
            getMethods.put(name, new MethodInvoker(method));
            getTypes.put(name, method.getReturnType());
        }
    }

    private void addMethodConflict(Map<String, List<Method>> conflictingGetters, String methodName, Method method) {
        List<Method> list = conflictingGetters.computeIfAbsent(methodName, k -> new ArrayList<>());
        list.add(method);
    }

    private Method[] getClassMethods(Class<?> clazz) {
        Map<String, Method> uniqueMethods = new HashMap<String, Method>();
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            //当前类中方法
            addUniqueMethods(uniqueMethods, clazz.getDeclaredMethods());

            //当前类实现的接口中的方法
            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                addUniqueMethods(uniqueMethods, anInterface.getDeclaredMethods());
            }
            //向上查找父类
            currentClass = currentClass.getSuperclass();
        }
        Collection<Method> methods = uniqueMethods.values();
        //返回当前类查找到的所有方法
        return methods.toArray(new Method[methods.size()]);
    }

    private void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods) {
        for (Method currentMethod : methods) {
            //不需要桥方法
            if (!currentMethod.isBridge()) {
                String signature = getSignature(currentMethod);
                if (!uniqueMethods.containsKey(signature)) {
                    if (canAccessPrivateMethods()) {
                        try {
                            currentMethod.setAccessible(true);
                        } catch (Exception e) {
                            // Ignored. This is only a final precaution, nothing we can do.
                        }
                    }
                    uniqueMethods.put(signature, currentMethod);
                }
            }
        }
    }

    private String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            sb.append(returnType.getName()).append("#");
        }
        sb.append(method.getName());
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (i == 0) {
                sb.append(":");
            } else {
                sb.append(",");
            }
            sb.append(parameters[i].getName());
        }
        return sb.toString();
    }

    private void addDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            if (declaredConstructor.getParameterCount() == 0) {
                if (canAccessPrivateMethods()) {
                    try {
                        declaredConstructor.setAccessible(true);
                    } catch (Exception ignore) {
                        // Ignored. This is only a final precaution, nothing we can do
                    }
                }
                this.defaultConstructor = declaredConstructor;
            }
        }
    }

    private static boolean canAccessPrivateMethods() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }
        return true;
    }

    public Constructor<?> getDefaultConstructor() {
        if (this.defaultConstructor != null) {
            return defaultConstructor;
        } else {
            throw new RuntimeException("There is no default constructor for " + type);
        }
    }

    public boolean hasDefaultConstructor() {
        return this.defaultConstructor != null;
    }

    private boolean isValidPropertyName(String name) {
        //不是由代理类产生  不是Object的getclass方法 或者获取序列化Id的方法
        return !(name.startsWith("$") || "serialVersionUID".equals(name) || "class".equals(name));
    }

    public boolean hasGetter(String name){
        return this.getMethods.containsKey(name);
    }
    public String[] getGetablePropertyNames() {
        return readablePropertyNames;
    }

    public boolean hasSetter(String name){
        return setMethods.containsKey(name);
    }

    public String[] getSetablePropertyNames(){
        return writeablePropertyNames;
    }

    public Map<String, String> getCaseInsensitivePropertyMap() {
        return caseInsensitivePropertyMap;
    }
}
