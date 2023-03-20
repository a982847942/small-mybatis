package nuaa.zk.s02.session.defaults;

import nuaa.zk.s02.binding.MapperRegistry;
import nuaa.zk.s02.session.SqlSession;
import nuaa.zk.s02.session.SqlSessionFactory;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/20 21:53
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }
}
