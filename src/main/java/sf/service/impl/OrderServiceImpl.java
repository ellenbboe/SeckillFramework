package sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.GoodsMapper;
import sf.dao.OrdMapper;
import sf.entity.Goods;
import sf.entity.Ord;
import sf.model.OrderModel;
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
        Ord newOrd = new Ord(userId,goodsId);
        try{
            ordMapper.insertSelective(newOrd);
            Goods goods = goodsService.getGoodsById(goodsId);
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

}
