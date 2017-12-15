package com.zhang.retrofittest.base;

import android.util.Log;

import com.zhang.retrofittest.LoginBean;
import com.zhang.retrofittest.MyApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhang on 2017/12/14.
 */

public class RetrofitManager {
    private static final String TAG = "RetrofitManager";
    //服务器路径
    private static final String API_SERVER = "http://101.200.182.221:82";

    private static Retrofit mRetrofit;

    private static OkHttpClient mOkHttpClient;

    public static LoginBean loginBean = null;

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    public static Retrofit getRetrofit() {

        if (null == mRetrofit) {

            if (mOkHttpClient == null) {
                mOkHttpClient = new OkHttpClient.Builder()
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .cookieJar(new CookiesManager(MyApplication.getContext()))
                        .retryOnConnectionFailure(false)
                        .addNetworkInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request originalRequest = chain.request();
                                Log.i(TAG, "intercept: url -> "+originalRequest.url());
                                if (loginBean == null) {
                                    originalRequest.newBuilder().removeHeader("Cookie");
                                    return chain.proceed(originalRequest);
                                }
                                Request authorised = originalRequest.newBuilder()
                                        .header("Authorization", loginBean.getToken().getTokenStr())
                                        .header("Content-Type", "application/json;charset=utf-8")
                                        .build();
                                Log.i(TAG, "intercept: headers -> " + authorised.headers());

                                return chain.proceed(authorised);
                            }
                        })
//                        .addNetworkInterceptor(new ErrorHandlerInterceptor()) 可以拿到返回的response
                        .build();
            }
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_SERVER + "/")
                    .addConverterFactory(MyGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();

        }

        return mRetrofit;
    }

}
