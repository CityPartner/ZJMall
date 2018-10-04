package com.nchhr.mall.Enum;

public enum ExceptionEnum {

    NoAuthority_Exception(100, "没有该权限"),
    NoLogin_Exception(101, "没有登录"),
    UNKNOW_Exception(-1, "未知错误"),
    DATALINK_Exception(500, "数据库连接错误"),
    SUCCESS(200, "成功！"),
    MD5(300, "加密失败");;


    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
