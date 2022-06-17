package com.huziyun.store;


import com.huziyun.store.entity.User;
import com.huziyun.store.service.IUserService;
import com.huziyun.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.rmi.server.ServerCloneException;

public class UserServiceTest extends BasicTests {

    @Autowired
    private IUserService iUserService;


    @Test
    public void testinsertservice(){
        try {
            User user = new User();
            user.setUsername("duanzhilan12");
            user.setPassword("123");
            iUserService.insert(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
