package nuaa.zk.s02.session;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/20 21:52
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
