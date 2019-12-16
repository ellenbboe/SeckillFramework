package sf.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeckillGoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SeckillGoodsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(Integer value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(Integer value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(Integer value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(Integer value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<Integer> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<Integer> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceIsNull() {
            addCriterion("seckill_price is null");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceIsNotNull() {
            addCriterion("seckill_price is not null");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceEqualTo(BigDecimal value) {
            addCriterion("seckill_price =", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceNotEqualTo(BigDecimal value) {
            addCriterion("seckill_price <>", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceGreaterThan(BigDecimal value) {
            addCriterion("seckill_price >", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("seckill_price >=", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceLessThan(BigDecimal value) {
            addCriterion("seckill_price <", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("seckill_price <=", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceIn(List<BigDecimal> values) {
            addCriterion("seckill_price in", values, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceNotIn(List<BigDecimal> values) {
            addCriterion("seckill_price not in", values, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("seckill_price between", value1, value2, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("seckill_price not between", value1, value2, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeIsNull() {
            addCriterion("seckill_starttime is null");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeIsNotNull() {
            addCriterion("seckill_starttime is not null");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeEqualTo(Date value) {
            addCriterion("seckill_starttime =", value, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeNotEqualTo(Date value) {
            addCriterion("seckill_starttime <>", value, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeGreaterThan(Date value) {
            addCriterion("seckill_starttime >", value, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("seckill_starttime >=", value, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeLessThan(Date value) {
            addCriterion("seckill_starttime <", value, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeLessThanOrEqualTo(Date value) {
            addCriterion("seckill_starttime <=", value, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeIn(List<Date> values) {
            addCriterion("seckill_starttime in", values, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeNotIn(List<Date> values) {
            addCriterion("seckill_starttime not in", values, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeBetween(Date value1, Date value2) {
            addCriterion("seckill_starttime between", value1, value2, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillStarttimeNotBetween(Date value1, Date value2) {
            addCriterion("seckill_starttime not between", value1, value2, "seckillStarttime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeIsNull() {
            addCriterion("seckill_endtime is null");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeIsNotNull() {
            addCriterion("seckill_endtime is not null");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeEqualTo(Date value) {
            addCriterion("seckill_endtime =", value, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeNotEqualTo(Date value) {
            addCriterion("seckill_endtime <>", value, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeGreaterThan(Date value) {
            addCriterion("seckill_endtime >", value, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("seckill_endtime >=", value, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeLessThan(Date value) {
            addCriterion("seckill_endtime <", value, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeLessThanOrEqualTo(Date value) {
            addCriterion("seckill_endtime <=", value, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeIn(List<Date> values) {
            addCriterion("seckill_endtime in", values, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeNotIn(List<Date> values) {
            addCriterion("seckill_endtime not in", values, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeBetween(Date value1, Date value2) {
            addCriterion("seckill_endtime between", value1, value2, "seckillEndtime");
            return (Criteria) this;
        }

        public Criteria andSeckillEndtimeNotBetween(Date value1, Date value2) {
            addCriterion("seckill_endtime not between", value1, value2, "seckillEndtime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}