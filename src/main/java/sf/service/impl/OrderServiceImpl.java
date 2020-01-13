package sf.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.GoodsMapper;
import sf.dao.OrdMapper;
import sf.entity.Goods;
import sf.entity.Ord;
import sf.entity.OrdExample;
import sf.exception.BaseException;
import sf.model.OrderModel;
import sf.redis.RedisKey;
import sf.redis.RedisService;
import sf.result.CodeMsg;
import sf.service.GoodsService;
import sf.service.OrderService;
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    OrdMapper ordMapper;
    @Autowired(required = false)
    GoodsMapper goodsMapper;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Override
    public Ord GetById(String id) {
        return ordMapper.selectByPrimaryKey(id);
    }

    @Override
    public String CreateOrderByGoodsAndUserID(int userId, int goodsId) {
        Goods goods = goodsService.getGoodsById(goodsId);
        if(goods.getGoodsStock()<=0)
        {
            throw new BaseException(CodeMsg.MIAO_SHA_NO_STOCK);
        }
        if(OrderExist(userId, goodsId))
        {
            throw new BaseException(CodeMsg.MIAO_SHA_REPEAT);
        }
        Ord newOrd = new Ord(userId,goodsId);
        try{
            goods.setGoodsStock(goods.getGoodsStock()-1);
            goodsMapper.updateByPrimaryKeySelective(goods);
            ordMapper.insertSelective(newOrd);
        }catch (Exception e)
        {
            throw new BaseException(CodeMsg.MIAO_SHA_NO_STOCK);
        }
        return newOrd.getId();
    }


    // TODO: 2020/1/12 是否可以使用aop实现
    @Override
    public OrderModel OrderToModel(Ord ord) {
        String key = RedisKey.getRedisKey(RedisKey.REDIS_MODEL,RedisKey.REDIS_MODEL_ORDERMODEL,ord.getId());
        OrderModel orderModel = (OrderModel)redisService.getObj(key);
        if(orderModel !=null)
        {
            return orderModel;
        }
        orderModel = new OrderModel(ord);
        redisService.setObj(key,orderModel,RedisKey.REDIS_MODEL_EXPICETIME);
        return orderModel;
    }

    public boolean OrderExist(int userId,int goodsId)
    {
        OrdExample ordExample = new OrdExample();
        OrdExample.Criteria criteria = ordExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andUserIdEqualTo(userId);
        return ordMapper.countByExample(ordExample) > 0;
    }
}
