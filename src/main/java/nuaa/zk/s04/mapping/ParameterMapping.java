package nuaa.zk.s04.mapping;

import cn.hutool.db.meta.JdbcType;
import nuaa.zk.s04.session.Configuration;
import nuaa.zk.s04.type.JDBCType;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 21:26
 */
public class ParameterMapping {
    private Configuration configuration;

    // property
    private String property;
    // javaType = int
    private Class<?> javaType = Object.class;
    // jdbcType=NUMERIC
    private JDBCType jdbcType;

    private ParameterMapping() {
    }

    public static class Builder {

        private ParameterMapping parameterMapping = new ParameterMapping();

        public Builder(Configuration configuration, String property) {
            parameterMapping.configuration = configuration;
            parameterMapping.property = property;
        }

        public Builder javaType(Class<?> javaType) {
            parameterMapping.javaType = javaType;
            return this;
        }

        public Builder jdbcType(JDBCType jdbcType) {
            parameterMapping.jdbcType = jdbcType;
            return this;
        }

    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getProperty() {
        return property;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public JDBCType getJdbcType() {
        return jdbcType;
    }
}
