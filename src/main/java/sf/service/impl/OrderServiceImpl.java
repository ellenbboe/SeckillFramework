package sf.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.GoodsMapper;
import sf.dao.OrdMapper;
import sf.entity.Goods;
import sf.entity.Ord;
import sf.entity.OrdExample;
import sf.model.OrderModel;
import sf.redis.RedisKey;
import sf.redis.RedisService;
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
    public boolean CreateOrderByGoodsAndUserID(int userId, int goodsId) {
        if(OrderExist(userId,goodsId))
        {
            return false;
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        Ord newOrd = new Ord(userId,goodsId);
        goods.setGoodsStock(goods.getGoodsStock()-1);
        goodsMapper.updateByPrimaryKeySelective(goods);
        ordMapper.insertSelective(newOrd);
        //update redis
        goodsService.updateModel(goodsId);
        return true;
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

    @Override
    public boolean OrderExist(int userId,int goodsId)
    {
        OrdExample ordExample = new OrdExample();
        OrdExample.Criteria criteria = ordExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andUserIdEqualTo(userId);
        return ordMapper.countByExample(ordExample) > 0;
    }
    @Override
    public String getOrderByUserIdAndGoodsId(int userId,int goodsId){
        OrdExample ordExample = new OrdExample();
        OrdExample.Criteria criteria = ordExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andUserIdEqualTo(userId);
        if(ordMapper.countByExample(ordExample)>0)
        {
            return ordMapper.selectByExample(ordExample).get(0).getId();
        }else{
            return null;
        }
    }
}
