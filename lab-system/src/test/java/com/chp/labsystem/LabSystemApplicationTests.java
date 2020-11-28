package com.chp.labsystem;

import com.chp.labsystem.modules.system.domain.User;
import com.chp.labsystem.modules.system.repository.MenuRepository;
import com.chp.labsystem.modules.system.repository.UserRespository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LabSystemApplicationTests {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRespository userRespository;

    @Test
    void contextLoads() {
        System.out.println(menuRepository.findAll());
    }

    @Test
    void testUser(){
        User user = userRespository.findByUsername("admin");
        System.out.println("用户："+user.getRoles());
    }

}
