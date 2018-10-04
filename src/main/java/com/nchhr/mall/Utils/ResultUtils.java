package com.nchhr.mall.Utils;

import com.nchhr.mall.Enum.ExceptionEnum;
import com.nchhr.mall.RsultModel.R_data;

public class ResultUtils {
    //成功返回
    public static R_data success(Object object , ExceptionEnum exceptionEnum){
        R_data r_data = new R_data();
        r_data.setCode(exceptionEnum.getCode());
        r_data.setMessage(exceptionEnum.getMessage());
        r_data.setData(object);
        return r_data;
    }
    //失败返回

    public static R_data error(Object object , ExceptionEnum exceptionEnum){
        R_data r_data = new R_data();
        r_data.setCode(exceptionEnum.getCode());
        r_data.setMessage(exceptionEnum.getMessage());
        r_data.setData(object);
        return r_data;
    }
    //integer string
    public static R_data error(Object object, Integer code, String message){
        R_data r_data = new R_data();
        r_data.setCode(code);
        r_data.setMessage(message);
        r_data.setData(object);
        return r_data;
    }
    //成功返回的对象是空的
    public static R_data success(){
        return success(null,null);
    }
}
