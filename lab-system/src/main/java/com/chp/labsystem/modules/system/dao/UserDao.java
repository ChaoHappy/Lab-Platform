package com.chp.labsystem.modules.system.dao;

import com.chp.labcommon.base.GenericDao;
import com.chp.labsystem.modules.system.domain.User;

public interface UserDao extends GenericDao<User, Long> {
    User findByUsername(String username);
}
