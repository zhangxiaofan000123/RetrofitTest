package com.zhang.retrofittest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.zhang.retrofittest.base.Param;
import com.zhang.retrofittest.base.SignUtil;

import okhttp3.Request;

/**
 * Created by zhang on 2016/7/12.
 */
public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public static boolean hasNetworkConnection(Context context) {
        if (context == null) {
            Log.w(TAG, "context should't be null");
        }
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") final NetworkInfo activeNetWorkInfo = connectivityManager.getActiveNetworkInfo();
        //如果没有与网络关联，则在此处进行关联
        boolean connected = (null != activeNetWorkInfo) && activeNetWorkInfo.isConnected();
        if (!connected) return false;
        //检测是否可以访问远程服务器
        boolean routeExists;
        //检测公共DNS
       /* try {

            InetAddress host = InetAddress.getByName(InterfaceDate.ROOT_URL);
            Socket s = new Socket();
            s.connect(new InetSocketAddress(host, 80), 5000);
            //如果没有抛出异常，则DNS存在
            routeExists = true;
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
            routeExists = false;
        }*/
        return (connected);
    }


    /**
     * 通过传入的参数构建get的url,默认带有sessionId和sign
     *
     * @param url
     * @param params 默认带有sign和sessionId
     * @return
     */
    public static String urlToolhasSesstionAndSign(String url, Param... params) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?")); //去除多余的无效的sessionId
        }
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (params.length == 0) {
            return sb.toString();
        } else {
            sb.append("?");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].getKey()).append("=").append(params[i].getValue());
                if (i != params.length - 1) {
                    sb.append("&");
                }
            }

            return sb.toString().replaceAll(" ", "%20");
        }
    }


    /**
     * 设置请求头和sign
     *
     * @param builder
     * @param params
     * @return
     */
    public static Request.Builder builderHeaderWithSign(Request.Builder builder, Param... params) {

        if (params.length == 0) {
            return builder;
        } else {

            for (int i = 0; i < params.length; i++) {
                builder.addHeader(params[i].getKey(), params[i].getValue());
            }
            return builder;
        }
    }

    /**
     * 判断所有的参数列表中是否有指定的key的参数
     *
     * @param key
     * @param params
     * @return
     */
    private static boolean hasKey(String key, Param... params) {
        for (Param param : params) {
            if (key.equals(param.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据前面的参数获取sign的值
     *
     * @param params
     * @return
     */
    @NonNull
    public static String getSign(Param... params) {
        String sign = getSignParam(params);
        return SignUtil.getSign(sign);
    }

    /**
     * 获取加密的参数
     *
     * @param params
     * @return
     */
    @NonNull
    public static String getSignParam(Param... params) {
        String sign = "";
        for (Param param : params) {
            sign += param.getKey() + param.getValue();
        }
        return sign;
    }


}
