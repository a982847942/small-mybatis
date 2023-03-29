package nuaa.zk.test08.dao;

import nuaa.zk.test08.po.User;

public interface IUserDao {

    User queryUserInfoById(Long uId);

}
