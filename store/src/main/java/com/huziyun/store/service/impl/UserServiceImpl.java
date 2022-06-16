package com.huziyun.store.service.impl;

import com.huziyun.store.dao.UserDao;
import com.huziyun.store.entity.User;
import com.huziyun.store.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void insert(User user) {
        //调用findByUsername(username)判断用户是否被注册
        User byUsername = userDao.findByUsername(user.getUsername());
        //判断结果集是否为null，不为null则抛出用户名被占用的异常



    }
}
