package nuaa.zk.test03;

import nuaa.zk.s03.io.Resources;
import nuaa.zk.s03.session.SqlSession;
import nuaa.zk.s03.session.SqlSessionFactory;
import nuaa.zk.s03.session.SqlSessionFactoryBuilder;
import nuaa.zk.test03.dao.IUserDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 21:10
 */
public class ApiTest {
    public final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        String res = userDao.queryUserById("10001");
        logger.info("测试结果,{}",res);
    }
}
