package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

/**
 * @author juggist
 * @date 2018/12/4 2:44 PM
 */
public class AddressBean extends BaseBean {
    /**
     * auto_id : 1
     * user_id : 1
     * addr : 大河之巅
     * is_default : 0
     * province : 330000
     * city : 330300
     * areas : 330324
     * province_name : 浙江省
     * city_name : 温州市
     * areas_name : 永嘉县
     * consignee : 1
     * cellphone : 1
     */

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

    public AddressBean(String addr, String is_default, String province, String city, String areas, String consignee, String cellphone) {
        this.addr = addr;
        this.is_default = is_default;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.consignee = consignee;
        this.cellphone = cellphone;
    }

    public AddressBean() {
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
