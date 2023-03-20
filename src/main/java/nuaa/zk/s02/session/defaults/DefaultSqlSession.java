package nuaa.zk.s02.session.defaults;

import nuaa.zk.s02.binding.MapperRegistry;
import nuaa.zk.s02.session.SqlSession;
import nuaa.zk.s02.session.SqlSessionFactory;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/20 21:52
 */
public class DefaultSqlSession implements SqlSession {
    public MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T)("你被代理了! " + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return (T) ("你被代理了！" + "方法：" + statement + " 入参：" + parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type,this);
    }
}
