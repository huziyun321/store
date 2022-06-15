package com.huziyun.store.dao;

import com.huziyun.store.entity.User;

public interface UserDao {
     Integer insert(User user);
     User findByUsername(String username);
}
