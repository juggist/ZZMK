package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/9 5:11 PM
 */
public class ProductBean extends BaseBean {
    /**
     * data : {"group_info":{"group_name":"开业大酬宾\u2014\u2014超值特价专场"},"goods_list":[{"goods_id":"115","goods_name":"韩国UNNY悠宜优宜网红卸妆水","price":"99","user_price":"99","wholesale_price":"48","sn":"MK201810120059","main_pic":["http://47.106.64.20/store/2018/10/12/26314684274ef8cbb97feba2574e0.jpg","http://47.106.64.20/store/2018/10/12/21760447687ed795d3074da5b65fa.jpg","http://47.106.64.20/store/2018/10/12/847668344bcb10abdd7085f79e151.jpg","http://47.106.64.20/store/2018/10/12/4605808625989575021f8200a154a.jpg"],"attr":[{"id":"785","auto_id":"785","attrname":"规格","model_id":null,"goods_id":"115","value":[{"id":"759","auto_id":"759","attr_id":"785","content":"500ml","is_default":"0","goods_id":"115"}]},{"id":"786","auto_id":"786","attrname":null,"model_id":null,"goods_id":"115","value":[]}]},{"goods_id":"116","goods_name":"韩国JMsolution 海洋珍珠面膜三部曲","price":"99","user_price":"99","wholesale_price":"56","sn":"MK201810120060","main_pic":["http://47.106.64.20/store/2018/10/12/530439045bcecfa3c570fdd379e1a.jpg","http://47.106.64.20/store/2018/10/12/28140292621c74862fc7c9e36f9c3.jpg","http://47.106.64.20/store/2018/10/12/618719478ff1005d5d6c724a4672f.jpg","http://47.106.64.20/store/2018/10/12/621278121e6393da5e9e268f835da.jpg"],"attr":[{"id":"783","auto_id":"783","attrname":"规格","model_id":null,"goods_id":"116","value":[{"id":"758","auto_id":"758","attr_id":"783","content":"30ml*10片/盒 ","is_default":"0","goods_id":"116"}]},{"id":"784","auto_id":"784","attrname":null,"model_id":null,"goods_id":"116","value":[]}]},{"goods_id":"35","goods_name":"尤妮佳1/2省水化妆棉","price":"49","user_price":"49","wholesale_price":"18","sn":"MK201810100006","main_pic":["http://47.106.64.20/store/2018/10/12/93499673920c741314bb34d5dce62.jpg","http://47.106.64.20/store/2018/10/12/2178185470cd98a5cea03beea8062.jpg","http://47.106.64.20/store/2018/10/12/26569906bc1085c57812c077dae8.jpg","http://47.106.64.20/store/2018/10/12/928857479293e34bdb49f400603d1.jpg"],"attr":[{"id":"622","auto_id":"622","attrname":"规格","model_id":null,"goods_id":"35","value":[{"id":"663","auto_id":"663","attr_id":"622","content":"40枚/盒","is_default":"0","goods_id":"35"}]},{"id":"623","auto_id":"623","attrname":null,"model_id":null,"goods_id":"35","value":[]}]},{"goods_id":"174","goods_name":"DHC 蝶翠诗纯榄护唇膏 1.5g","price":"59","user_price":"59","wholesale_price":"43","sn":"MK201810120118","main_pic":["http://47.106.64.20/store/2018/10/12/53045466165527db5a5c0e3172fda.jpg","http://47.106.64.20/store/2018/10/12/2962991355c0f3cca65c1c9b812bd.jpg","http://47.106.64.20/store/2018/10/12/191179070c64e2c93dc4f106a2c65.jpg","http://47.106.64.20/store/2018/10/12/735411300db3583b2d0ccdf05de74.jpg"],"attr":[{"id":"787","auto_id":"787","attrname":"规格","model_id":null,"goods_id":"174","value":[{"id":"760","auto_id":"760","attr_id":"787","content":"1.5g","is_default":"0","goods_id":"174"}]},{"id":"788","auto_id":"788","attrname":null,"model_id":null,"goods_id":"174","value":[]}]},{"goods_id":"155","goods_name":"韩国九朵云CLOUD9 淡化色斑面霜","price":"89","user_price":"89","wholesale_price":"65","sn":"MK201810120099","main_pic":["http://47.106.64.20/store/2018/10/12/506075745876d5d6e8551c93045b0.jpg","http://47.106.64.20/store/2018/10/12/2759916825b94b952f1f221344127.jpg","http://47.106.64.20/store/2018/10/12/8299540470cf6fb565b9f4992fbba.jpg","http://47.106.64.20/store/2018/10/12/155630130f4b3185fc2f90b13ddbf.jpg"],"attr":[{"id":"435","auto_id":"435","attrname":"规格","model_id":null,"goods_id":"155","value":[{"id":"521","auto_id":"521","attr_id":"435","content":"50ml","is_default":"0","goods_id":"155"}]}]},{"goods_id":"109","goods_name":"【2瓶】日本城野医生毛孔细致化妆水","price":"199","user_price":"199","wholesale_price":"166","sn":"MK201810110054","main_pic":["http://47.106.64cf4.jpg","http://47.106.64.20/store/2018/10/11/223625942e1112a0f23e4b83a70d.jpg","http://47.106.64.20/store/2018/10/11/205366231c99b1b566ef6c880f439.jpg"],"attr":[{"id":"789","auto_id":"789","attrname":"规格","model_id":null,"goods_id":"109","value":[{"id":"761","auto_id":"761","attr_id":"789","content":"100ml/支","is_default":"0","goods_id":"109"}]},{"id":"790","auto_id":"790","attrname":null,"model_id":null,"goods_id":"109","value":[]}]}]}
     * data_status : 0
     */

