package nuaa.zk.s09.session;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 19:55
 */
public interface SqlSession {
    <T> T selectOne(String statement);
    <T> T selectOne(String statement,Object parameters);
    <T> T getMapper(Class<T> type);
    Configuration getConfiguration();
}
