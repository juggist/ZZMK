package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/13 4:28 PM
 */
public class OrderBean extends BaseBean {
    private String order_id;
    private String              create_time;
    private String              now_status;
    private int                 refund_status;
    private String              cost;
    private int                 refund_id;
    private String              status_name;
    private List<GoodsListBean> goods_list;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getNow_status() {
        return now_status;
    }

    public void setNow_status(String now_status) {
        this.now_status = now_status;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(int refund_id) {
        this.refund_id = refund_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean extends BaseBean  {
        private static final long serialVersionUID = 5363053770666841210L;
        /**
         * id : 190
         * auto_id : 190
         * goods_id : 54
         * goods_name : flaviaBB护理粉底液 粉白色 30g
         * orders_id : 329
         * goods_number : 1
         * goods_price : 450
         * attr : [{"attr":"规格","attrvalue":"150ml"},{"attr":"规格","attrvalue":"300ml"},{"attr":"规格","attrvalue":"500ml"},{"attr":"规格","attrvalue":"200ml"},{"attr":"规格","attrvalue":"50g"}]
         * goods_pic : http://47.106.64.20/store/2018/10/10/4698281046d5a8e438af76e7d56c4.jpg
         */

        private String id;
        private String         auto_id;
        private String         goods_id;
        private String         goods_name;
        private String         orders_id;
        private String         goods_number;
        private String         goods_price;
        private String         goods_pic;
        private List<AttrBean> attr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuto_id() {
            return auto_id;
        }

        public void setAuto_id(String auto_id) {
            this.auto_id = auto_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(String orders_id) {
            this.orders_id = orders_id;
        }

        public String getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(String goods_number) {
            this.goods_number = goods_number;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(String goods_pic) {
            this.goods_pic = goods_pic;
        }

        public List<AttrBean> getAttr() {
            return attr;
        }

        public void setAttr(List<AttrBean> attr) {
            this.attr = attr;
        }

        public static class AttrBean extends BaseBean{
            private static final long serialVersionUID = 3418053022801683602L;
            /**
             * attr : 规格
             * attrvalue : 150ml
             */

            private String attr;
            private String attrvalue;

            public String getAttr() {
                return attr;
            }

            public void setAttr(String attr) {
                this.attr = attr;
            }

            public String getAttrvalue() {
                return attrvalue;
            }

            public void setAttrvalue(String attrvalue) {
                this.attrvalue = attrvalue;
            }
        }
    }
}
