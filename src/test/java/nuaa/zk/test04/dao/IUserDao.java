package nuaa.zk.test04.dao;


import nuaa.zk.test04.po.User;

public interface IUserDao {

    User queryUserInfoById(Long uId);

}
