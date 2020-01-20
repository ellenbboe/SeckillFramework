package sf.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SeckillGoods {
    private Integer id;

    private Integer goodsId;

    private BigDecimal seckillPrice;

    private Date seckillStarttime;

    private Date seckillEndtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Date getSeckillStarttime() {
        return seckillStarttime;
    }

    public void setSeckillStarttime(Date seckillStarttime) {
        this.seckillStarttime = seckillStarttime;
    }

    public Date getSeckillEndtime() {
        return seckillEndtime;
    }

    public void setSeckillEndtime(Date seckillEndtime) {
        this.seckillEndtime = seckillEndtime;
    }

    @Override
    public String toString() {
        return "seckillId:"+id;
    }
}