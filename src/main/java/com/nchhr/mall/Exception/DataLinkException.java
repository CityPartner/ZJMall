package com.nchhr.mall.Exception;

import com.nchhr.mall.Enum.ExceptionEnum;

/**
 * 数据库链接时出现异常
 */
public class DataLinkException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DataLinkException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }
}
