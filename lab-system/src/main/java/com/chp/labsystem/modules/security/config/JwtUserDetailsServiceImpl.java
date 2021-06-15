package com.chp.labsystem.modules.security.config;

import com.chp.labsystem.modules.security.dto.JwtUser;
import com.chp.labsystem.modules.system.domain.Menu;
import com.chp.labsystem.modules.system.domain.Role;
import com.chp.labsystem.modules.system.domain.User;
import com.chp.labsystem.modules.system.repository.UserRespository;
import com.chp.labsystem.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO： 从数据库中获取用户对象
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", username));
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (user.getIsAdmin()) {
            authorities = new ArrayList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("admin"));
        } else {
            authorities = user.getRoles().stream().flatMap(role -> role.getMenus().stream()).filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                    .map(Menu::getPermission).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        return new JwtUser(user.getUsername(), encode.encode(user.getPassword()), 1, authorities);
    }
}

