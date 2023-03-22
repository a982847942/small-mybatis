package nuaa.zk.s04.transaction;

import nuaa.zk.s04.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 20:50
 */
public interface TransactionFactory {
    Transaction getTransaction(Connection connection);
    Transaction getTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

}
