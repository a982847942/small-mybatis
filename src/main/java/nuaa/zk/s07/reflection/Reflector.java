package nuaa.zk.s07.reflection;

import nuaa.zk.s07.reflection.invoke.Invoker;

import java.lang.reflect.Constructor;
import java.lang.reflect.ReflectPermission;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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
    private Map<String,String> caseInsensitivePropertyMap = new HashMap<>();

    public Reflector(Class<?> clazz) {
        this.type = clazz;
        //获取默认构造器
        addDefaultConstructor(clazz);
        //获取get方法列表 并处理 与get方法相关的属性
//        addGetMethods(clazz);
        //获取set方法列表 同上
//        addSetMethods(clazz);
        //获取field字段
//        addFields(clazz);
        readablePropertyNames = getMethods.keySet().toArray(new String[getMethods.keySet().size()]);
        writeablePropertyNames = setMethods.keySet().toArray(new String[setMethods.keySet().size()]);
        for (String readablePropertyName : readablePropertyNames) {
            caseInsensitivePropertyMap.put(readablePropertyName.toUpperCase(Locale.ENGLISH),readablePropertyName);
        }

        for (String writeablePropertyName : writeablePropertyNames) {
            caseInsensitivePropertyMap.put(writeablePropertyName.toUpperCase(Locale.ENGLISH),writeablePropertyName);
        }
    }
    private void addDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            if (declaredConstructor.getParameterCount() == 0){
                if (canAccessPrivateMethods()) {
                    try {
                        declaredConstructor.setAccessible(true);
                    }catch (Exception ignore) {
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
        if (this.defaultConstructor != null){
            return defaultConstructor;
        } else {
            throw new RuntimeException("There is no default constructor for " + type);
        }
    }
    public boolean hasDefaultConstructor(){
        return this.defaultConstructor != null;
    }
}
