package sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sf.entity.Goods;
import sf.entity.User;
import sf.model.SeckillGoodsModel;
import sf.service.GoodsService;
import sf.service.UserService;
import sf.util.DateUtil;
import sf.validator.LoginTokenValidator;

import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;
    @RequestMapping("/goods/list")
    public String toLogin(Model model, @LoginTokenValidator User user){
        model.addAttribute("user",userService.usertoModel(user));
        List<Goods>  goodsList = goodsService.GetGoodsList();
        model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }

    @RequestMapping("/goods_detail/goodsId/{id}")
    public String toDetail(@PathVariable("id") int id,Model model,@LoginTokenValidator User user)
    {
        model.addAttribute("user",userService.usertoModel(user));
        SeckillGoodsModel seckillGoodsModel = goodsService.GoodsToModel(goodsService.getGoodsById(id));
        long remainSeconds = seckillGoodsModel.getGoodsStock()>0? DateUtil.secoundToSeckill(seckillGoodsModel.getSeckillStarttime(),seckillGoodsModel.getSeckillEndtime()):-1;
        model.addAttribute("goods",seckillGoodsModel);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goods_detail";
    }


}
