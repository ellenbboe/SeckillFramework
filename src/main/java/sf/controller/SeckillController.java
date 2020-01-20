package sf.controller;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sf.entity.Goods;
import sf.entity.Ord;
import sf.entity.User;
import sf.exception.BaseException;
import sf.model.OrderModel;
import sf.model.SeckillGoodsModel;
import sf.model.UserModel;
import sf.mq.MqSender;
import sf.mq.OrdMessage;
import sf.redis.RedisKey;
import sf.redis.RedisService;
import sf.result.CodeMsg;
import sf.result.Result;
import sf.service.GoodsService;
import sf.service.OrderService;
import sf.service.UserService;
import sf.util.CodeUtil;
import sf.validator.LoginTokenValidator;
import sf.validator.ServiceLimit;
import sf.vo.SeckillDetailVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class SeckillController implements InitializingBean {

    HashMap<Integer,Boolean> goodsStock = new HashMap<>();
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    MqSender mqSender;

    //将秒杀商品数量加载到redis中
    @Override
    public void afterPropertiesSet() throws Exception {
    List<Goods> list = goodsService.GetGoodsList();
        for (Goods one:list) {
            int stock = one.getGoodsStock();
            if (stock>0) {
                goodsStock.put(one.getId(), true);
            } else {
                goodsStock.put(one.getId(), false);
            }
            Object[] objects = {one.getId()};
            String key = RedisKey.genKey(Goods.class.getName(),"getGoodsStock",objects);
            redisService.setObj(Integer.class,key,stock,RedisKey.REDIS_GOODS_GOODSSTOCKXPICETIME, TimeUnit.SECONDS);
        }
    }

    @ServiceLimit(limitType = ServiceLimit.LimitType.IP)
    @GetMapping(value = "/seckill/getpath")
    @ResponseBody
    public Result<String> create_path(@RequestParam("goodsId")int goodsId, HttpServletRequest request)
    {
        if(!CodeUtil.checkVerifyCode(request))
        {
            return Result.error(CodeMsg.MIAO_SHA_VERICODE);
        }
        String result = goodsService.getRandomPath(goodsId);
        if(StringUtils.isEmpty(result))
        {
            return Result.error(CodeMsg.LINK_OUTTIME);
        }
        return Result.success(result);
    }

    @ServiceLimit(limitType = ServiceLimit.LimitType.IP)
    //生成订单,返回订单id fakesnow即可
    @PostMapping(value = "/seckill/{path}/seckill")
    @ResponseBody
    public Result<CodeMsg> to_seckill(@RequestParam("goodsId")int goodsId,@PathVariable("path")String path, @LoginTokenValidator User user)
    {
        if(StringUtils.isEmpty(path)||!path.equals(goodsService.getRandomPath(goodsId)))
        {
            return Result.error(CodeMsg.MIAO_SHA_FAIL);
        }
        if(!goodsStock.get(goodsId))
        {
            return Result.error(CodeMsg.MIAO_SHA_NO_STOCK);
        }
        Object[] objects = {goodsId};
        Long stock = redisService.decr(RedisKey.genKey(Goods.class.getName(),"getGoodsStock",objects));
        if (stock < 0) {
            goodsStock.put(goodsId, false);
            log.info("2");
            return Result.error(CodeMsg.MIAO_SHA_NO_STOCK);
        }
        OrdMessage message = new OrdMessage(user.getId(),goodsId);
        try{
            mqSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        if(goods.getGoodsStock()<=0)
        {
            throw new BaseException(CodeMsg.MIAO_SHA_NO_STOCK);
        }
        if(orderService.OrderExist(user.getId(), goodsId))
        {
            throw new BaseException(CodeMsg.MIAO_SHA_REPEAT);
        }
        return Result.success(CodeMsg.ORDER_IN_LINE);
//        return Result.success(orderService.CreateOrderByGoodsAndUserID(user.getId(),goodsId));
    }

    @GetMapping(value = "/seckill/seckill/result")
    @ResponseBody
    public Result<String> getSeckillResult(@RequestParam("goodsId")int goodsId, @LoginTokenValidator User user)
    {
        if(!goodsService.haveStock(goodsId))
        {
            log.info("3");
            return Result.error(CodeMsg.MIAO_SHA_NO_STOCK);
        }
        String result = orderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if(StringUtils.isEmpty(result))
        {
            return Result.error(CodeMsg.ORDER_IN_LINE);
        }else
        {
            return Result.success(result);
        }
    }
    @ServiceLimit(limitType = ServiceLimit.LimitType.IP)
    //返回订单信息
    @GetMapping("/seckill/order_detail")
    @ResponseBody
    public Result<SeckillDetailVo> get_seckill_orderdetail(@RequestParam("orderid")String id,@LoginTokenValidator User user)
    {
        log.info("fuck the bug");
        SeckillDetailVo seckillDetailVo= orderService.getSeckillDetailVo(id,user);
        //seckillDetailVo取缓存
        //查数据库
//        Ord ord = orderService.GetById(id);
//        seckillDetailVo.setOrderModel(orderService.OrderToModel(ord));
//        seckillDetailVo.setGoods(goodsService.GoodsToModel(goodsService.getGoodsById(ord.getGoodsId())));
//        seckillDetailVo.setUser(userService.usertoModel(user));
        //seckillDetailVo放缓存
//        redisService.setObj();
        return Result.success(seckillDetailVo);
    }
}
