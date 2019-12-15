package sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sf.entity.User;
import sf.service.UserService;
import sf.validator.LoginTokenValidator;
@Controller
public class GoodsController {
    @Autowired
    UserService userService;

    @RequestMapping("/goods/list")
    public String toLogin(Model model, @LoginTokenValidator User user){
        model.addAttribute("user",user);
        return "goods_list";
    }


}
