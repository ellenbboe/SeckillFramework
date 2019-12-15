package sf.entity;

import java.math.BigDecimal;

public class Goods {
    private Integer id;

    private String goodsName;

    private String goodsImg;

    private String goodsInfo;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private BigDecimal goodsSeckillprice;

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
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg == null ? null : goodsImg.trim();
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo == null ? null : goodsInfo.trim();
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

    public BigDecimal getGoodsSeckillprice() {
        return goodsSeckillprice;
    }

    public void setGoodsSeckillprice(BigDecimal goodsSeckillprice) {
        this.goodsSeckillprice = goodsSeckillprice;
    }
}