package nuaa.zk.s07.executor;

import nuaa.zk.s07.mapping.BoundSql;
import nuaa.zk.s07.mapping.MapperStatement;
import nuaa.zk.s07.session.ResultHandler;
import nuaa.zk.s07.transaction.Transaction;

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
