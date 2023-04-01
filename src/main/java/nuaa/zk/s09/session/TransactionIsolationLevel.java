package nuaa.zk.s09.session;

import java.sql.Connection;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 20:51
 */
public enum TransactionIsolationLevel {
    NONE(Connection.TRANSACTION_NONE),
    READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
    READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
    REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
    SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

    private final int isolationLevel;

    TransactionIsolationLevel(int level) {
        isolationLevel = level;
    }

    public int getIsolationLevel() {
        return isolationLevel;
    }
}
