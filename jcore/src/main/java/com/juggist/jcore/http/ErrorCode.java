package com.juggist.jcore.http;

/**
 * @author juggist
 * @date 2018/10/30 3:06 PM
 */
public enum ErrorCode {
    /**
     * 网络错误
     */
    NET_ERROR("NET_ERROR", "网络异常，请稍后重试"),

    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),

    SERVER_ERROR("SERVER_ERROR","服务器异常，请稍后重试"),

    /**
     * json解析错误==服务器返回错误
     */
    PARSER_EXCEPTION("JSON_PARSER", "未知错误"),

    /**
     * 用户信息
     */
    USER_INFO_OUT_OF_LENGTH("USER_INFO_OUT_OF_LENGTH","用户信息重叠"),

    DATA_NULL("DATA_NULL","获取数据失败")
    ;


    private String code;
    private String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
