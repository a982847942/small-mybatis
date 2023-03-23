package nuaa.zk.test05;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s05.io.Resources;
import nuaa.zk.s05.session.SqlSession;
import nuaa.zk.s05.session.SqlSessionFactory;
import nuaa.zk.s05.session.SqlSessionFactoryBuilder;
import nuaa.zk.test05.dao.IUserDao;
import nuaa.zk.test05.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/23 20:35
 */
public class ApiTest {
    public final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource05.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        for (int i = 0; i < 50; i++) {
            User user = userDao.queryUserInfoById(1L);
            logger.info("测试结果：{}", JSON.toJSONString(user));
        }
    }
}
