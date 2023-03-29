package nuaa.zk.s08.scripting.defaults;


import nuaa.zk.s08.bulider.SqlSourceBuilder;
import nuaa.zk.s08.mapping.BoundSql;
import nuaa.zk.s08.mapping.SqlSource;
import nuaa.zk.s08.scripting.xmltags.DynamicContext;
import nuaa.zk.s08.scripting.xmltags.SqlNode;
import nuaa.zk.s08.session.Configuration;

import java.util.HashMap;

public class RawSqlSource implements SqlSource {

    private final SqlSource sqlSource;

    public RawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
        this(configuration, getSql(configuration, rootSqlNode), parameterType);
    }

    public RawSqlSource(Configuration configuration, String sql, Class<?> parameterType) {
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> clazz = parameterType == null ? Object.class : parameterType;
        sqlSource = sqlSourceParser.parse(sql, clazz, new HashMap<>());
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return sqlSource.getBoundSql(parameterObject);
    }

    private static String getSql(Configuration configuration, SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(configuration, null);
        rootSqlNode.apply(context);
        return context.getSql();
    }

}
