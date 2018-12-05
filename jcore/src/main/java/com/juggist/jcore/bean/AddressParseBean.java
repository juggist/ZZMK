package com.juggist.jcore.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.juggist.jcore.base.BaseBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/12/4 5:41 PM
 */
public class AddressParseBean extends BaseBean implements IPickerViewData {

    /**
     * id : 1
     * provinceid : 110000
     * province : 北京市
     * city : [{"id":"1","cityid":"110100","city":"北京市","provinceid":"110000","area":[{"id":"1","areaid":"110101","area":"东城区","cityid":"110100"},{"id":"2","areaid":"110102","area":"西城区","cityid":"110100"},{"id":"3","areaid":"110103","area":"崇文区","cityid":"110100"},{"id":"4","areaid":"110104","area":"宣武区","cityid":"110100"},{"id":"5","areaid":"110105","area":"朝阳区","cityid":"110100"},{"id":"6","areaid":"110106","area":"丰台区","cityid":"110100"},{"id":"7","areaid":"110107","area":"石景山区","cityid":"110100"},{"id":"8","areaid":"110108","area":"海淀区","cityid":"110100"},{"id":"9","areaid":"110109","area":"门头沟区","cityid":"110100"},{"id":"10","areaid":"110111","area":"房山区","cityid":"110100"},{"id":"11","areaid":"110112","area":"通州区","cityid":"110100"},{"id":"12","areaid":"110113","area":"顺义区","cityid":"110100"},{"id":"13","areaid":"110114","area":"昌平区","cityid":"110100"},{"id":"14","areaid":"110115","area":"大兴区","cityid":"110100"},{"id":"15","areaid":"110116","area":"怀柔区","cityid":"110100"},{"id":"16","areaid":"110117","area":"平谷区","cityid":"110100"},{"id":"17","areaid":"110228","area":"密云县","cityid":"110100"},{"id":"18","areaid":"110229","area":"延庆县","cityid":"110100"}]}]
     */

    private String id;
    private String provinceid;
    private String province;
    private List<CityBean> city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    @Override
    public String getPickerViewText() {
        return province;
    }

    public static class CityBean {
        /**
         * id : 1
         * cityid : 110100
         * city : 北京市
         * provinceid : 110000
         * area : [{"id":"1","areaid":"110101","area":"东城区","cityid":"110100"},{"id":"2","areaid":"110102","area":"西城区","cityid":"110100"},{"id":"3","areaid":"110103","area":"崇文区","cityid":"110100"},{"id":"4","areaid":"110104","area":"宣武区","cityid":"110100"},{"id":"5","areaid":"110105","area":"朝阳区","cityid":"110100"},{"id":"6","areaid":"110106","area":"丰台区","cityid":"110100"},{"id":"7","areaid":"110107","area":"石景山区","cityid":"110100"},{"id":"8","areaid":"110108","area":"海淀区","cityid":"110100"},{"id":"9","areaid":"110109","area":"门头沟区","cityid":"110100"},{"id":"10","areaid":"110111","area":"房山区","cityid":"110100"},{"id":"11","areaid":"110112","area":"通州区","cityid":"110100"},{"id":"12","areaid":"110113","area":"顺义区","cityid":"110100"},{"id":"13","areaid":"110114","area":"昌平区","cityid":"110100"},{"id":"14","areaid":"110115","area":"大兴区","cityid":"110100"},{"id":"15","areaid":"110116","area":"怀柔区","cityid":"110100"},{"id":"16","areaid":"110117","area":"平谷区","cityid":"110100"},{"id":"17","areaid":"110228","area":"密云县","cityid":"110100"},{"id":"18","areaid":"110229","area":"延庆县","cityid":"110100"}]
         */

        private String id;
        private String cityid;
        private String city;
        private String provinceid;
        private List<AreaBean> area;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvinceid() {
            return provinceid;
        }

        public void setProvinceid(String provinceid) {
            this.provinceid = provinceid;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public static class AreaBean {
            /**
             * id : 1
             * areaid : 110101
             * area : 东城区
             * cityid : 110100
             */

            private String id;
            private String areaid;
            private String area;
            private String cityid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAreaid() {
                return areaid;
            }

            public void setAreaid(String areaid) {
                this.areaid = areaid;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }
        }
    }
}
