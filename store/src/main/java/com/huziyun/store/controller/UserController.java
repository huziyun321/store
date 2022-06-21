package com.huziyun.store.controller;

import com.huziyun.store.entity.User;
import com.huziyun.store.service.IUserService;
import com.huziyun.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService iUserService;

/*    @RequestMapping("insert")
    public JsonResult<Void> insert(User user){
        JsonResult<Void> result = new JsonResult<>();

        try {
            iUserService.insert(user);
            result.setMessage("用户注册成功");
            result.setState(200);
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("用户时产生位置异常");
        }
        return result;

    }*/
    @RequestMapping("insert")
    public JsonResult<Void> insert(User user){
        iUserService.insert(user);
        return new JsonResult<>(ok);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User login = iUserService.login(username, password);
        session.setAttribute("uid",login.getUid());
        session.setAttribute("username",login.getUsername());
        return new JsonResult<User>(ok,login);

    }

    @RequestMapping("changePassword")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        iUserService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(ok);


    }
}
