package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/12/3 3:11 PM
 */
public class OrderTransportBean extends BaseBean {
    /**
     * LogisticCode : 801605373291160748
     * ShipperCode : YTO
     * Traces : [{"AcceptStation":"【广东省东莞市道滘公司】 已收件","AcceptTime":"2018-09-17 19:10:12"},{"AcceptStation":"【广东省东莞市道滘公司】 已打包","AcceptTime":"2018-09-18 00:51:59"},{"AcceptStation":"【广东省东莞市道滘公司】 已发出 下一站 【虎门转运中心】","AcceptTime":"2018-09-18 01:08:09"},{"AcceptStation":"【虎门转运中心】 已收入","AcceptTime":"2018-09-18 05:31:29"},{"AcceptStation":"【虎门转运中心】 已发出 下一站 【南昌转运中心】","AcceptTime":"2018-09-18 05:33:54"},{"AcceptStation":"【南昌转运中心】 已发出 下一站 【赣州转运中心】","AcceptTime":"2018-09-19 15:30:40"},{"AcceptStation":"【赣州转运中心】 已收入","AcceptTime":"2018-09-20 01:32:19"},{"AcceptStation":"【赣州转运中心】 已发出 下一站 【江西省赣州市安远县公司】","AcceptTime":"2018-09-20 03:02:54"},{"AcceptStation":"【江西省赣州市安远县公司】 已发出 下一站 【江西省赣州市安远县孔田镇公司】","AcceptTime":"2018-09-20 09:54:23"},{"AcceptStation":"【江西省赣州市安远县孔田镇公司】 派件人: 刘兰芳 派件中 派件员电话15170170203","AcceptTime":"2018-09-20 18:14:33"},{"AcceptStation":"快件已到达孔田高阳街中段圆通速递妈妈驿站,联系电话15170170203","AcceptTime":"2018-09-20 18:16:33"},{"AcceptStation":"孔田高阳街中段圆通速递妈妈驿站已发出自提短信,请上门自提,联系电话15170170203","AcceptTime":"2018-09-20 18:17:33"},{"AcceptStation":"客户 签收人: 他人代签,转入孔田镇 已签收 感谢使用圆通速递，期待再次为您服务","AcceptTime":"2018-09-20 22:51:25"}]
     * State : 3
     * EBusinessID : 1359959
     * Success : true
     */

    private String LogisticCode;
    private String ShipperCode;
    private String State;
    private String EBusinessID;
    private boolean Success;
    private List<TracesBean> Traces;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    public static class TracesBean extends BaseBean {
        /**
         * AcceptStation : 【广东省东莞市道滘公司】 已收件
         * AcceptTime : 2018-09-17 19:10:12
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }
}
