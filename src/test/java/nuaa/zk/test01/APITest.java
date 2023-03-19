package nuaa.zk.test01;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s01.binding.MapperProxyFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAnyAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/19 21:53
 */
public class APITest {
    private Logger logger = LoggerFactory.getLogger(APITest.class);
    @Test
    public void test(){
        MapperProxyFactory<IUserDao> mapperProxyFactory = new MapperProxyFactory(IUserDao.class);
        Map<String,String> sqlSession = new HashMap<String, String>();
        sqlSession.put("nuaa.zk.test01.IUserDao.queryUserName","模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
        sqlSession.put("nuaa.zk.test01.IUserDao.queryUserAge","模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");
        IUserDao iUserDao = mapperProxyFactory.newInstance(sqlSession);
        String res = iUserDao.queryUserName("10001");
        logger.info("测试结果,{}", JSON.toJSONString(res));
    }
}
