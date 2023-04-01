package nuaa.zk.s09.reflection;

import nuaa.zk.s09.reflection.invoke.GetFieldInvoker;
import nuaa.zk.s09.reflection.invoke.Invoker;
import nuaa.zk.s09.reflection.invoke.MethodInvoker;
import nuaa.zk.s09.reflection.property.PropertyTokenizer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/25 21:26
 */
public class MetaClass {
    private Reflector reflector;

    private MetaClass(Class<?> type) {
        this.reflector = Reflector.forClass(type);
    }

    public static MetaClass forClass(Class<?> type) {
        return new MetaClass(type);
    }
    public static boolean isClassCacheEnabled() {
        return Reflector.isClassCacheEnabled();
    }

    public static void setClassCacheEnabled(boolean classCacheEnabled) {
        Reflector.setClassCacheEnabled(classCacheEnabled);
    }


    // TODO: 2023/3/26 返回get类型属性的返回值的MetaClass用处在于什么？
    public MetaClass metaClassForProperty(String name) {
        Class<?> propType = reflector.getGetterType(name);
        return MetaClass.forClass(propType);
    }


    // TODO: 2023/3/26 解析时候为什么不把集合解析出来？ 
    public String findProperty(String propName) {
        StringBuilder prop = buildProperty(propName, new StringBuilder());
        return prop.length() > 0 ? prop.toString() : null;
    }

    // TODO: 2023/3/26   这里的转换并没有把_后面的字母变为大写？？？？
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if (useCamelCaseMapping) {
            name = name.replace("_", "");
        }
        return findProperty(name);
    }

    /**
     *getSetterType 解析出List中元素的类型的方法是通过ObjectWrapper取出元素 然后一层层创建MetaClass 获取最后查询的属性的类型
     * getGetterType  通过反射直接获取泛型擦除前的actualTypeArguments 然后获得其MetaClass 再查询属性的类型
     * 有什么区别？？？ 不是很明白 2023.3.26
     */
    public Class<?> getSetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            //  这里返回第一个字段的 ReturnType 意义何在？  假如是student.id 这里是无法返回id的类型
            //更新。 这里配合MetaObject ObjectWrapper使用 经过前面处理到这一步 只会有一个字段 比如id
            MetaClass metaProp = metaClassForProperty(prop.getName());
            return metaProp.getSetterType(prop.getChildren());
        } else {
            return reflector.getSetterType(prop.getName());
        }
    }

    public Class<?> getGetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            //这里metaProp 会返回actualTypeArguments 而不是rawType
            MetaClass metaProp = metaClassForProperty(prop);
            //递归查找 直到找到最后一个属性值
            return metaProp.getGetterType(prop.getChildren());
        }
        // issue #506. Resolve the type inside a Collection Object
        //如果最后一个属性是集合的话，会获得集合的内部元素类型
        return getGetterType(prop);
    }
    private MetaClass metaClassForProperty(PropertyTokenizer prop) {
        Class<?> propType = getGetterType(prop);
        return MetaClass.forClass(propType);
    }
    private Class<?> getGetterType(PropertyTokenizer prop) {
        Class<?> type = reflector.getGetterType(prop.getName());
        //Collection.class.isAssignableFrom(type) 判断type是不是属于Collection
        if (prop.getIndex() != null && Collection.class.isAssignableFrom(type)) {
            Type returnType = getGenericGetterType(prop.getName());
            //ParameterizedType  泛型
            if (returnType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    returnType = actualTypeArguments[0];
                    if (returnType instanceof Class) {
                        type = (Class<?>) returnType;
                    } else if (returnType instanceof ParameterizedType) {
                        type = (Class<?>) ((ParameterizedType) returnType).getRawType();
                    }
                }
            }
        }
        return type;
    }
    private Type getGenericGetterType(String propertyName) {
        try {
            //查看invoker类型(MethodInvoker  GetInvoker)
            Invoker invoker = reflector.getGetInvoker(propertyName);
            if (invoker instanceof MethodInvoker) {
                //methodInvoker  获取method信息(MethodInvoker中的一个字段)
                Field _method = MethodInvoker.class.getDeclaredField("method");
                _method.setAccessible(true);
                //找到真正的method字段的值  类型是Method
                Method method = (Method) _method.get(invoker);
                return method.getGenericReturnType();
            } else if (invoker instanceof GetFieldInvoker) {
                Field _field = GetFieldInvoker.class.getDeclaredField("field");
                _field.setAccessible(true);
                Field field = (Field) _field.get(invoker);
                return field.getGenericType();
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
        return null;
    }


    private StringBuilder buildProperty(String name, StringBuilder builder) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            String propertyName = reflector.findPropertyName(prop.getName());
            if (propertyName != null) {
                builder.append(propertyName);
                builder.append(".");
                MetaClass metaProp = metaClassForProperty(propertyName);
                metaProp.buildProperty(prop.getChildren(), builder);
            }
        } else {
            String propertyName = reflector.findPropertyName(name);
            if (propertyName != null) {
                builder.append(propertyName);
            }
        }
        return builder;
    }

    public String[] getGetterNames() {
        return reflector.getGetablePropertyNames();
    }

    public String[] getSetterNames() {
        return reflector.getSetablePropertyNames();
    }
    public Invoker getGetInvoker(String name) {
        return reflector.getGetInvoker(name);
    }

    public Invoker getSetInvoker(String name) {
        return reflector.getSetInvoker(name);
    }
    public boolean hasDefaultConstructor() {
        return reflector.hasDefaultConstructor();
    }

    public boolean hasSetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (reflector.hasSetter(prop.getName())) {
                MetaClass metaProp = metaClassForProperty(prop.getName());
                return metaProp.hasSetter(prop.getChildren());
            } else {
                return false;
            }
        } else {
            return reflector.hasSetter(prop.getName());
        }
    }

    public boolean hasGetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (reflector.hasGetter(prop.getName())) {
                MetaClass metaProp = metaClassForProperty(prop);
                return metaProp.hasGetter(prop.getChildren());
            } else {
                return false;
            }
        } else {
            return reflector.hasGetter(prop.getName());
        }
    }
}
