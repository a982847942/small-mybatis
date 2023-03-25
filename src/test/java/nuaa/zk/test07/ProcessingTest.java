package nuaa.zk.test07;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s07.reflection.Reflector;
import nuaa.zk.test07.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/25 21:46
 */
public class ProcessingTest {
    private final Logger logger = LoggerFactory.getLogger(ProcessingTest.class);
    @Test
    public void test(){
        Reflector reflector = new Reflector(User.class);
        logger.info("reflector:{}",reflector.getDefaultConstructor());
        logger.info("hasUserId getter:{}",reflector.hasGetter("userId"));
        logger.info("getter:{}", JSON.toJSON(reflector.getGetablePropertyNames()));
        logger.info("setter:{}", JSON.toJSON(reflector.getSetablePropertyNames()));
        logger.info("User caseInsensitive:{}", JSON.toJSON(reflector.getCaseInsensitivePropertyMap()));
    }
}