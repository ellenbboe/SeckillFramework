package sf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sf.entity.Ord;
import sf.entity.User;
import sf.model.OrderModel;
import sf.model.SeckillGoodsModel;
import sf.model.UserModel;
import sf.redis.RedisService;
import sf.result.Result;
import sf.service.GoodsService;
import sf.service.OrderService;
import sf.service.UserService;
import sf.validator.LoginTokenValidator;
import sf.vo.SeckillDetailVo;

@Controller
@Slf4j
public class SeckillController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    //生成订单,返回订单id fakesnow即可
    @PostMapping(value = "/seckill/seckill")
    @ResponseBody
    public Result<String> to_seckill(@RequestParam("goodsId")int goodsId, @LoginTokenValidator User user)
    {
        return Result.success(orderService.CreateOrderByGoodsAndUserID(user.getId(),goodsId));
    }
    //返回订单信息
    @GetMapping("/seckill/order_detail")
    @ResponseBody
    public Result<SeckillDetailVo> get_seckill_orderdetail(@RequestParam("orderid")String id,@LoginTokenValidator User user)
    {
        log.info("fuck the bug");
        SeckillDetailVo seckillDetailVo= new SeckillDetailVo();
        //seckillDetailVo取缓存
        // TODO: 2020/1/12 下面的方法里面写进缓存
        //查数据库
        Ord ord = orderService.GetById(id);
        seckillDetailVo.setOrderModel(orderService.OrderToModel(ord));
        seckillDetailVo.setGoods(goodsService.GoodsToModel(goodsService.getGoodsById(ord.getGoodsId())));
        seckillDetailVo.setUser(userService.usertoModel(user));
        //seckillDetailVo放缓存
//        redisService.setObj();
        return Result.success(seckillDetailVo);
    }
}
