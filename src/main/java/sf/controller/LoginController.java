package sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import sf.result.CodeMsg;
import sf.result.Result;
import sf.service.UserService;
import sf.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/user/do_login")
    @ResponseBody
    public Result<Object> LoginByMobile(HttpServletRequest request,@Valid LoginVo loginVo)
    {
        String token = userService.login(request,loginVo);
        if (!StringUtils.isEmpty(token)){
            return Result.success(token);
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }


}
