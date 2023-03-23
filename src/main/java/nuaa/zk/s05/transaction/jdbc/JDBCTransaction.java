package nuaa.zk.s05.transaction.jdbc;

import nuaa.zk.s05.session.TransactionIsolationLevel;
import nuaa.zk.s05.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 20:58
 */
public class JDBCTransaction implements Transaction {
    protected Connection connection;
    protected DataSource dataSource;
    protected TransactionIsolationLevel isolationLevel;
    protected boolean autoCommit;

    public JDBCTransaction(Connection connection) {
        this.connection = connection;
    }

    public JDBCTransaction(DataSource dataSource, TransactionIsolationLevel isolationLevel, boolean autoCommit) {
        this.dataSource = dataSource;
        this.isolationLevel = isolationLevel;
        this.autoCommit = autoCommit;
    }

    @Override
    public Connection getConnection() throws SQLException {
        connection = dataSource.getConnection();
        connection.setAutoCommit(autoCommit);
        connection.setTransactionIsolation(isolationLevel.getIsolationLevel());
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null && !connection.getAutoCommit()){
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null && !connection.getAutoCommit()){
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.getAutoCommit()){
            connection.close();
        }
    }
}
