package sf.vo;

import sf.model.OrderModel;
import sf.model.SeckillGoodsModel;
import sf.model.UserModel;

public class SeckillDetailVo {
    private UserModel user;
    private OrderModel orderModel;
    private SeckillGoodsModel goods;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public SeckillGoodsModel getGoods() {
        return goods;
    }

    public void setGoods(SeckillGoodsModel goods) {
        this.goods = goods;
    }
}
