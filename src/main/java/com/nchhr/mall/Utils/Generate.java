package com.nchhr.mall.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    public static String getRandomNumStr(int length){
        if(length<=0||length>=100){
            return null;
        }
        StringBuffer str=new StringBuffer();
        Random r=new Random();
        int a;
        for(int i =0;i<length;i++){
//            a=r.nextInt();
//            a=
//            System.out.println("fjdsklafjdlsa----"+a+"fdsafad=="+a%10);
            str.append(Math.abs(r.nextInt()%10));
        }
//        System.out.println("fjdsklfjdks====="+str.toString());
        return str.toString();
    }
}
