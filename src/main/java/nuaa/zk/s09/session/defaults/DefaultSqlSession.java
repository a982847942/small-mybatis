package nuaa.zk.s09.session.defaults;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s09.executor.Executor;
import nuaa.zk.s09.mapping.MapperStatement;
import nuaa.zk.s09.session.Configuration;
import nuaa.zk.s09.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:18
 */
public class DefaultSqlSession implements SqlSession {
    private Logger logger = LoggerFactory.getLogger(DefaultSqlSession.class);


//    private Configuration configuration;
//    private Executor executor;
//
//    public DefaultSqlSession(Configuration configuration, Executor executor) {
//        this.configuration = configuration;
//        this.executor = executor;
//    }
//
//    @Override
//    public <T> T selectOne(String statement) {
//        return (T) ("你被代理了！" + statement);
//    }
//
//    @Override
////    public <T> T selectOne(String statement, Object parameter) {
////        MapperStatement mappedStatement = configuration.getMappedStatement(statement);
////        return (T) ("你被代理了！" + "\n方法：" + statement + "\n入参：" + parameter + "\n待执行SQL：" + mappedStatement.getSql());
////    }
//    public <T> T selectOne(String statement, Object parameter) {
//        try {
//            MapperStatement mappedStatement = configuration.getMappedStatement(statement);
////            Environment environment = configuration.getEnvironment();
//            //真正执行时再去获取连接
////            Connection connection = environment.getDataSource().getConnection();
////
////            BoundSql boundSql = mappedStatement.getBoundSql();
////            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
////            preparedStatement.setLong(1, Long.parseLong(((Object[]) parameter)[0].toString()));
////            ResultSet resultSet = preparedStatement.executeQuery();
////
////            List<T> objList = resultSet2Obj(resultSet, Class.forName(boundSql.getResultType()));
////            return objList.get(0);
//            List<T> res = executor.query(mappedStatement, parameter, Executor.NO_RESULT_HANDLER, mappedStatement.getBoundSql());
//            return res.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
//        List<T> list = new ArrayList<>();
//        try {
//            ResultSetMetaData metaData  = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//            while (resultSet.next()){
//                T obj = (T)clazz.newInstance();
//                for (int i = 1; i <= columnCount; i++) {
//                    Object value = resultSet.getObject(i);
//                    String columnName = metaData.getColumnName(i);
//                    String setMethod = "set" + columnName.substring(0,1).toUpperCase()  + columnName.substring(1);
//                    Method method;
//                    if (value instanceof Timestamp){
//                        method = clazz.getMethod(setMethod,Date.class);
//                    }else {
//                        method = clazz.getMethod(setMethod,value.getClass());
//                    }
//                    method.invoke(obj,value);
//                }
//                list.add(obj);
//            }
//            return list;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
////    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
////        List<T> list = new ArrayList<>();
////        try {
////            ResultSetMetaData metaData = resultSet.getMetaData();
////            int columnCount = metaData.getColumnCount();
////            // 每次遍历行值
////            while (resultSet.next()) {
////                T obj = (T) clazz.newInstance();
////                for (int i = 1; i <= columnCount; i++) {
////                    Object value = resultSet.getObject(i);
////                    String columnName = metaData.getColumnName(i);
////                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
////                    Method method;
////                    if (value instanceof Timestamp) {
////                        method = clazz.getMethod(setMethod, Date.class);
////                    } else {
////                        method = clazz.getMethod(setMethod, value.getClass());
////                    }
////                    method.invoke(obj, value);
////                }
////                list.add(obj);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return list;
////    }
//
//    @Override
//    public <T> T getMapper(Class<T> type) {
//        return configuration.getMapper(type, this);
//    }
//
//    @Override
//    public Configuration getConfiguration() {
//        return configuration;
//    }

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statement) {
        return this.selectOne(statement, null);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        logger.info("执行查询 statement：{} parameter：{}", statement, JSON.toJSONString(parameter));
        MapperStatement ms = configuration.getMappedStatement(statement);
        List<T> list = executor.query(ms, parameter, Executor.NO_RESULT_HANDLER, ms.getSqlSource().getBoundSql(parameter));
        return list.get(0);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
