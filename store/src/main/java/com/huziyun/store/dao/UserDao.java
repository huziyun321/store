package com.huziyun.store.dao;

import com.huziyun.store.entity.User;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;

@Repository
public interface UserDao {
     Integer insert(User user);
     User findByUsername(String username);
     Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);
     User findByUid(Integer uid);
     Integer updateInfoByUid(User user);
     Integer updateAvatarByUid(Integer uid,String avatar,String modifiedUser,Date modifiedTime);

}
