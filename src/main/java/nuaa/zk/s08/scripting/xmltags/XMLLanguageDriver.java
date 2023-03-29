package nuaa.zk.s08.scripting.xmltags;


import nuaa.zk.s08.mapping.SqlSource;
import nuaa.zk.s08.scripting.LanguageDriver;
import nuaa.zk.s08.session.Configuration;
import org.dom4j.Element;

public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

}