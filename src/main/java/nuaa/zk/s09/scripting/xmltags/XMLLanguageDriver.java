package nuaa.zk.s09.scripting.xmltags;


import nuaa.zk.s09.executor.parameter.ParameterHandler;
import nuaa.zk.s09.mapping.BoundSql;
import nuaa.zk.s09.mapping.MapperStatement;
import nuaa.zk.s09.mapping.SqlSource;
import nuaa.zk.s09.scripting.LanguageDriver;
import nuaa.zk.s09.scripting.defaults.DefaultParameterHandler;
import nuaa.zk.s09.session.Configuration;
import org.dom4j.Element;

public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

    @Override
    public ParameterHandler createParameterHandler(MapperStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }

}