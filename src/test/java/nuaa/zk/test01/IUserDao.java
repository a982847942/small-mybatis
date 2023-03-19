package nuaa.zk.test01;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/19 21:51
 */
public interface IUserDao {

    String queryUserName(String uId);

    Integer queryUserAge(String uId);

}
