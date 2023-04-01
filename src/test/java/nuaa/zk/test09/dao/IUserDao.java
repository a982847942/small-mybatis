package nuaa.zk.test09.dao;

import nuaa.zk.test09.po.User;

public interface IUserDao {

    User queryUserInfoById(Long id);

    User queryUserInfo(User req);

}
