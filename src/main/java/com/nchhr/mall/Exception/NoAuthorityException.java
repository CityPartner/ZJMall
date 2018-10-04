package com.nchhr.mall.Exception;

import com.nchhr.mall.Enum.ExceptionEnum;

/**
 * 没有权限
 */
public class NoAuthorityException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public NoAuthorityException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }
}
