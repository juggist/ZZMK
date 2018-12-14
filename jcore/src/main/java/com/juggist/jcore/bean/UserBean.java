package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

/**
 * @author juggist
 * @date 2018/10/30 4:33 PM
 */
public class UserBean extends BaseBean {

    /**
     * nickname : 范三
     * photo : http://47.106.64.20/store/2018/09/29/1538210823_70405097113336c11c56f1987cf107.jpg
     * birth : 9311265
     * sex : 0
     * token : a7b0cf408fb815b7bc8d50345ed5e567
     * balance : 0
     * e_money : null
     * level : 3
     * cellphone : 13242001948
     * level_name : 钻石
     * today_orders_number : 2
     * total_price : 215.16
     * user_id : 1
     * address : {"id":"2","auto_id":"2","user_id":"1","addr":"","is_default":"1","province":"630000","city":"630100","areas":"630101","province_name":"青海省","city_name":"西宁市","areas_name":"市辖区","consignee":"方法","cellphone":"123456"}
     */

    private String      nickname;
    private String      photo;
    private String      birth;
    private String      sex;
    private String      token;
    private String      balance;
    private String      e_money;
    private String      level;
    private String      cellphone;
    private String      level_name;
    private int         today_orders_number;
    private double      total_price;
    private String      user_id;
    private AddressBean address;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getE_money() {
        return e_money;
    }

    public void setE_money(String e_money) {
        this.e_money = e_money;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public int getToday_orders_number() {
        return today_orders_number;
    }

    public void setToday_orders_number(int today_orders_number) {
        this.today_orders_number = today_orders_number;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

}
