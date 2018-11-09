package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

/**
 * @author juggist
 * @date 2018/11/7 4:11 PM
 *
 */
public class BannerBean extends BaseBean {
    private String auto_id;
    private String ad_pic;
    private String ad_alt;
    private String ad_link;
    private String ad_order;

    public String getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(String auto_id) {
        this.auto_id = auto_id;
    }

    public String getAd_pic() {
        return ad_pic;
    }

    public void setAd_pic(String ad_pic) {
        this.ad_pic = ad_pic;
    }

    public String getAd_alt() {
        return ad_alt;
    }

    public void setAd_alt(String ad_alt) {
        this.ad_alt = ad_alt;
    }

    public String getAd_link() {
        return ad_link;
    }

    public void setAd_link(String ad_link) {
        this.ad_link = ad_link;
    }

    public String getAd_order() {
        return ad_order;
    }

    public void setAd_order(String ad_order) {
        this.ad_order = ad_order;
    }
}
