package com.zhang.retrofittest.base;

public class SignUtil {

    //	public static Context _context;
    private final static String SIGN_KEY = "dherss_key";


    // 机组列表
    public static String getSign(String str) {
        return MD5.getMD5String(str + SIGN_KEY);
    }

    // 机组详细信息
    public static String getSigntwo(String str) {

        return MD5.getMD5String(str + SIGN_KEY);
    }
    /**
     * cyb
     *
     * @param
     * @return 加密后的字符串
     */
    public static String getfanyiSign(String str) {
        return MD5.getMD5String(str);
    }


}
