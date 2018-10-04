package com.nchhr.mall.Exception;

import com.nchhr.mall.Enum.ExceptionEnum;

/**
 * 没有登录异常
 */
public class NoLoginException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public NoLoginException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = code;
    }
}
