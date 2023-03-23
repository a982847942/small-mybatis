package nuaa.zk.s05.binding;

import cn.hutool.core.lang.ClassScanner;
import nuaa.zk.s05.session.Configuration;
import nuaa.zk.s05.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:54
 */
public class MapperRegistry {
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    private Configuration configuration;

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<?> mapperProxyFactory = knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return (T) mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }

    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                //重复添加
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            knownMappers.put(type, new MapperProxyFactory<>(type));
        } else {
            throw new RuntimeException("Type " + type + " is not a interface");
        }
    }

    public void addMappers(String packageName) {
        Set<Class<?>> mapperClassSet = ClassScanner.scanPackage(packageName);
        for (Class<?> mapperClass : mapperClassSet) {
            addMapper(mapperClass);
        }
    }
}
