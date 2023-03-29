package nuaa.zk.s08.executor.statement;

import nuaa.zk.s08.executor.Executor;
import nuaa.zk.s08.executor.result.ResultSetHandler;
import nuaa.zk.s08.mapping.BoundSql;
import nuaa.zk.s08.mapping.MapperStatement;
import nuaa.zk.s08.session.Configuration;
import nuaa.zk.s08.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 19:54
 */
public abstract class BaseStatementHandler implements StatementHandler {
    protected final Configuration configuration;
    protected final Executor executor;
    protected final MapperStatement mappedStatement;

    protected final Object parameterObject;
    protected final ResultSetHandler resultSetHandler;

    protected BoundSql boundSql;

    public BaseStatementHandler(Executor executor, MapperStatement mappedStatement, Object parameterObject, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.boundSql = boundSql;
        this.parameterObject = parameterObject;
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, boundSql);
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        Statement statement = null;
        try {
            statement = instantiateStatement(connection);
            statement.setQueryTimeout(350);
            statement.setFetchSize(10000);
            return statement;
        } catch (Exception e) {
            throw new RuntimeException("Error preparing statement.  Cause: " + e, e);
        }
    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;
}
