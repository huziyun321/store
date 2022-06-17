package com.huziyun.store.controller;

import com.huziyun.store.service.ex.InsertException;
import com.huziyun.store.service.ex.ServiceException;
import com.huziyun.store.service.ex.UsernameDuplicatedException;
import com.huziyun.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.rmi.server.ServerCloneException;

public class BaseController {
    public static final int ok = 200;
    public static final String ck = "注册成功";



    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        }else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知异常");
        }
        return result;
    }


}