package sf.model;

import sf.entity.Ord;

import java.util.Date;

public class OrderModel {
    private String id;
    private Date createDate;
    private Integer orderStatus;

    public OrderModel(Ord ord) {
        this.id = ord.getId();
        this.createDate = ord.getOrdTime();
        this.orderStatus = ord.getOrdStatus();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
