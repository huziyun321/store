package com.huziyun.store.dao;

import com.huziyun.store.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
     Integer insert(User user);
     User findByUsername(String username);
}