    private DataBean data;
    private int data_status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getData_status() {
        return data_status;
    }

    public void setData_status(int data_status) {
        this.data_status = data_status;
    }

    public static class DataBean extends BaseBean{
        /**
         * group_info : {"group_name":"开业大酬宾\u2014\u2014超值特价专场"}
         * goods_list : [{"goods_id":"115","goods_name":"韩国UNNY悠宜优宜网红卸妆水","price":"99","user_price":"99","wholesale_price":"48","sn":"MK201810120059","main_pic":["http://47.106.64.20/store/2018/10/12/26314684274ef8cbb97feba2574e0.jpg","http://47.106.64.20/store/2018/10/12/21760447687ed795d3074da5b65fa.jpg","http://47.106.64.20/store/2018/10/12/847668344bcb10abdd7085f79e151.jpg","http://47.106.64.20/store/2018/10/12/4605808625989575021f8200a154a.jpg"],"attr":[{"id":"785","auto_id":"785","attrname":"规格","model_id":null,"goods_id":"115","value":[{"id":"759","auto_id":"759","attr_id":"785","content":"500ml","is_default":"0","goods_id":"115"}]},{"id":"786","auto_id":"786","attrname":null,"model_id":null,"goods_id":"115","value":[]}]},{"goods_id":"116","goods_name":"韩国JMsolution 海洋珍珠面膜三部曲","price":"99","user_price":"99","wholesale_price":"56","sn":"MK201810120060","main_pic":["http://47.106.64.20/store/2018/10/12/530439045bcecfa3c570fdd379e1a.jpg","http://47.106.64.20/store/2018/10/12/28140292621c74862fc7c9e36f9c3.jpg","http://47.106.64.20/store/2018/10/12/618719478ff1005d5d6c724a4672f.jpg","http://47.106.64.20/store/2018/10/12/621278121e6393da5e9e268f835da.jpg"],"attr":[{"id":"783","auto_id":"783","attrname":"规格","model_id":null,"goods_id":"116","value":[{"id":"758","auto_id":"758","attr_id":"783","content":"30ml*10片/盒 ","is_default":"0","goods_id":"116"}]},{"id":"784","auto_id":"784","attrname":null,"model_id":null,"goods_id":"116","value":[]}]},{"goods_id":"35","goods_name":"尤妮佳1/2省水化妆棉","price":"49","user_price":"49","wholesale_price":"18","sn":"MK201810100006","main_pic":["http://47.106.64.20/store/2018/10/12/93499673920c741314bb34d5dce62.jpg","http://47.106.64.20/store/2018/10/12/2178185470cd98a5cea03beea8062.jpg","http://47.106.64.20/store/2018/10/12/26569906bc1085c57812c077dae8.jpg","http://47.106.64.20/store/2018/10/12/928857479293e34bdb49f400603d1.jpg"],"attr":[{"id":"622","auto_id":"622","attrname":"规格","model_id":null,"goods_id":"35","value":[{"id":"663","auto_id":"663","attr_id":"622","content":"40枚/盒","is_default":"0","goods_id":"35"}]},{"id":"623","auto_id":"623","attrname":null,"model_id":null,"goods_id":"35","value":[]}]},{"goods_id":"174","goods_name":"DHC 蝶翠诗纯榄护唇膏 1.5g","price":"59","user_price":"59","wholesale_price":"43","sn":"MK201810120118","main_pic":["http://47.106.64.20/store/2018/10/12/53045466165527db5a5c0e3172fda.jpg","http://47.106.64.20/store/2018/10/12/2962991355c0f3cca65c1c9b812bd.jpg","http://47.106.64.20/store/2018/10/12/191179070c64e2c93dc4f106a2c65.jpg","http://47.106.64.20/store/2018/10/12/735411300db3583b2d0ccdf05de74.jpg"],"attr":[{"id":"787","auto_id":"787","attrname":"规格","model_id":null,"goods_id":"174","value":[{"id":"760","auto_id":"760","attr_id":"787","content":"1.5g","is_default":"0","goods_id":"174"}]},{"id":"788","auto_id":"788","attrname":null,"model_id":null,"goods_id":"174","value":[]}]},{"goods_id":"155","goods_name":"韩国九朵云CLOUD9 淡化色斑面霜","price":"89","user_price":"89","wholesale_price":"65","sn":"MK201810120099","main_pic":["http://47.106.64.20/store/2018/10/12/506075745876d5d6e8551c93045b0.jpg","http://47.106.64.20/store/2018/10/12/2759916825b94b952f1f221344127.jpg","http://47.106.64.20/store/2018/10/12/8299540470cf6fb565b9f4992fbba.jpg","http://47.106.64.20/store/2018/10/12/155630130f4b3185fc2f90b13ddbf.jpg"],"attr":[{"id":"435","auto_id":"435","attrname":"规格","model_id":null,"goods_id":"155","value":[{"id":"521","auto_id":"521","attr_id":"435","content":"50ml","is_default":"0","goods_id":"155"}]}]},{"goods_id":"109","goods_name":"【2瓶】日本城野医生毛孔细致化妆水","price":"199","user_price":"199","wholesale_price":"166","sn":"MK201810110054","main_pic":["http://47.106.64cf4.jpg","http://47.106.64.20/store/2018/10/11/223625942e1112a0f23e4b83a70d.jpg","http://47.106.64.20/store/2018/10/11/205366231c99b1b566ef6c880f439.jpg"],"attr":[{"id":"789","auto_id":"789","attrname":"规格","model_id":null,"goods_id":"109","value":[{"id":"761","auto_id":"761","attr_id":"789","content":"100ml/支","is_default":"0","goods_id":"109"}]},{"id":"790","auto_id":"790","attrname":null,"model_id":null,"goods_id":"109","value":[]}]}]
         */

