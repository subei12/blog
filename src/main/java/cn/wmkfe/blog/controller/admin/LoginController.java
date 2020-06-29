package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.User;
import cn.wmkfe.blog.service.UserService;
import cn.wmkfe.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/blog") //原本是perfectStudent,太难记就改了，至于为什么不改成admin是因为和下面的requestMapping结合之后会被当初是templates的内容，会404的
public class LoginController {

    @Autowired
    private UserService userService;

    @Operation("登录界面")
    @RequestMapping(value = "/login.html")
    public String loginPage(){
        return "admin/login";
    }


    @Operation("退出登录")
    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "admin/login";
    }

}
