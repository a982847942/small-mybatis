package nuaa.zk.s08.scripting;

import nuaa.zk.s08.mapping.SqlSource;
import nuaa.zk.s08.session.Configuration;
import org.dom4j.Element;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/27 15:35
 */
public interface LanguageDriver {
    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);
}
