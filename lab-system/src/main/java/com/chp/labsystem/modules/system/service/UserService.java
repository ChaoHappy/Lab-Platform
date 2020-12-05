package com.chp.labsystem.modules.system.service;

import com.chp.labsystem.modules.system.domain.User;

public interface UserService {
    User findByUsername(String username);
}
