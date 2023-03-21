package nuaa.zk.s03.mapping;

import nuaa.zk.s03.session.Configuration;

import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:35
 */
public class MapperStatement {
    private Configuration configuration;

    private String id;
    private String parameterType;
    private String resultType;
    private String sql;
    private SqlCommandType sqlCommandType;
    private Map<Integer, String> parameter;

    public MapperStatement() {

    }


    public static class Builder {
        private MapperStatement mapperStatement = new MapperStatement();

        public Builder(Configuration configuration, String id, SqlCommandType sqlCommandType, String parameterType, String resultType, String sql, Map<Integer, String> parameter) {
            mapperStatement.configuration = configuration;
            mapperStatement.id = id;
            mapperStatement.sql = sql;
            mapperStatement.sqlCommandType = sqlCommandType;
            mapperStatement.parameterType = parameterType;
            mapperStatement.resultType = resultType;
            mapperStatement.parameter = parameter;
        }
        public MapperStatement builder(){
            assert mapperStatement.configuration != null;
            assert mapperStatement.id != null;
            return mapperStatement;
        }
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public Map<Integer, String> getParameter() {
        return parameter;
    }

    public void setParameter(Map<Integer, String> parameter) {
        this.parameter = parameter;
    }
}
