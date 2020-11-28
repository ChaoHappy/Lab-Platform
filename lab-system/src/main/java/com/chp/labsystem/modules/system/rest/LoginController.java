package com.chp.labsystem.modules.system.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    @PostMapping("/login")
    public String login(){
        System.out.println("login");
        return "aa";
    }

    @PostMapping("/lo")
    public String lo(){
        System.out.println("lo");
        return "lo";
    }

    @PostMapping("/user")
    @PreAuthorize("@el.check('user:list')")
    public String user(){
        return "user";
    }
}
