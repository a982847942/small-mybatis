package nuaa.zk.s01.binding;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/19 21:39
 */
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }
    public T newInstance(Map<String,String> sqlSession){
        final MapperProxy<T> mapperProxy = new MapperProxy(sqlSession, mapperInterface);
        return (T)Proxy.newProxyInstance(mapperInterface.getClassLoader(),new Class[]{mapperInterface},mapperProxy);
    }
}
