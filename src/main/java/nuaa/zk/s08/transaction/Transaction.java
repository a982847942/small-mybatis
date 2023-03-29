package nuaa.zk.s08.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 20:47
 */
public interface Transaction {
    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;
}
