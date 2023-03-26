package nuaa.zk.test07.dao;


import nuaa.zk.test07.po.User;

public interface IUserDao {

    User queryUserInfoById(Long uId);

}
