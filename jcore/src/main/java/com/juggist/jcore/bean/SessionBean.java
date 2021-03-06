package com.juggist.jcore.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.juggist.jcore.base.BaseBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/8 4:17 PM
 */
public class SessionBean extends BaseBean {

    /**
     * data : [{"id":"66","auto_id":"66","group_name":"开业大酬宾\u2014\u2014超值特价专场","group_pic":"http://47.106.64.20/store/2018/10/12/47847679727b00d1114fe88017f92.jpg","sort_number":"0","alt":"没有最低，只有更低，超值特价名品抢不停！！！首单赠送开业神秘礼物哦~~~~","now_status":"1","public_time":"1539302400","end_time":"1540944000","goods_number":6,"goods_list":[{"main_pic":"http://47.106.64.20/store/2018/10/12/26314684274ef8cbb97feba2574e0.jpg","price":"99"},{"main_pic":"http://47.106.64.20/store/2018/10/12/530439045bcecfa3c570fdd379e1a.jpg","price":"99"},{"main_pic":"http://47.106.64.20/store/2018/10/12/93499673920c741314bb34d5dce62.jpg","price":"49"},{"main_pic":"http://47.106.64.20/store/2018/10/12/53045466165527db5a5c0e3172fda.jpg","price":"59"},{"main_pic":"http://47.106.64.20/store/2018/10/12/506075745876d5d6e8551c93045b0.jpg","price":"89"}]},{"id":"4","auto_id":"4","group_name":"底妆专区","group_pic":"http://47.106.64.20/store/2018/10/11/5634864709ceef4c89d189b1f909f.jpg","sort_number":"1","alt":"帮你打造夏日轻薄底妆","now_status":"1","public_time":"1539216000","end_time":"1548892800","goods_number":16,"goods_list":[{"main_pic":"http://47.106.64.20/store/2018/10/12/577496319f15741737eeddbd1e5a8.jpg","price":"109"},{"main_pic":"http://47.106.64.20/store/2018/10/12/4379679686fe5fcb1a84eff0af99a.jpg","price":"109"},{"main_pic":"http://47.106.64.20/store/2018/10/12/7678052603b6abf870c56bd75dd1f.jpg","price":"109"},{"main_pic":"http://47.106.64.20/store/2018/10/12/695537762ae758bce2b2c63870832.jpg","price":"99"},{"main_pic":"http://47.106.64.20/store/2018/10/12/964270943c59b1011911e7d4298b3.jpg","price":"69"}]}]
     * data_status : 1
     */

    private int data_status;
    private List<DataBean> data;

    public int getData_status() {
        return data_status;
    }

    public void setData_status(int data_status) {
        this.data_status = data_status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean implements MultiItemEntity {
        /**
         * id : 66
         * auto_id : 66
         * group_name : 开业大酬宾——超值特价专场
         * group_pic : http://47.106.64.20/store/2018/10/12/47847679727b00d1114fe88017f92.jpg
         * sort_number : 0
         * alt : 没有最低，只有更低，超值特价名品抢不停！！！首单赠送开业神秘礼物哦~~~~
         * now_status : 1
         * public_time : 1539302400
         * end_time : 1540944000
         * goods_number : 6
         * goods_list : [{"main_pic":"http://47.106.64.20/store/2018/10/12/26314684274ef8cbb97feba2574e0.jpg","price":"99"},{"main_pic":"http://47.106.64.20/store/2018/10/12/530439045bcecfa3c570fdd379e1a.jpg","price":"99"},{"main_pic":"http://47.106.64.20/store/2018/10/12/93499673920c741314bb34d5dce62.jpg","price":"49"},{"main_pic":"http://47.106.64.20/store/2018/10/12/53045466165527db5a5c0e3172fda.jpg","price":"59"},{"main_pic":"http://47.106.64.20/store/2018/10/12/506075745876d5d6e8551c93045b0.jpg","price":"89"}]
         */

        public static final int ITEM_TOP = 0;
        public static final int ITEM_HOT_BRAND = 1;
        public static final int ITEM_HOT_RECOMMED = 2;
        public static final int ITEM_NORMAL = 3;

        private int position = 0;//自己添加的
        List<DataBean> specialData ;//自己添加的

        private String id;
        private String              auto_id;
        private String              group_name;
        private String              group_pic;
        private String              sort_number;
        private String              alt;
        private String              now_status;
        private String              public_time;
        private String              end_time;
        private int                 goods_number;
        private List<GoodsListBean> goods_list;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public List<DataBean> getSpecialData() {
            return specialData;
        }

        public void setSpecialData(List<DataBean> specialData) {
            this.specialData = specialData;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        private int itemType = ITEM_NORMAL;

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

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getGroup_pic() {
            return group_pic;
        }

        public void setGroup_pic(String group_pic) {
            this.group_pic = group_pic;
        }

        public String getSort_number() {
            return sort_number;
        }

        public void setSort_number(String sort_number) {
            this.sort_number = sort_number;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getNow_status() {
            return now_status;
        }

        public void setNow_status(String now_status) {
            this.now_status = now_status;
        }

        public String getPublic_time() {
            return public_time;
        }

        public void setPublic_time(String public_time) {
            this.public_time = public_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public static class GoodsListBean extends BaseBean{
            /**
             * main_pic : http://47.106.64.20/store/2018/10/12/26314684274ef8cbb97feba2574e0.jpg
             * price : 99
             */

            private String main_pic;
            private String price;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            private String goods_name;

            public String getShoppe() {
                return shoppe;
            }

            public void setShoppe(String shoppe) {
                this.shoppe = shoppe;
            }

            private String shoppe;

            public String getMain_pic() {
                return main_pic;
            }

            public void setMain_pic(String main_pic) {
                this.main_pic = main_pic;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
