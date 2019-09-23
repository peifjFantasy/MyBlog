package com.blog.util;


import org.apache.shiro.crypto.hash.SimpleHash;

public class CryptographyUtil {

    /**
     * 设计一个MD5加密方法
     * 用于获取初始用户的初始明文密码经md5加密1024次后的暗文密码
     */
    public static SimpleHash md5(String str, String salt){
        return new SimpleHash("MD5",str,salt,1024);
    }

    public static void main(String[] args) {

        Object result=md5("123","john");
        System.out.println(result.toString());
    }
}
