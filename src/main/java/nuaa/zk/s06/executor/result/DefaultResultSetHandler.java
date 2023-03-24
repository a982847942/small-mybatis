package nuaa.zk.s06.executor.result;

import nuaa.zk.s06.executor.Executor;
import nuaa.zk.s06.mapping.BoundSql;
import nuaa.zk.s06.mapping.MapperStatement;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/24 19:58
 */
public class DefaultResultSetHandler implements ResultSetHandler {
    private BoundSql boundSql;

    public DefaultResultSetHandler(Executor executor, MapperStatement mappedStatement, BoundSql boundSql) {
        this.boundSql = boundSql;
    }

    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        ResultSet resultSet = stmt.getResultSet();
        try {
            return resultSet2Obj(resultSet, Class.forName(boundSql.getResultType()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> res = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String name = metaData.getColumnName(i);
                    String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Method method;
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(methodName, Date.class);
                    } else {
                        method = clazz.getMethod(methodName, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                res.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
