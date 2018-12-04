package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

/**
 * @author juggist
 * @date 2018/12/3 2:20 PM
 */
public class OrderRefundBean extends BaseBean {
    /**
     * sn : 153786706281590971
     * apply_time : 1537929906
     * refund_money :
     * now_status : 1
     * reply_content :
     */

    private String sn;
    private String apply_time;
    private String refund_money;
    private String now_status;
    private String reply_content;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public String getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(String refund_money) {
        this.refund_money = refund_money;
    }

    public String getNow_status() {
        return now_status;
    }

    public void setNow_status(String now_status) {
        this.now_status = now_status;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }
}
