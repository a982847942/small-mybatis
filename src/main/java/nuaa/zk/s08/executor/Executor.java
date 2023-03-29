package nuaa.zk.s08.executor;

import nuaa.zk.s08.mapping.BoundSql;
import nuaa.zk.s08.mapping.MapperStatement;
import nuaa.zk.s08.session.ResultHandler;
import nuaa.zk.s08.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 17:27
 */
public interface Executor {
    ResultHandler NO_RESULT_HANDLER = null;

    <E> List<E> query(MapperStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);
    Transaction getTransaction();
    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);

}
