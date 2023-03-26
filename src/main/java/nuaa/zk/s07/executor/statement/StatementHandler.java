package nuaa.zk.s07.executor.statement;

import nuaa.zk.s07.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 19:49
 */
public interface StatementHandler {
    /**
     * 获取Statement以及设置一些Statement参数
     * @param connection
     * @return
     * @throws SQLException
     */
    Statement prepare(Connection connection) throws SQLException;

    void parameterize(Statement statement) throws SQLException;

    <T> List<T> query(Statement statement, ResultHandler resultHandler) throws SQLException;
}
