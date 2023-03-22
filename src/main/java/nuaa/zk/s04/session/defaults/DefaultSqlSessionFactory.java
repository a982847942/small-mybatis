package nuaa.zk.s04.session.defaults;

import nuaa.zk.s04.session.Configuration;
import nuaa.zk.s04.session.SqlSession;
import nuaa.zk.s04.session.SqlSessionFactory;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:19
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
