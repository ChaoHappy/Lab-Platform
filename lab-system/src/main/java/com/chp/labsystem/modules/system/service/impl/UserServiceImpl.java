package com.chp.labsystem.modules.system.service.impl;

import com.chp.labsystem.modules.system.dao.UserDao;
import com.chp.labsystem.modules.system.domain.User;
import com.chp.labsystem.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
