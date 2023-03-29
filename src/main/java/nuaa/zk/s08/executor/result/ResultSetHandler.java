package nuaa.zk.s08.executor.result;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 19:55
 */
public interface ResultSetHandler {
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;
}
