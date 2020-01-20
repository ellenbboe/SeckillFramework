package sf.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.cache.RedisCache;
import sf.dao.GoodsMapper;
import sf.dao.OrdMapper;
import sf.entity.Goods;
import sf.entity.Ord;
import sf.entity.OrdExample;
import sf.entity.User;
import sf.model.OrderModel;
import sf.redis.RedisKey;
import sf.redis.RedisService;
import sf.service.GoodsService;
import sf.service.OrderService;
import sf.service.UserService;
import sf.vo.SeckillDetailVo;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    OrdMapper ordMapper;
    @Autowired
    UserService userService;
    @Autowired(required = false)
    GoodsMapper goodsMapper;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Override
    @RedisCache(TYPE = Ord.class)
    public Ord GetById(String id) {
        return ordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void CreateOrderByGoodsAndUserID(int userId, int goodsId) {
        if(OrderExist(userId,goodsId))
        {
            return;
        }
        if (!goodsService.haveStock(goodsId))
        {
            return;
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        Ord newOrd = new Ord(userId,goodsId);
        goods.setGoodsStock(goods.getGoodsStock()-1);
        goodsMapper.updateByPrimaryKeySelective(goods);
        ordMapper.insertSelective(newOrd);
        //update redis
        goodsService.updateModel(goodsId);
    }

    @Override
    @RedisCache(TYPE = SeckillDetailVo.class)
    public SeckillDetailVo getSeckillDetailVo(String id, User user)
    {
        SeckillDetailVo seckillDetailVo= new SeckillDetailVo();
        Ord ord = GetById(id);
        seckillDetailVo.setOrderModel(OrderToModel(ord));
        seckillDetailVo.setGoods(goodsService.GoodsToModel(goodsService.getGoodsById(ord.getGoodsId())));
        seckillDetailVo.setUser(userService.usertoModel(user));
        return seckillDetailVo;
    }

    @Override
    @RedisCache(TYPE = OrderModel.class)
    public OrderModel OrderToModel(Ord ord) {
        return new OrderModel(ord);
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
//    @RedisCache(TYPE = String.class)
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
