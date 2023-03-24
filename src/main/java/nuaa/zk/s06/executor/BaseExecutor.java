package nuaa.zk.s06.executor;

import nuaa.zk.s06.mapping.BoundSql;
import nuaa.zk.s06.mapping.MapperStatement;
import nuaa.zk.s06.session.Configuration;
import nuaa.zk.s06.session.ResultHandler;
import nuaa.zk.s06.transaction.Transaction;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 17:32
 */
public abstract class BaseExecutor implements Executor {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(BaseExecutor.class);
    Configuration configuration;
    protected Transaction transaction;
    protected Executor wrapper;

    private boolean closed;

    public BaseExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
    }

    @Override
    public <E> List<E> query(MapperStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        if (closed) {
            throw new RuntimeException("Executor was closed.");
        }
        return doquery(ms, parameter, resultHandler, boundSql);
    }

    protected abstract <E> List<E> doquery(MapperStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);

    @Override
    public Transaction getTransaction() {
        if (closed) {
            throw new RuntimeException("Executor was closed.");
        }
        return transaction;
    }

    @Override
    public void commit(boolean required) throws SQLException {
        if (closed) {
            throw new RuntimeException("Cannot commit, transaction is already closed");
        }
        if (required) {
            transaction.commit();
        }
    }

    @Override
    public void rollback(boolean required) throws SQLException {
        if (!closed) {
            if (required) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void close(boolean forceRollback) {
        try {
            try {
                rollback(forceRollback);
            } finally {
                transaction.close();
            }
        } catch (SQLException e) {
            logger.warn("Unexpected exception on closing transaction.  Cause: " + e);
        } finally {
            transaction = null;
            closed = true;
        }
    }
}
