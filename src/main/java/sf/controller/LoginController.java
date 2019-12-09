package sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import sf.entity.User;
import sf.result.CodeMsg;
import sf.result.Result;
import sf.service.UserService;
import sf.util.MD5Util;
import sf.vo.LoginVo;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Controller
public class LoginController {

    @Autowired
    UserService userService;


    @RequestMapping("/user")
    public String login(){
        return "login";
    }

    @RequestMapping("/user/do_login")
    @ResponseBody
    public Result<CodeMsg> LoginByMobile(@Valid LoginVo loginVo)
    {
        if (userService.login(loginVo)){
            return Result.success(CodeMsg.SUCCESS);
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
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
