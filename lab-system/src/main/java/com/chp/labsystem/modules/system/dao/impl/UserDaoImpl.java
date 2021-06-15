package com.chp.labsystem.modules.system.dao.impl;

import com.chp.labcommon.base.GenericDaoImpl;
import com.chp.labsystem.modules.system.dao.UserDao;
import com.chp.labsystem.modules.system.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    @Override
    public User findByUsername(String username) {
        String hql = "FROM User WHERE username=?0 AND enabled=1";
        Object[] args = {username};
        return queryUniqueObject(hql, args);
    }
}
