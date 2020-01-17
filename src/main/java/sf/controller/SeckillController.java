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
import sf.vo.SeckillDetailVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

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
            String key = RedisKey.getRedisKey(RedisKey.REDIS_GOODS,RedisKey.REDIS_GOODS_STOCK,one.getId().toString());
            redisService.setObj(key,stock,RedisKey.REDIS_GOODS_GOODSSTOCKXPICETIME);
        }
    }

    @GetMapping(value = "/seckill/getpath")
    @ResponseBody
    public Result<String> create_path(@RequestParam("goodsId")int goodsId, HttpServletRequest request)
    {
        if(!CodeUtil.checkVerifyCode(request))
        {
            return Result.error(CodeMsg.MIAO_SHA_VERICODE);
        }
        String result = goodsService.createRandomPath(goodsId);
        if(StringUtils.isEmpty(result))
        {
            return Result.error(CodeMsg.LINK_OUTTIME);
        }
        return Result.success(result);
    }


    //生成订单,返回订单id fakesnow即可
    @PostMapping(value = "/seckill/{path}/seckill")
    @ResponseBody
    public Result<CodeMsg> to_seckill(@RequestParam("goodsId")int goodsId,@PathVariable("path")String path, @LoginTokenValidator User user)
    {
        if(!goodsService.checkRandomPath(path, goodsId))
        {
            return Result.error(CodeMsg.MIAO_SHA_FAIL);
        }
        if(!goodsStock.get(goodsId))
        {
            return Result.error(CodeMsg.MIAO_SHA_NO_STOCK);
        }
        Long stock = (Long)redisService.decr(RedisKey.getRedisKey(RedisKey.REDIS_GOODS,RedisKey.REDIS_GOODS_STOCK,String.valueOf(goodsId)));
        if (stock < 0) {
            goodsStock.put(goodsId, false);
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
