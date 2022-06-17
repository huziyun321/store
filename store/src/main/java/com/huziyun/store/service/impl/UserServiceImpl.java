package com.huziyun.store.service.impl;

import com.huziyun.store.dao.UserDao;
import com.huziyun.store.entity.User;
import com.huziyun.store.service.IUserService;
import com.huziyun.store.service.ex.InsertException;
import com.huziyun.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void insert(User user) {
        //调用findByUsername(username)判断用户是否被注册
        User byUsername = userDao.findByUsername(user.getUsername());
        //判断结果集是否为null，为null则抛出用户名被占用的异常
        if(byUsername != null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }
        //密码加密处理的实现:md5算法的形式
        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String md5Password = getMD5Password(oldPassword,salt);
        user.setPassword(md5Password);

        //补全数据，is_delete设置成0 数据库内默认设置
        user.setIsDelete(0);
        //补全数据：4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现
        Integer insert = userDao.insert(user);
        if(insert != 1 ){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }

    }

    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
     return password;
    }
}
