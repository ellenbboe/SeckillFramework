package sf.model;

import sf.entity.Goods;
import sf.entity.SeckillGoods;

import java.math.BigDecimal;
import java.util.Date;

public class SeckillGoodsModel {
    private Integer id;

    private String goodsName;

    private String goodsImg;

    private String goodsInfo;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private BigDecimal seckillPrice;

    private Date seckillStarttime;

    private Date seckillEndtime;

    public SeckillGoodsModel() {
    }

    public SeckillGoodsModel(Goods goods, SeckillGoods seckillGoods){
        this.id = goods.getId();
        this.goodsName = goods.getGoodsName();
        this.goodsImg = goods.getGoodsImg();
        this.goodsPrice = goods.getGoodsPrice();
        this.goodsInfo = goods.getGoodsInfo();
        this.goodsStock = goods.getGoodsStock();
        this.seckillPrice = seckillGoods.getSeckillPrice();
        this.seckillStarttime = seckillGoods.getSeckillStarttime();
        this.seckillEndtime = seckillGoods.getSeckillEndtime();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
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
}
