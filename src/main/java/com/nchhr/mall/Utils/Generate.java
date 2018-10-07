package com.nchhr.mall.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Generate {
    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return df.format(date);
    }

    public static String getTimeByFormat(String format){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}
