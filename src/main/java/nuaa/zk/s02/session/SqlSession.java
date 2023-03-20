package nuaa.zk.s02.session;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/20 21:42
 */
public interface SqlSession {
    /**
     * Mybatis 中的SqlID
     * @param statement
     * @return
     * @param <T>
     */
    <T> T selectOne(String statement);
    <T> T selectOne(String statement,Object parameters);
    <T> T getMapper(Class<T> type);
}
