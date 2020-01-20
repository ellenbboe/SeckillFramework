package sf.service;

import sf.entity.Ord;
import sf.entity.User;
import sf.model.OrderModel;
import sf.vo.SeckillDetailVo;

public interface OrderService {
    Ord GetById(String id);
    void CreateOrderByGoodsAndUserID(int userId, int goodsId);

    SeckillDetailVo getSeckillDetailVo(String id, User user);

    OrderModel OrderToModel(Ord ord);
    boolean OrderExist(int userId, int goodsId);
    String getOrderByUserIdAndGoodsId(int userId, int goodsId);
}
