package com.huziyun.store;


import com.huziyun.store.dao.UserDao;
import com.huziyun.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


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

}
