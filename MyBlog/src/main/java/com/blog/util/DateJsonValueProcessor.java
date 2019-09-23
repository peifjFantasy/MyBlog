package com.blog.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 把Date类型或Timestamp类型数据按照具体显示格式转换为String的工具类
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

    private String format;

    public DateJsonValueProcessor(String format){
        this.format=format;
    }

    @Override
    public Object processArrayValue(Object o, JsonConfig jsonConfig) {
        return null;
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if(value==null){
            return "";
        }
        //把时间戳类型数据转换为字符串
        if(value instanceof Timestamp){
            String str=new SimpleDateFormat(this.format).format((Timestamp)value);
            return str;
        }
        //把Date类型数据转换为字符串
        if(value instanceof Date){
            String str=new SimpleDateFormat(this.format).format((Date)value);
            return str;
        }
        return value.toString();
    }
}
