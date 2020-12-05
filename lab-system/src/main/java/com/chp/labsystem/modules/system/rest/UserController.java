package com.chp.labsystem.modules.system.rest;

import com.chp.labcommon.exception.BadRequestException;
import com.chp.labsystem.modules.system.domain.User;
import com.chp.labsystem.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> user(){
        return new ResponseEntity<>("user",HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> user(String username){
        User user = userService.findByUsername(username);
        if(user==null){
            throw new BadRequestException("发生了异常");
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
