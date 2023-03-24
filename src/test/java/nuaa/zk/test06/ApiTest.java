package nuaa.zk.test06;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s06.io.Resources;
import nuaa.zk.s06.session.SqlSession;
import nuaa.zk.s06.session.SqlSessionFactory;
import nuaa.zk.s06.session.SqlSessionFactoryBuilder;
import nuaa.zk.test06.dao.IUserDao;
import nuaa.zk.test06.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 20:31
 */
public class ApiTest {
    public final Logger logger = LoggerFactory.getLogger(ApiTest.class);
    @Test
    public void test() throws IOException {
            // 1. 从SqlSessionFactory中获取SqlSession
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource06.xml"));
            SqlSession sqlSession = sqlSessionFactory.openSession();

            // 2. 获取映射器对象
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);

            // 3. 测试验证
            User user = userDao.queryUserInfoById(1L);
            logger.info("测试结果：{}", JSON.toJSONString(user));
    }
}
