package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

/**
 * @author juggist
 * @date 2018/12/11 3:36 PM
 */
public class OrderTmpBean extends BaseBean {
    public OrderTmpBean(String goods_id, String goods_number) {
        this.goods_id = goods_id;
        this.goods_number = goods_number;
    }

    private String goods_id;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    private String goods_number;
}
