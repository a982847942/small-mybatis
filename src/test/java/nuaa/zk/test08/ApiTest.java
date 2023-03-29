package nuaa.zk.test08;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s08.io.Resources;
import nuaa.zk.s08.session.SqlSession;
import nuaa.zk.s08.session.SqlSessionFactory;
import nuaa.zk.s08.session.SqlSessionFactoryBuilder;
import nuaa.zk.test08.dao.IUserDao;
import nuaa.zk.test08.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource08.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        User user = userDao.queryUserInfoById(1L);
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }

}
