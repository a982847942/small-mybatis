package nuaa.zk.test02.dao;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/20 22:17
 */
public interface IUserDao {
    Integer queryUserAge(String uId);
    String queryUserName(String uId);
}
