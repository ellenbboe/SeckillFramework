package sf.entity;

import sf.util.UUIDUtil;

import java.util.Date;

public class Ord {
    private String id;

    private Integer userId;

    private Integer goodsId;

    private Integer ordStatus;

    private Date ordTime;

    private Date payTime;
    public Ord(){}
    public Ord(Integer userId, Integer goodsId) {
        this.id = UUIDUtil.snowFlake();
        this.userId = userId;
        this.goodsId = goodsId;
        this.ordStatus = 0;
        this.ordTime = new Date();
        this.payTime = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
    }

    public Date getOrdTime() {
        return ordTime;
    }

    public void setOrdTime(Date ordTime) {
        this.ordTime = ordTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}