        private GroupInfoBean group_info;
        private List<GoodsListBean> goods_list;

        public GroupInfoBean getGroup_info() {
            return group_info;
        }

        public void setGroup_info(GroupInfoBean group_info) {
            this.group_info = group_info;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GroupInfoBean extends BaseBean{
            /**
             * group_name : 开业大酬宾——超值特价专场
             */

            private String group_name;

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }
        }

        public static class GoodsListBean extends BaseBean{
            /**
             * goods_id : 115
             * goods_name : 韩国UNNY悠宜优宜网红卸妆水
             * price : 99
             * user_price : 99
             * wholesale_price : 48
             * sn : MK201810120059
             * main_pic : ["http://47.106.64.20/store/2018/10/12/26314684274ef8cbb97feba2574e0.jpg","http://47.106.64.20/store/2018/10/12/21760447687ed795d3074da5b65fa.jpg","http://47.106.64.20/store/2018/10/12/847668344bcb10abdd7085f79e151.jpg","http://47.106.64.20/store/2018/10/12/4605808625989575021f8200a154a.jpg"]
             * attr : [{"id":"785","auto_id":"785","attrname":"规格","model_id":null,"goods_id":"115","value":[{"id":"759","auto_id":"759","attr_id":"785","content":"500ml","is_default":"0","goods_id":"115"}]},{"id":"786","auto_id":"786","attrname":null,"model_id":null,"goods_id":"115","value":[]}]
             */

