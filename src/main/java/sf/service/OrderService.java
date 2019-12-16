package sf.service;

import sf.entity.Ord;
import sf.model.OrderModel;

public interface OrderService {
    Ord GetById(String id);
    Ord CreateOrderByGoodsAndUserID(int userId, int goodsId);
    OrderModel OrderToModel(Ord ord);
}
