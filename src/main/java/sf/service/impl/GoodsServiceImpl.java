package sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.GoodsMapper;
import sf.entity.Goods;
import sf.entity.GoodsExample;
import sf.service.GoodsService;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired(required = false)
    GoodsMapper goodsMapper;
    @Override
    public List<Goods> GetGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andIdIsNotNull();
        return goodsMapper.selectByExample(goodsExample);
    }
}
