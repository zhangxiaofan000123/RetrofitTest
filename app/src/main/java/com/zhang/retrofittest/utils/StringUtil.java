package com.zhang.retrofittest.utils;

/**
 * Created by 29495 on 2017/11/2.
 */

public class StringUtil {

    /**
     * 判断字符串是否为null或空白
     */
    public static boolean isEmptyOrNull(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 转换字符串首字母为小写
     */
    public static String toLowerCaseFirstOne(String str) {
        if(Character.isLowerCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    /**
     * 转换字符串首字母为大写
     */
    public static String toUpperCaseFirstOne(String str) {
        if(Character.isUpperCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();
    }
}
