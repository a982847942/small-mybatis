package nuaa.zk.test07;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s07.io.Resources;
import nuaa.zk.s07.session.SqlSession;
import nuaa.zk.s07.session.SqlSessionFactory;
import nuaa.zk.s07.session.SqlSessionFactoryBuilder;
import nuaa.zk.test07.dao.IUserDao;
import nuaa.zk.test07.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/26 20:33
 */
public class ApiTest {
    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource07.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        User user = userDao.queryUserInfoById(1L);
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }
}
