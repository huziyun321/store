package com.huziyun.store.controller;

import com.huziyun.store.controller.ex.FileEmptyException;
import com.huziyun.store.controller.ex.FileSizeException;
import com.huziyun.store.controller.ex.FileTypeException;
import com.huziyun.store.controller.ex.FileUploadIoException;
import com.huziyun.store.entity.User;
import com.huziyun.store.service.IUserService;
import com.huziyun.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

    @RequestMapping("getByUid")
    public JsonResult<User> getByUid(HttpSession session){
        User byUid = iUserService.getByUid(getuidFromSession(session));
        return new JsonResult<>(ok,byUid);
    }

    @RequestMapping("changeInfo")
    public JsonResult<Void>changInfo(User user,HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        iUserService.changeInfo(user,uid,username);
        return new JsonResult<>(ok);
    }


    //设置上传文件的最大值
    public static final int Avatar_Max_Size = 10 * 1024 * 1024;

    //限制上传文件的类型
    public static final List<String> Avatar_Type = new ArrayList<>();
    static {
        Avatar_Type.add("image/jpeg");
        Avatar_Type.add("image/png");
        Avatar_Type.add("image/bmp");
        Avatar_Type.add("image/gif");
        Avatar_Type.add("image/pdf");
    }


    @RequestMapping("changeAvatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }

        if(file.getSize() > Avatar_Max_Size){
            throw new FileSizeException("文件超出限制");
        }

        //判断文件的类型是否是我们规定的后缀类型
        String contentType = file.getContentType();
        System.out.println(contentType);
        //如果集合包含某个元素则返回true
        if(!Avatar_Type.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        //项目部署到哪 就把上传的文件放在部署的文件下创建的upload文件夹下  .../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");
        //File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if(!dir.exists()){
            //创建目录
            dir.mkdir();
        }
        //获取这个文件名称，UUID工具来将生成的一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(i);
        String filename = UUID.randomUUID().toString().toUpperCase() + substring;
        //创建新文件，将上传文件写入新的空文件中
        File file1 = new File(dir, filename);
        //将参数写入这个空文件中
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            throw new FileUploadIoException("文件读写异常");
        }
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/" + filename;
        System.out.println(avatar);
        iUserService.changeAvatar(uid,avatar,username);
        //输出文件存放目录
        System.out.println(parent);
        //返回用户头像路径给前端页面，将来用于展示使用
        return new JsonResult<>(ok,avatar);

    }
}
