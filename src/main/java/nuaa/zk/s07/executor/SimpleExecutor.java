package nuaa.zk.s07.executor;

import nuaa.zk.s07.executor.statement.StatementHandler;
import nuaa.zk.s07.mapping.BoundSql;
import nuaa.zk.s07.mapping.MapperStatement;
import nuaa.zk.s07.session.Configuration;
import nuaa.zk.s07.session.ResultHandler;
import nuaa.zk.s07.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 17:41
 */
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    protected <E> List<E> doquery(MapperStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {

        try {
            StatementHandler statementHandler = configuration.newStatementHandler(this, ms, parameter, resultHandler, boundSql);
            Connection connection = transaction.getConnection();
            Statement statement = statementHandler.prepare(connection);
            statementHandler.parameterize(statement);
            return statementHandler.query(statement, resultHandler);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
