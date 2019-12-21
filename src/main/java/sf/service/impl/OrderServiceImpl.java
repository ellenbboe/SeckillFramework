package sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.GoodsMapper;
import sf.dao.OrdMapper;
import sf.entity.Goods;
import sf.entity.Ord;
import sf.entity.OrdExample;
import sf.exception.BaseException;
import sf.model.OrderModel;
import sf.result.CodeMsg;
import sf.service.GoodsService;
import sf.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    OrdMapper ordMapper;
    @Autowired(required = false)
    GoodsMapper goodsMapper;
    @Autowired
    GoodsService goodsService;
    @Override
    public Ord GetById(String id) {
        return ordMapper.selectByPrimaryKey(id);
    }

    @Override
    public Ord CreateOrderByGoodsAndUserID(int userId, int goodsId) {
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
            ordMapper.insertSelective(newOrd);
            goods.setGoodsStock(goods.getGoodsStock()-1);
            goodsMapper.updateByPrimaryKeySelective(goods);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return newOrd;
    }

    @Override
    public OrderModel OrderToModel(Ord ord) {
        return new OrderModel(ord);
    }

    public boolean OrderExist(int userId,int goodsId)
    {
        OrdExample ordExample = new OrdExample();
        OrdExample.Criteria criteria = ordExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andUserIdEqualTo(userId);
        return ordMapper.selectByExample(ordExample).get(0) != null;
    }
}
