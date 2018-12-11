package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/12/11 3:23 PM
 * 预备订单
 */
public class OrderPreBean extends BaseBean {
    /**
     * consignee : 方法
     * address : {"id":"2","auto_id":"2","user_id":"1","addr":"不不不不","is_default":"1","province":"130000","city":"130100","areas":"130101","province_name":"河北省","city_name":"石家庄市","areas_name":"市辖区","consignee":"方法","cellphone":"123456"}
     * cellphone : 123456
     * available_coupon : [{"id":"18","auto_id":"18","coupon_name":"1元优惠券","discount":"","unlimit":"1","order_limit":"2","useful_start":"1537372800","useful_end":"1538496000","hand_send":null,"self_get":null,"activity_gifts":null,"total_number":null,"receive":"1","remainder":null,"conditions":"1","member_type":"2,3,4","used":"0","image_normal":"http://47.106.64.20/store/2018/09/20/204889722ef361f13511f4193dc88.jpg","image_expire":"http://47.106.64.20/store/2018/09/20/801357947d54d875ff7f44829961c.jpg","image_used":"http://47.106.64.20/store/2018/09/20/79092560497efc52f2863e7605b3a.jpg","coupon_type":"1","derate":"1","distribute_id":"0A873086254822EEFC05"},{"id":"19","auto_id":"19","coupon_name":"2元优惠券","discount":"","unlimit":"1","order_limit":"3","useful_start":"1537372800","useful_end":"1538323200","hand_send":null,"self_get":null,"activity_gifts":null,"total_number":null,"receive":"1","remainder":null,"conditions":"1","member_type":"4,3,2","used":"0","image_normal":"http://47.106.64.20/store/2018/09/20/9712538285915987c16d38f71be78.jpg","image_expire":"http://47.106.64.20/store/2018/09/20/685635971541ae0ff6451bb0671d8.jpg","image_used":"http://47.106.64.20/store/2018/09/20/8048277998aedcd1681cb5f0db064.jpg","coupon_type":"1","derate":"2","distribute_id":"74956BC5A24E671A2137"}]
     * user_info : 1
     * total_price : 160.00
     * goods_list : [{"auto_id":"10","goods_name":"香奈儿香水","price":"200","mail_type":"1","user_price":"160.00","user_price_level":"160.00","attr":[{"attr_name":"色号","value":"100"},{"attr_name":"色调","value":"10"}],"main_pic":"http://47.106.64.20/store/2018/09/19/9635926856414b237f20cd5b76a2e.jpg","goods_number":"1"}]
     */

    private String consignee;
    private AddressBean               address;
    private String                    cellphone;
    private int                       user_info;
    private String                    total_price;
    private List<AvailableCouponBean> available_coupon;
    private List<GoodsListBean>       goods_list;

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getUser_info() {
        return user_info;
    }

