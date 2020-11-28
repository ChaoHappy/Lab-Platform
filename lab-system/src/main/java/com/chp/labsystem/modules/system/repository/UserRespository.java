package com.chp.labsystem.modules.system.repository;

import com.chp.labsystem.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
