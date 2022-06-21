package com.huziyun.store.service.impl;

import com.huziyun.store.dao.UserDao;
import com.huziyun.store.entity.User;
import com.huziyun.store.service.IUserService;
import com.huziyun.store.service.ex.*;
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

    @Override
    public User login(String username, String password) {
        User result = userDao.findByUsername(username);
        if(result == null ){
            throw new UserNotFoundException("用户数据不存在");
        }
        String oldpassword = result.getPassword();
        String salt = result.getSalt();
        String newMd5Password = getMD5Password(password,salt);
        if(!newMd5Password.equals(oldpassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        if(result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //减少数据传输，提升系统性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        //将当前的用户数据返回，返回的数据是为了辅助其他页面做数据展示使用
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User byUid = userDao.findByUid(uid);
        if(byUid == null || byUid.getIsDelete() == 1 ){
            throw new UserNotFoundException("用户不存在");
        }
        String md5Password = getMD5Password(oldPassword, byUid.getSalt());
        if(!byUid.getPassword().equals(md5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        String md5Password1 = getMD5Password(newPassword, byUid.getSalt());
        Integer integer = userDao.updatePasswordByUid(uid, md5Password1, username, new Date());
        if(integer != 1){
            throw new UpdateException("更新数据产生未知的异常");
        }
    }



}
