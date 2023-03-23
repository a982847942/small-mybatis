package nuaa.zk.test05.dao;


import nuaa.zk.test05.po.User;

public interface IUserDao {

    User queryUserInfoById(Long uId);

}