    public void setUser_info(int user_info) {
        this.user_info = user_info;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public List<AvailableCouponBean> getAvailable_coupon() {
        return available_coupon;
    }

    public void setAvailable_coupon(List<AvailableCouponBean> available_coupon) {
        this.available_coupon = available_coupon;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class AddressBean extends BaseBean {
        private static final long serialVersionUID = -560397977415762107L;
        /**
         * id : 2
         * auto_id : 2
         * user_id : 1
         * addr : 不不不不
         * is_default : 1
         * province : 130000
         * city : 130100
         * areas : 130101
         * province_name : 河北省
         * city_name : 石家庄市
         * areas_name : 市辖区
         * consignee : 方法
         * cellphone : 123456
         */

        private String id;
        private String auto_id;
        private String user_id;
        private String addr;
        private String is_default;
        private String province;
        private String city;
        private String areas;
        private String province_name;
        private String city_name;
        private String areas_name;
        private String consignee;
        private String cellphone;

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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAreas() {
            return areas;
        }

        public void setAreas(String areas) {
            this.areas = areas;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getAreas_name() {
            return areas_name;
        }

        public void setAreas_name(String areas_name) {
            this.areas_name = areas_name;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }
    }

    public static class AvailableCouponBean extends BaseBean {
        private static final long serialVersionUID = 8600552302542725963L;
        /**
         * id : 18
         * auto_id : 18
         * coupon_name : 1元优惠券
         * discount :
         * unlimit : 1
         * order_limit : 2
         * useful_start : 1537372800
         * useful_end : 1538496000
         * hand_send : null
         * self_get : null
         * activity_gifts : null
         * total_number : null
         * receive : 1
         * remainder : null
         * conditions : 1
         * member_type : 2,3,4
         * used : 0
         * image_normal : http://47.106.64.20/store/2018/09/20/204889722ef361f13511f4193dc88.jpg
         * image_expire : http://47.106.64.20/store/2018/09/20/801357947d54d875ff7f44829961c.jpg
         * image_used : http://47.106.64.20/store/2018/09/20/79092560497efc52f2863e7605b3a.jpg
         * coupon_type : 1
         * derate : 1
         * distribute_id : 0A873086254822EEFC05
         */

        private String id;
        private String auto_id;
        private String coupon_name;
        private String discount;
        private String unlimit;
        private String order_limit;
        private String useful_start;
        private String useful_end;
        private Object hand_send;
        private Object self_get;
        private Object activity_gifts;
        private Object total_number;
        private String receive;
        private Object remainder;
        private String conditions;
        private String member_type;
        private String used;
        private String image_normal;
        private String image_expire;
        private String image_used;
        private String coupon_type;
        private String derate;
        private String distribute_id;

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

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getUnlimit() {
            return unlimit;
        }

        public void setUnlimit(String unlimit) {
            this.unlimit = unlimit;
        }

        public String getOrder_limit() {
            return order_limit;
        }

        public void setOrder_limit(String order_limit) {
            this.order_limit = order_limit;
        }

        public String getUseful_start() {
            return useful_start;
        }

        public void setUseful_start(String useful_start) {
            this.useful_start = useful_start;
        }

        public String getUseful_end() {
            return useful_end;
        }

        public void setUseful_end(String useful_end) {
            this.useful_end = useful_end;
        }

        public Object getHand_send() {
            return hand_send;
        }

        public void setHand_send(Object hand_send) {
            this.hand_send = hand_send;
        }

        public Object getSelf_get() {
            return self_get;
        }

        public void setSelf_get(Object self_get) {
            this.self_get = self_get;
        }

        public Object getActivity_gifts() {
            return activity_gifts;
        }

        public void setActivity_gifts(Object activity_gifts) {
            this.activity_gifts = activity_gifts;
        }

        public Object getTotal_number() {
            return total_number;
        }

        public void setTotal_number(Object total_number) {
            this.total_number = total_number;
        }

        public String getReceive() {
            return receive;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }

        public Object getRemainder() {
            return remainder;
        }

        public void setRemainder(Object remainder) {
            this.remainder = remainder;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public String getMember_type() {
            return member_type;
        }

        public void setMember_type(String member_type) {
            this.member_type = member_type;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getImage_normal() {
            return image_normal;
        }

        public void setImage_normal(String image_normal) {
            this.image_normal = image_normal;
        }

        public String getImage_expire() {
            return image_expire;
        }

        public void setImage_expire(String image_expire) {
            this.image_expire = image_expire;
        }

        public String getImage_used() {
            return image_used;
        }

        public void setImage_used(String image_used) {
            this.image_used = image_used;
        }

        public String getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(String coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getDerate() {
            return derate;
        }

        public void setDerate(String derate) {
            this.derate = derate;
        }

        public String getDistribute_id() {
            return distribute_id;
        }

        public void setDistribute_id(String distribute_id) {
            this.distribute_id = distribute_id;
        }
    }

    public static class GoodsListBean extends BaseBean{
        private static final long serialVersionUID = 3598724989800582658L;
        /**
         * auto_id : 10
         * goods_name : 香奈儿香水
         * price : 200
         * mail_type : 1
         * user_price : 160.00
         * user_price_level : 160.00
         * attr : [{"attr_name":"色号","value":"100"},{"attr_name":"色调","value":"10"}]
         * main_pic : http://47.106.64.20/store/2018/09/19/9635926856414b237f20cd5b76a2e.jpg
         * goods_number : 1
         */

        private String auto_id;
        private String         goods_name;
        private String         price;
        private String         mail_type;
        private String         user_price;
        private String         user_price_level;
        private String         main_pic;
        private String         goods_number;
        private List<AttrBean> attr;

        public String getAuto_id() {
            return auto_id;
        }

        public void setAuto_id(String auto_id) {
            this.auto_id = auto_id;
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

        public String getMail_type() {
            return mail_type;
        }

        public void setMail_type(String mail_type) {
            this.mail_type = mail_type;
        }

        public String getUser_price() {
            return user_price;
        }

        public void setUser_price(String user_price) {
            this.user_price = user_price;
        }

        public String getUser_price_level() {
            return user_price_level;
        }

        public void setUser_price_level(String user_price_level) {
            this.user_price_level = user_price_level;
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

        public List<AttrBean> getAttr() {
            return attr;
        }

        public void setAttr(List<AttrBean> attr) {
            this.attr = attr;
        }

        public static class AttrBean extends BaseBean {
            private static final long serialVersionUID = -2512418960860760664L;
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
        }
    }
}
