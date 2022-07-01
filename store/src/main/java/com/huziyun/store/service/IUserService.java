package com.huziyun.store.service;

import com.huziyun.store.entity.User;
import org.springframework.stereotype.Service;


public interface IUserService {
    void insert(User user);
    User login(String username,String password);
    void changePassword(Integer uid,String username,String oldPassword,String newPassword);
    User getByUid(Integer uid);
    void changeInfo(User user,Integer uid,String name);
    void changeAvatar(Integer uid,String avatar,String username);




}
