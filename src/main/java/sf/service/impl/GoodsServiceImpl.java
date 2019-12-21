package sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.GoodsMapper;
import sf.dao.SeckillGoodsMapper;
import sf.entity.Goods;
import sf.entity.GoodsExample;
import sf.entity.SeckillGoods;
import sf.entity.SeckillGoodsExample;
import sf.model.SeckillGoodsModel;
import sf.service.GoodsService;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired(required = false)
    GoodsMapper goodsMapper;
    @Autowired(required = false)
    SeckillGoodsMapper seckillGoodsMapper;
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

    @Override
    public SeckillGoodsModel GoodsToModel(Goods goods) {
        SeckillGoodsExample seckillGoodsExample = new SeckillGoodsExample();
        SeckillGoodsExample.Criteria criteria = seckillGoodsExample.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getId());
        SeckillGoods seckillGoods = seckillGoodsMapper.selectByExample(seckillGoodsExample).get(0);
        return new SeckillGoodsModel(goods,seckillGoods);
    }

}
