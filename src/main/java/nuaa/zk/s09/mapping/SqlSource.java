package nuaa.zk.s09.mapping;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/27 15:36
 */
public interface SqlSource {
    BoundSql getBoundSql(Object parameterObject);
}
