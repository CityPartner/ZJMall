package com.nchhr.mall.Enum;

public enum CodeEnum {

    AccessId("AccessKeyId", "LTAIt2OMF0RdFsw0"),
    AccessKeySecre("AccessKeySecret", "CKNdqWQgzmYmPdeZetwpu0HmXHmEQH"),
    //登录模板
    SMSTemplateCode("SMSTemplateCode", "SMS_147125244"),
    SignName("SignName", "南昌城市合伙人"),
    //订单提醒
    SMSTemplate1("SMSTemplateCode","SMS_156276411"),
    SignName1("SignName", "南昌城市合伙人"),
    ;

    private String key;
    private String value;



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    CodeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
