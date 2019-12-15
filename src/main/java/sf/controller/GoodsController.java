package sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sf.entity.Goods;
import sf.entity.User;
import sf.service.GoodsService;
import sf.service.UserService;
import sf.validator.LoginTokenValidator;

import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/goods/list")
    public String toLogin(Model model, @LoginTokenValidator User user){
        model.addAttribute("user",user);
        List<Goods>  goodsList = goodsService.GetGoodsList();
        System.out.println("12121212");
        model.addAttribute("goodsList",goodsList);

        return "goods_list";
    }
}