            private String goods_id;
            private String         goods_name;
            private String         price;
            private String         user_price;
            private String         wholesale_price;
            private String mail_type;
            private String mail_type_name;
            private String shoppe;
            private String         sn;
            private List<String>   main_pic;
            private List<AttrBean> attr;

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

            public String getWholesale_price() {
                return wholesale_price;
            }

            public void setWholesale_price(String wholesale_price) {
                this.wholesale_price = wholesale_price;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public List<String> getMain_pic() {
                return main_pic;
            }

            public void setMain_pic(List<String> main_pic) {
                this.main_pic = main_pic;
            }

            public List<AttrBean> getAttr() {
                return attr;
            }

            public void setAttr(List<AttrBean> attr) {
                this.attr = attr;
            }

            public String getMail_type() {
                return mail_type;
            }

            public void setMail_type(String mail_type) {
                this.mail_type = mail_type;
            }

            public String getMail_type_name() {
                return mail_type_name;
            }

            public void setMail_type_name(String mail_type_name) {
                this.mail_type_name = mail_type_name;
            }

            public String getShoppe() {
                return shoppe;
            }

            public void setShoppe(String shoppe) {
                this.shoppe = shoppe;
            }

            public static class AttrBean extends BaseBean{
                /**
                 * id : 785
                 * auto_id : 785
                 * attrname : 规格
                 * model_id : null
                 * goods_id : 115
                 * value : [{"id":"759","auto_id":"759","attr_id":"785","content":"500ml","is_default":"0","goods_id":"115"}]
                 */

                private String id;
                private String          auto_id;
                private String          attrname;
                private Object          model_id;
                private String          goods_id;
                private List<ValueBean> value;

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

                public String getAttrname() {
                    return attrname;
                }

                public void setAttrname(String attrname) {
                    this.attrname = attrname;
                }

                public Object getModel_id() {
                    return model_id;
                }

                public void setModel_id(Object model_id) {
                    this.model_id = model_id;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public List<ValueBean> getValue() {
                    return value;
                }

                public void setValue(List<ValueBean> value) {
                    this.value = value;
                }

                public static class ValueBean extends BaseBean{
                    /**
                     * id : 759
                     * auto_id : 759
                     * attr_id : 785
                     * content : 500ml
                     * is_default : 0
                     * goods_id : 115
                     */

                    private String id;
                    private String auto_id;
                    private String attr_id;
                    private String content;
                    private String is_default;
                    private String goods_id;

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

                    public String getAttr_id() {
                        return attr_id;
                    }

                    public void setAttr_id(String attr_id) {
                        this.attr_id = attr_id;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getIs_default() {
                        return is_default;
                    }

                    public void setIs_default(String is_default) {
                        this.is_default = is_default;
                    }

                    public String getGoods_id() {
                        return goods_id;
                    }

                    public void setGoods_id(String goods_id) {
                        this.goods_id = goods_id;
                    }
                }
            }
        }
    }
}
