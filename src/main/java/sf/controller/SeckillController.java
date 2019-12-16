package sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sf.entity.User;
import sf.service.GoodsService;
import sf.service.OrderService;
import sf.service.UserService;
import sf.validator.LoginTokenValidator;

@Controller
public class SeckillController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    //生成订单
    @RequestMapping("/seckill/seckill")
    public String to_seckill(int goodsId, Model model, @LoginTokenValidator User user)
    {
        model.addAttribute("user",userService.usertoModel(user));
        model.addAttribute("goods",goodsService.getGoodsById(goodsId));
        model.addAttribute("orderInfo",orderService.OrderToModel(orderService.CreateOrderByGoodsAndUserID(user.getId(),goodsId)));
        return "order_detail";
    }



}
