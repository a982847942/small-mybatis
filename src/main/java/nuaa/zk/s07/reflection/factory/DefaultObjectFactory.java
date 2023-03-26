package nuaa.zk.s07.reflection.factory;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/26 13:16
 */
public class DefaultObjectFactory implements ObjectFactory, Serializable {
    public final static long serialVersionUID = -8855120656740914948L;

    @Override
    public void setProperties(Properties properties) {
        //暂时不设置
    }

    @Override
    public <T> T create(Class<T> type) {
        return create(type, null, null);
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        //1.解析
        Class<?> classToCreate = resolveInterface(type);
        //2.实例化
        return (T) instantiateClass(classToCreate, constructorArgTypes, constructorArgs);
    }

    private <T> T instantiateClass(Class<T> classToCreate, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        try {
            Constructor<T> constructor;
            if (constructorArgTypes == null || constructorArgs == null) {
                constructor = classToCreate.getDeclaredConstructor();
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                return constructor.newInstance();
            }
            //传入args
            constructor = classToCreate.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
        } catch (Exception e) {
            StringBuilder argTypes = new StringBuilder();
            if (constructorArgTypes != null) {
                for (Class<?> constructorArgType : constructorArgTypes) {
                    argTypes.append(constructorArgType);
                    argTypes.append(",");
                }
            }

            StringBuilder args = new StringBuilder();
            if (constructorArgs != null) {
                for (Object constructorArg : constructorArgs) {
                    args.append(constructorArg);
                    args.append(",");
                }
            }
            throw new RuntimeException("Error instantiating " + classToCreate + " with invalid types (" + argTypes + ") or values (" + args + "). Cause: " + e, e);
        }
    }

    private <T> Class<?> resolveInterface(Class<T> type) {
        Class<?> classToCreate = null;
        if (type == List.class || type == Collection.class || type == Iterable.class) {
            classToCreate = ArrayList.class;
        } else if (type == Map.class) {
            classToCreate = HashMap.class;
        } else if (type == Set.class) {
            classToCreate = HashSet.class;
        } else {
            classToCreate = type;
        }
        return classToCreate;
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
