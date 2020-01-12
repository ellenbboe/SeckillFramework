package sf.vo;

import sf.model.SeckillGoodsModel;
import sf.model.UserModel;

public class GoodsDetailVo {
    private UserModel user;
    private SeckillGoodsModel goods;
    private long remainSeconds;

    public SeckillGoodsModel getGoods() {
        return goods;
    }

    public void setGoods(SeckillGoodsModel goods) {
        this.goods = goods;
    }

    public long getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(long remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
