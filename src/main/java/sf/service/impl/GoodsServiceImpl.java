package sf.service.impl;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.cache.RedisCache;
import sf.dao.GoodsMapper;
import sf.dao.SeckillGoodsMapper;
import sf.entity.Goods;
import sf.entity.GoodsExample;
import sf.entity.SeckillGoods;
import sf.entity.SeckillGoodsExample;
import sf.model.SeckillGoodsModel;
import sf.redis.RedisKey;
import sf.redis.RedisService;
import sf.redis.StringRedisService;
import sf.service.GoodsService;
import sf.util.UUIDUtil;

import java.util.List;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired(required = false)
    GoodsMapper goodsMapper;
    @Autowired(required = false)
    SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    RedisService redisService;
    @Autowired
    StringRedisService stringRedisService;

    @Override
    @RedisCache(TYPE = List.class)
    public List<Goods> GetGoodsList() {
//        String key = RedisKey.getRedisKey(RedisKey.REDIS_GOODS,RedisKey.REDIS_GOODS_GOODSLIST,"");
//        List<Goods> list = (List<Goods>) redisService.getList(key);
//        if(list!=null)
//        {
//            return list;
//        }
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andIdIsNotNull();
        //        redisService.setList(key,list,RedisKey.REDIS_GOODS_GOODSLISTEXPICETIME);
        return goodsMapper.selectByExample(goodsExample);
    }

    @Override
    @RedisCache(TYPE = Goods.class)
    public Goods getGoodsById(int id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    @RedisCache(TYPE = SeckillGoodsModel.class)
    public SeckillGoodsModel GoodsToModel(Goods goods) {
//        String key = RedisKey.getRedisKey(RedisKey.REDIS_MODEL,RedisKey.REDIS_MODEL_GOODSMODEL,Integer.toString(goods.getId()));
//        SeckillGoodsModel model = redisService.getObj(key,SeckillGoodsModel.class);
//        if(model!=null)
//        {
//            return model;
//        }
        SeckillGoodsExample seckillGoodsExample = new SeckillGoodsExample();
        SeckillGoodsExample.Criteria criteria = seckillGoodsExample.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getId());
        SeckillGoods seckillGoods = seckillGoodsMapper.selectByExample(seckillGoodsExample).get(0);
        //        redisService.setObj(key,model,RedisKey.REDIS_MODEL_EXPICETIME);
        return new SeckillGoodsModel(goods,seckillGoods);
    }

    @Override
    public void updateModel(int goodsId) {
        Object[] objects ={goodsId};
        String key = RedisKey.genKey(GoodsServiceImpl.class.getName(),"GoodsToModel",objects);
        SeckillGoodsModel model = redisService.getObj(key,SeckillGoodsModel.class);
        model.setGoodsStock(model.getGoodsStock()-1);
        redisService.setObj(key,model,RedisKey.REDIS_MODEL_EXPICETIME);
    }

    @Override
    public boolean haveStock(int goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        int stock = goods.getGoodsStock();
        return stock > 0;
    }

    @Override
    @RedisCache(TYPE = String.class)
    public String getRandomPath(int goodsId) {
//        String key = RedisKey.getRedisKey(RedisKey.REDIS_GOODS,RedisKey.REDIS_GOODS_RANDOM_PATH,String.valueOf(goodsId));
//        String value = stringRedisService.getString(key);
//        if(!StringUtils.isEmpty(value))
//        {
//            return value;
//        }
        String value = UUIDUtil.snowFlake();
//        stringRedisService.setString(key,value,RedisKey.REDIS_GOODS_RANDOM_PATHEXPICETIME);
        return value;
    }
}
