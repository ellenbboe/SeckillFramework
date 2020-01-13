package sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.aop.RedisAop;
import sf.dao.GoodsMapper;
import sf.dao.SeckillGoodsMapper;
import sf.entity.Goods;
import sf.entity.GoodsExample;
import sf.entity.SeckillGoods;
import sf.entity.SeckillGoodsExample;
import sf.model.SeckillGoodsModel;
import sf.redis.RedisKey;
import sf.redis.RedisService;
import sf.service.GoodsService;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired(required = false)
    GoodsMapper goodsMapper;
    @Autowired(required = false)
    SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    RedisService redisService;
    @Override
    public List<Goods> GetGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andIdIsNotNull();
        return goodsMapper.selectByExample(goodsExample);
    }

    @Override
    public Goods getGoodsById(int id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    // TODO: 2020/1/12 是否可以使用aop实现
    @Override
    public SeckillGoodsModel GoodsToModel(Goods goods) {
        String key = RedisKey.getRedisKey(RedisKey.REDIS_MODEL,RedisKey.REDIS_MODEL_GOODSMODEL,Integer.toString(goods.getId()));
        SeckillGoodsModel model = (SeckillGoodsModel)redisService.getObj(key);
        if(model!=null)
        {
            return model;
        }
        SeckillGoodsExample seckillGoodsExample = new SeckillGoodsExample();
        SeckillGoodsExample.Criteria criteria = seckillGoodsExample.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getId());
        SeckillGoods seckillGoods = seckillGoodsMapper.selectByExample(seckillGoodsExample).get(0);
        model = new SeckillGoodsModel(goods,seckillGoods);
        redisService.setObj(key,model,RedisKey.REDIS_MODEL_EXPICETIME);
        return model;
    }

}
