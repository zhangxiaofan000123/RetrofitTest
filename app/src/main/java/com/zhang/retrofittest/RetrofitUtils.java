package com.zhang.retrofittest;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.zhang.retrofittest.base.Result;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by zhang on 2017/12/14.
 */

public class RetrofitUtils {

    //创建实现接口调用
    protected static final NetService service = RetrofitManager.getRetrofit().create(NetService.class);

    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private interface NetService {

        //POST请求
        @POST("api/account/login")
        @Headers({"Content-Type: application/json;charset=utf-8"})
        Observable<Result<LoginBean>> getVerfcationCodePost(@Body RequestBody route);

        @GET("api/secheduling/listByRole")
        @Headers({"Content-Type: application/json;charset=utf-8"

        })
        Observable<Result<List<DiagnosisApplyModel>>> getDiagnosisListGet(@HeaderMap Map<String, String> headers);


    }

    //POST请求
    public static void loginPost(String UserNo, String Password, Observer<Result<LoginBean>> observer) {
        LoginHeader loginHeader = new LoginHeader(UserNo, Password);
        setSubscribe(service.getVerfcationCodePost(jsonToRequestBody(getModeJson(loginHeader))), observer);
    }

    public static void getDiagnosisList(String Identification, String Status, String paseNo, Observer<Result<LoginBean>> observer) {



//        setSubscribe(service.getDiagnosisListGet( "page="+paseNo, , observer);
    }

    public static <T> String getModeJson(T t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    static class LoginHeader {
        private String UserNo;
        private String Password;

        public LoginHeader(String userNo, String password) {
            UserNo = userNo;
            Password = password;
        }
    }


    /**
     * 为build添加Post的json内容
     *
     * @param jsonStr
     */
    private static RequestBody jsonToRequestBody(@NonNull String jsonStr) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return RequestBody.create(JSON, jsonStr);
    }


    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
