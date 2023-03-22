package nuaa.zk.test04;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s04.bulider.xml.XMLConfigBuilder;
import nuaa.zk.s04.io.Resources;
import nuaa.zk.s04.session.Configuration;
import nuaa.zk.s04.session.SqlSession;
import nuaa.zk.s04.session.SqlSessionFactory;
import nuaa.zk.s04.session.SqlSessionFactoryBuilder;
import nuaa.zk.s04.session.defaults.DefaultSqlSession;
import nuaa.zk.test04.dao.IUserDao;
import nuaa.zk.test04.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 21:38
 */
public class ApiTest {
    final Logger logger = LoggerFactory.getLogger(ApiTest.class);
    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource04.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        User user = userDao.queryUserInfoById(1L);
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }

    @Test
    public void test_selectOne() throws IOException {
        // 解析 XML
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource04.xml");
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        Configuration configuration = xmlConfigBuilder.parse();

        // 获取 DefaultSqlSession
        SqlSession sqlSession = new DefaultSqlSession(configuration);

        // 执行查询：默认是一个集合参数
        Object[] req = {1L};
        Object res = sqlSession.selectOne("nuaa.zk.test04.dao.IUserDao.queryUserInfoById", req);
        logger.info("测试结果：{}", JSON.toJSONString(res));
    }
}
