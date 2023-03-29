package nuaa.zk.s08.mapping;

import nuaa.zk.s08.session.Configuration;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:35
 */
public class MapperStatement {

    private Configuration configuration;
    private String id;
    private SqlCommandType sqlCommandType;
    private SqlSource sqlSource;
    Class<?> resultType;

    MapperStatement() {
        // constructor disabled
    }

    /**
     * 建造者
     */
    public static class Builder {

        private MapperStatement mappedStatement = new MapperStatement();

        public Builder(Configuration configuration, String id, SqlCommandType sqlCommandType, SqlSource sqlSource, Class<?> resultType) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.sqlCommandType = sqlCommandType;
            mappedStatement.sqlSource = sqlSource;
            mappedStatement.resultType = resultType;
        }

        public MapperStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            return mappedStatement;
        }

    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getId() {
        return id;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public Class<?> getResultType() {
        return resultType;
    }
}
