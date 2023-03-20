package nuaa.zk.test02;

import nuaa.zk.s02.binding.MapperRegistry;
import nuaa.zk.s02.session.SqlSession;
import nuaa.zk.s02.session.SqlSessionFactory;
import nuaa.zk.s02.session.defaults.DefaultSqlSession;
import nuaa.zk.s02.session.defaults.DefaultSqlSessionFactory;
import nuaa.zk.test01.APITest;
import nuaa.zk.test02.dao.IUserDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/20 22:21
 */
public class ApiTest {
    private Logger logger = LoggerFactory.getLogger(ApiTest.class);
    @Test
    public void test(){
        MapperRegistry mapperRegistry = new MapperRegistry();
        mapperRegistry.addMappers("nuaa.zk.test02.dao");
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(mapperRegistry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        String res = userDao.queryUserName("10001");
        logger.info("测试结果:{}",res);
    }
}
