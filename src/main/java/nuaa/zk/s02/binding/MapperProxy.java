package nuaa.zk.s02.binding;

import nuaa.zk.s02.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/20 21:41
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398559729838L;
    final Class<T> mapperInterface;
    SqlSession sqlSession;

    public MapperProxy(Class<T> mapperInterface, SqlSession sqlSession) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       if (Object.class.equals(method.getDeclaringClass())){
           return method.invoke(proxy,args);
       }else {
           return sqlSession.selectOne(method.getName(),args);
       }
    }
}
