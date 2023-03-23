package nuaa.zk.s05.transaction.jdbc;

import nuaa.zk.s05.session.TransactionIsolationLevel;
import nuaa.zk.s05.transaction.Transaction;
import nuaa.zk.s05.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 21:02
 */
public class JDBCTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(Connection connection) {
        return new JDBCTransaction(connection);
    }

    @Override
    public Transaction getTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JDBCTransaction(dataSource,level,autoCommit);
    }
}
