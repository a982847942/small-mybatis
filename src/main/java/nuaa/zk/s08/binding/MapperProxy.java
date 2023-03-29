package nuaa.zk.s08.binding;

import nuaa.zk.s08.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:57
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398559729838L;
    private Class<T> mapperInterface;
    private SqlSession sqlSession;
    private final Map<Method, MapperMethod> methodCache;
    public MapperProxy(Class<T> mapperInterface, SqlSession sqlSession, Map<Method, MapperMethod> methodCache) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(proxy, args);
        } else {
            final MapperMethod mapperMethod = cacheMapperMethod(method);
            return mapperMethod.execute(sqlSession,args);
        }
    }

    public MapperMethod cacheMapperMethod(Method method){
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null){
           mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
           methodCache.put(method,mapperMethod);
        }
        return mapperMethod;
    }

}
