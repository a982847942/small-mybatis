package nuaa.zk.s09.executor.statement;

import nuaa.zk.s09.executor.Executor;
import nuaa.zk.s09.mapping.BoundSql;
import nuaa.zk.s09.mapping.MapperStatement;
import nuaa.zk.s09.session.ResultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 20:13
 */
public class PrepareStatementHandler extends BaseStatementHandler {
    public PrepareStatementHandler(Executor executor, MapperStatement mappedStatement, Object parameterObject, ResultHandler resultHandler, BoundSql boundSql) {
        super(executor, mappedStatement, parameterObject, resultHandler, boundSql);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
//        System.out.println(boundSql.getSql());
        return connection.prepareStatement(boundSql.getSql());
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
//
//        PreparedStatement ps = (PreparedStatement) statement;
//        ps.setLong(1, Long.parseLong(((Object[]) parameterObject)[0].toString()));
        parameterHandler.setParameters((PreparedStatement) statement);
    }

    @Override
    public <T> List<T> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        ((PreparedStatement) statement).execute();
        return resultSetHandler.handleResultSets(statement);
    }
}
