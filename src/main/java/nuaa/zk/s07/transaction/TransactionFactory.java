package nuaa.zk.s07.transaction;

import nuaa.zk.s07.session.TransactionIsolationLevel;

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
