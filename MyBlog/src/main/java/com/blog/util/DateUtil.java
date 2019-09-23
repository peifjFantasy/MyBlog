package com.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: john
 * @Date: 2019/9/14 18:34
 * @Description: 日期工具类
 * @version: 1.0
 */
public class DateUtil {

    /**
     * 功能描述：把当前时间转换为一个字符串，用作一个唯一标识号
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    public static String getCurrentDateStr(){
        Date date=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    /**
     * 功能描述：把指定的Date类型的参数转换为相应格式的字符串形式
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    public static String formatDate(Date date,String format){
        String result="";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        if(date != null){
            result = sdf.format(date);
        }
        return result;
    }

}
