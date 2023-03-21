package nuaa.zk.s03.session;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:13
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
