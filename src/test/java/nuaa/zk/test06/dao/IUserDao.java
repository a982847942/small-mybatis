package nuaa.zk.test06.dao;


import nuaa.zk.test06.po.User;

public interface IUserDao {

    User queryUserInfoById(Long uId);

}
