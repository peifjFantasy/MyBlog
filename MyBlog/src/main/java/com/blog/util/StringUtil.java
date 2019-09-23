package com.blog.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：操作字符串的工具类
 * @param:
 * @return:
 * @author: john
 * @date:
 */
public class StringUtil {

    private StringUtil(){}

    /**
     * 功能描述：在字符串前后加%，以支持模糊查询
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    public static String formatLike(String str){
        if(isNotEmpty(str)){
            return "%"+str+"%";
        }
        return null;
    }

   /**
    * 功能描述：判断一个字符串是否不为空
    * @param:
    * @return:
    * @author: john
    * @date:
    */
    public static boolean isNotEmpty(String str){

        return (str!=null && !(str.trim()).equals(""));
    }

    /**
     * 功能描述：将传入的存放字符串的集合逐一遍历，去掉其中的空串（“”）
     * @return:
     * @author: john
     * @Date:
     */
    public static List<String> filterWhite(List<String> list){
        List<String> result=new ArrayList<>();
        for (String s : list) {
            if(isNotEmpty(s)){
                result.add(s);
            }
        }
        return result;
    }


















}
