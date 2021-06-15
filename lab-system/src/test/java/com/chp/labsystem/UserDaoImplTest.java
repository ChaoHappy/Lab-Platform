package com.chp.labsystem;

import com.chp.labsystem.modules.system.dao.UserDao;
import com.chp.labsystem.modules.system.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsername() {
        User user = userDao.findByUsername("admin");
        System.out.println(user.toString());
    }
}
