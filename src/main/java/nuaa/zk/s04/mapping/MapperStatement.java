package nuaa.zk.s04.mapping;

import nuaa.zk.s04.session.Configuration;

import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:35
 */
public class MapperStatement {
    private Configuration configuration;

    private String id;
    private SqlCommandType sqlCommandType;
    //    private String parameterType;
//    private String resultType;
//    private String sql;
//    private Map<Integer, String> parameter;
    private BoundSql boundSql;

    public MapperStatement() {

    }


    public static class Builder {
        private MapperStatement mapperStatement = new MapperStatement();

//        public Builder(Configuration configuration, String id, SqlCommandType sqlCommandType, String parameterType, String resultType, String sql, Map<Integer, String> parameter) {
//            mapperStatement.configuration = configuration;
//            mapperStatement.id = id;
//            mapperStatement.sql = sql;
//            mapperStatement.sqlCommandType = sqlCommandType;
//            mapperStatement.parameterType = parameterType;
//            mapperStatement.resultType = resultType;
//            mapperStatement.parameter = parameter;
//        }

        public Builder(Configuration configuration, String id, SqlCommandType sqlCommandType, BoundSql boundSql) {
            mapperStatement.configuration = configuration;
            mapperStatement.id = id;
            mapperStatement.sqlCommandType = sqlCommandType;
            mapperStatement.boundSql = boundSql;
        }
        public MapperStatement builder() {
            assert mapperStatement.configuration != null;
            assert mapperStatement.id != null;
            return mapperStatement;
        }
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



    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public BoundSql getBoundSql() {
        return boundSql;
    }

    public void setBoundSql(BoundSql boundSql) {
        this.boundSql = boundSql;
    }
}
