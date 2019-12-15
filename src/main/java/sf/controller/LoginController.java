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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Controller
public class LoginController {

    @Autowired
    UserService userService;


    @RequestMapping("/user/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/user/do_login")
    @ResponseBody
    public Result<CodeMsg> LoginByMobile(HttpServletResponse response,@Valid LoginVo loginVo)
    {
        if (userService.login(response,loginVo)){
            return Result.success(CodeMsg.SUCCESS);
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }


}
