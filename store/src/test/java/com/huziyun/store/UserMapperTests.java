package com.huziyun.store;


import com.huziyun.store.dao.UserDao;
import com.huziyun.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


public class UserMapperTests extends BasicTests {

    @Autowired
    private UserDao userdao;

    @Test
    public void testinsert(){
        User user = new User();
        user.setUsername("huziyun1");
        user.setPassword("123");
        Integer insert = userdao.insert(user);
        System.out.println(insert);

    }

    @Test
    public void testfindByUsername(){
        User huziyun1 = userdao.findByUsername("huziyun1");
        System.out.println(huziyun1);
    }

    @Test
    public void  testfindByUid(){
        User byUid = userdao.findByUid(6);
        System.out.println(byUid);
    }

    @Test
    public void testupdatePasswordByuid(){
        Date date = new Date();
        Integer huziyun = userdao.updatePasswordByUid(6, "333", "huziyun",date );
        System.out.println(huziyun);
    }

    @Test
    public void testupdateAvatarByUid(){
        userdao.updateAvatarByUid(6,"motherfucker","管理员",new Date());
    }

}
