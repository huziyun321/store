package com.huziyun.store.controller;

import com.huziyun.store.controller.ex.*;
import com.huziyun.store.service.ex.*;
import com.huziyun.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;
import java.rmi.server.ServerCloneException;

public class BaseController {
    public static final int ok = 200;



    @ExceptionHandler({ServiceException.class,FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        }else if (e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户数据不存在的异常");
        }else if (e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("用户名的密码错误的异常");
        }else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知异常");
        }else if (e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新时数据异常");
        }else if (e instanceof FileEmptyException){
            result.setState(6000);
            result.setMessage("空文件数据异常");
        }else if (e instanceof FileSizeException){
            result.setState(6001);
            result.setMessage("文件大小数据异常");
        }else if (e instanceof FileTypeException){
            result.setState(6002);
            result.setMessage("文件类型数据异常");
        }else if (e instanceof FileStateException){
            result.setState(6003);
            result.setMessage("文件状态数据异常");
        }else if (e instanceof FileUploadIoException){
            result.setState(6004);
            result.setMessage("文件IO流数据异常");
        }
        return result;
    }
    protected final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }




}
