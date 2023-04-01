package nuaa.zk.s09.scripting;

import nuaa.zk.s09.executor.parameter.ParameterHandler;
import nuaa.zk.s09.mapping.BoundSql;
import nuaa.zk.s09.mapping.MapperStatement;
import nuaa.zk.s09.mapping.SqlSource;
import nuaa.zk.s09.session.Configuration;
import org.dom4j.Element;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/27 15:35
 */
public interface LanguageDriver {
    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);
    /**
     * 创建参数处理器
     */
    ParameterHandler createParameterHandler(MapperStatement mappedStatement, Object parameterObject, BoundSql boundSql);
}
