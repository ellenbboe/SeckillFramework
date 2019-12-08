package sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import sf.entity.User;
import sf.service.UserService;
import sf.vo.LoginVo;

import javax.websocket.server.PathParam;

@Controller
public class LoginController {

    @Autowired
    UserService userService;


    @RequestMapping("/user/login")
    public String login(){
        return "login";
    }



    @RequestMapping("/user/1")
    @ResponseBody
    public User getUsers(){
        return userService.getById(1);
    }
//    public String dologin(LoginVo loginVo)
//    {
//        String phone = loginVo.getPhone();
//        String password = loginVo.getPassword();
//        //数据检验
//        if(StringUtils.isEmpty(password))
//        {
//            return
//        }
//        if(StringUtils.isEmpty(phone))
//        {
//            return
//        }
//    }

//    public String dologinout()
//    {
//
//    }


}
