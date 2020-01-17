package sf.service;

import sf.entity.Ord;
import sf.model.OrderModel;

public interface OrderService {
    Ord GetById(String id);
    void CreateOrderByGoodsAndUserID(int userId, int goodsId);
    OrderModel OrderToModel(Ord ord);
    boolean OrderExist(int userId, int goodsId);
    String getOrderByUserIdAndGoodsId(int userId, int goodsId);
}
