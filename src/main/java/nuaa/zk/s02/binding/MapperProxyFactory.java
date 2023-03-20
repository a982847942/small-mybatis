package nuaa.zk.s02.binding;

import nuaa.zk.s02.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/20 21:41
 */
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>(mapperInterface, sqlSession);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface},
                mapperProxy);
    }
}
