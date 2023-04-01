package nuaa.zk.s09.session.defaults;

import nuaa.zk.s09.executor.Executor;
import nuaa.zk.s09.session.Configuration;
import nuaa.zk.s09.session.SqlSession;
import nuaa.zk.s09.session.SqlSessionFactory;
import nuaa.zk.s09.session.TransactionIsolationLevel;
import nuaa.zk.s09.transaction.Transaction;
import nuaa.zk.s09.transaction.TransactionFactory;

import java.sql.SQLException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:19
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Transaction transaction = null;
        try {
            TransactionFactory transactionFactory = configuration.getEnvironment().getTransactionFactory();
            transaction = transactionFactory.getTransaction(configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
            final Executor executor = configuration.newExecutor(transaction);
            return new DefaultSqlSession(configuration, executor);
        }catch (Exception e) {
            try {
                assert transaction != null;
                transaction.close();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException("Error opening session.  Cause: " + e);
        }
    }
}
