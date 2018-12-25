package com.juggist.componentservice;

import com.juggist.jcore.base.BaseBean;

/**
 * @author juggist
 * @date 2018/12/20 4:59 PM
 */
public class TargetBean extends BaseBean {
    int tag;//1:从订单进入,2:从个人中心进入

    public TargetBean(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
