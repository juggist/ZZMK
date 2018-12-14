package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/12/13 5:28 PM
 */
public class OrderCreateBean extends BaseBean {
    private static final long serialVersionUID = 7329037024736616754L;
    /**
     * sn : 153786706281590971
     * total_price : 345.6
     * unified : [{"appid":"wx0502b9a5113cb754","partnerid":"1508175381","prepay_id":"wx251741553931773d02e9c2083368014031","wxpackage":"Sign=WXPay","noncestr":"305fb8eea3e49d9f64aada7d084dc0b2","timestamp":"1537868515","sign":"7A4D0BA338E00640B0072CA2525748C1"}]
     * orders_id : 211
     */

    private String sn;
    private String            total_price;
    private String            orders_id;
    private List<UnifiedBean> unified;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public List<UnifiedBean> getUnified() {
        return unified;
    }

    public void setUnified(List<UnifiedBean> unified) {
        this.unified = unified;
    }

    public static class UnifiedBean extends BaseBean {
        private static final long serialVersionUID = 8674877336710464320L;
        /**
         * appid : wx0502b9a5113cb754
         * partnerid : 1508175381
         * prepay_id : wx251741553931773d02e9c2083368014031
         * wxpackage : Sign=WXPay
         * noncestr : 305fb8eea3e49d9f64aada7d084dc0b2
         * timestamp : 1537868515
         * sign : 7A4D0BA338E00640B0072CA2525748C1
         */



        private String appid;
        private String partnerid;
        private String prepay_id;
        private String wxpackage;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getWxpackage() {
            return wxpackage;
        }

        public void setWxpackage(String wxpackage) {
            this.wxpackage = wxpackage;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

}
