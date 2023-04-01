package nuaa.zk.s09.bulider;

import nuaa.zk.s09.mapping.BoundSql;
import nuaa.zk.s09.mapping.ParameterMapping;
import nuaa.zk.s09.mapping.SqlSource;
import nuaa.zk.s09.session.Configuration;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/29 20:44
 */
public class StaticSqlSource implements SqlSource {
    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }
}
