package com.juggist.jcore.bean;

import android.os.Build;

import com.juggist.jcore.base.BaseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author juggist
 * @date 2018/11/12 1:55 PM
 */
public class ShopCarBean extends BaseBean {
    /**
     * goods_name : 雅诗兰黛111
     * price : 111
     * user_price : 88.80
     * attr : [{"attr_name":"色号","value":"100"},{"attr_name":"色调","value":"10"}]
     * main_pic : http://47.106.64.20/store/2018/09/17/66285170462791e748d95039ce158.jpg
     * goods_number : 8
     * goods_id : 9
     */

    private String goods_name;
    private String         price;
    private String         user_price;
    private String         main_pic;
    private String         goods_number;
    private String         goods_id;
    private ArrayList<AttrBean> attr;
    private Boolean checked = false;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_price() {
        return user_price;
    }

    public void setUser_price(String user_price) {
        this.user_price = user_price;
    }

    public String getMain_pic() {
        return main_pic;
    }

    public void setMain_pic(String main_pic) {
        this.main_pic = main_pic;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public List<AttrBean> getAttr() {
        return attr;
    }

    public void setAttr(ArrayList<AttrBean> attr) {
        this.attr = attr;
    }

    public static class AttrBean  extends BaseBean{
        /**
         * attr_name : 色号
         * value : 100
         */

        private String attr_name;
        private String value;

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return "AttrBean{" +
                    "attr_name='" + attr_name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopCarBean that = (ShopCarBean) o;
        return Objects.equals(goods_id, that.goods_id);
    }

    @Override
    public String toString() {
        return "ShopCarBean{" +
                "goods_name='" + goods_name + '\'' +
                ", price='" + price + '\'' +
                ", user_price='" + user_price + '\'' +
                ", main_pic='" + main_pic + '\'' +
                ", goods_number='" + goods_number + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", attr=" + attr +
                ", checked=" + checked +
                '}';
    }
}
