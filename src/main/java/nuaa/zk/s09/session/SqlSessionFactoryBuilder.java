package nuaa.zk.s09.session;

import nuaa.zk.s09.bulider.xml.XMLConfigBuilder;
import nuaa.zk.s09.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:24
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(Reader reader){
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration configuration){
        return new DefaultSqlSessionFactory(configuration);
    }
}
