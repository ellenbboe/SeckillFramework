package sf.mq;

public class OrdMessage {
    private int userId;
    private int goodsId;

    public OrdMessage() {
    }

    public OrdMessage(int userId, int goodsId) {
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
