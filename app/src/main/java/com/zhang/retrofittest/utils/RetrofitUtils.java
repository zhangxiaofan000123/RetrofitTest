package com.zhang.retrofittest.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.zhang.retrofittest.Alarm;
import com.zhang.retrofittest.DiagnosisApplyModel;
import com.zhang.retrofittest.LoginBean;
import com.zhang.retrofittest.base.RetrofitManager;
import com.zhang.retrofittest.UserModel;
import com.zhang.retrofittest.base.PageData;
import com.zhang.retrofittest.base.Param;
import com.zhang.retrofittest.base.Result;

import java.util.HashMap;
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
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by zhang on 2017/12/14.
 */

public class RetrofitUtils {
    private static final String TAG = "RetrofitUtils";

    //创建实现接口调用
    protected static final NetService service = RetrofitManager.getRetrofit().create(NetService.class);

    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static final String API_TOKEN = "api-token";
    private static final String API_RANGES = "api-ranges";

    private interface NetService {

        //POST请求
        @POST("api/account/login")
        Observable<Result<LoginBean>> getVerfcationCodePost(@Body RequestBody route);

        @GET("api/secheduling/listByRole")
        Observable<Result<List<DiagnosisApplyModel>>> getDiagnosisListGet(@Header(API_TOKEN) String api_token,
                                                                          @Query("status") String status,
                                                                          @Query("Identification") String name);


        @GET("api/secheduling/list")
        Observable<Result<PageData<DiagnosisApplyModel>>> getDiagnosisPageListGet(@HeaderMap Map<String, String> api_token,
                                                                                  @Query("Status") String status,
                                                                                  @Query("Name") String name);


        @GET("api/account/users")
        Observable<Result<List<UserModel>>> getUserModerListGet(@Header(API_TOKEN) String api_token,
                                                                @Query("Name") String name,
                                                                @Query("Enable") String enable,
                                                                @Query("IsExpert") String isExpert);


        @GET("api/account/users")
        Observable<Result<PageData<UserModel>>> getUserModerPageListGet(@HeaderMap Map<String, String> api_token,
                                                                        @Query("Name") String name,
                                                                        @Query("Enable") String enable,
                                                                        @Query("IsExpert") String isExpert);


        @POST("api/account/logout")
        Observable<Result> logoutPost();

        //用户信息返回数组
        @GET("api/account/users")
        Observable<Result<UserModel>> getUserModelGet(@Header(API_TOKEN) String api_token,
                                                      @Query("id") String id);

        @POST("api/account/users/edit")
        Observable<Result> ediUserModelPost(@Body RequestBody route);

        @GET("api/alarm/list")
        Observable<Result<List<Alarm>>> getAlarmListGet();


    }

    /**
     * 登陆请求
     *
     * @param UserNo
     * @param Password
     * @param observer
     */
    public static void loginPost(String UserNo, String Password, Observer<Result<LoginBean>> observer) {
        LoginHeader loginHeader = new LoginHeader(UserNo, Password);
        setSubscribe(service.getVerfcationCodePost(getRequestBody(getModeJson(loginHeader))), observer);
    }

    /**
     * 获取诊断单列表
     *
     * @param Identification
     * @param Status
     * @param observer
     */
    public static void getDiagnosisList(String Identification, String Status
            , Observer<Result<List<DiagnosisApplyModel>>> observer) {
        String api_token = NetworkUtils.getSign(new Param("status", Status), new Param("Identification", Identification));
        Log.i(TAG, "getDiagnosisList: api_token " + api_token);
        setSubscribe(service.getDiagnosisListGet(api_token, Status, Identification), observer);
    }

    /**
     * 获取诊断单分页数据
     *
     * @param Identification
     * @param Status
     * @param observer
     */
    public static void getDiagnosisPageList(String Identification, String Status,
                                            int page, int pageSize, String order, Observer<Result<PageData<DiagnosisApplyModel>>> observer) {

        Map<String, String> headerMap = new HashMap<>();
        String api_token = NetworkUtils.getSign(new Param("Status", Status), new Param("Name", Identification));
        String api_ranges = "page=" + page + ";" + "size=" + pageSize + ";" + "order=" + (order == null ? "asc" : order);
        headerMap.put(API_TOKEN, api_token);
        headerMap.put(API_RANGES, api_ranges);

        setSubscribe(service.getDiagnosisPageListGet(headerMap, Status, Identification), observer);
    }

    /**
     * 获取不分页的用户列表
     *
     * @param Name     用户名称？？
     * @param Enable   0 启用 1 禁用 2 全部
     * @param IsExpert true 专家
     * @param observer
     */
    public static void getUserModelList(String Name, int Enable, boolean IsExpert,
                                        Observer<Result<List<UserModel>>> observer) {
        String api_token = NetworkUtils.getSign(new Param("Name", Name),
                new Param("Enable", Enable),
                new Param("IsExpert", IsExpert + ""));
        Log.i(TAG, "getUserModelList: api_token " + api_token);
        setSubscribe(service.getUserModerListGet(api_token, Name, Enable + "", IsExpert + ""), observer);
    }

    /**
     * 获取分页的用户列表
     *
     * @param Name
     * @param Enable
     * @param IsExpert
     * @param page
     * @param pageSize
     * @param order
     * @param observer
     */
    public static void getUserModelPageList(String Name, int Enable, boolean IsExpert,
                                            int page, int pageSize, String order,
                                            Observer<Result<PageData<UserModel>>> observer) {

        Map<String, String> headerMap = new HashMap<>();
        String api_token = NetworkUtils.getSign(new Param("Name", Name),
                new Param("Enable", Enable),
                new Param("IsExpert", IsExpert + ""));

        String api_ranges = "page=" + page + ";" + "size=" + pageSize + ";" + "order=" + (order == null ? "asc" : order);
        headerMap.put(API_TOKEN, api_token);
        headerMap.put(API_RANGES, api_ranges);

        setSubscribe(service.getUserModerPageListGet(headerMap, Name, Enable + "", IsExpert + ""), observer);
    }

    /**
     * 退出登录
     *
     * @param observer
     */
    public static void logout(Observer<Result> observer) {
        setSubscribe(service.logoutPost(), observer);
    }

    /**
     * 获取用户信息的
     *
     * @param id       用户的id
     * @param observer
     */
    public static void getUserModel(String id, Observer<Result<UserModel>> observer) {
        String api_token = NetworkUtils.getSign(new Param("id", id));

        setSubscribe(service.getUserModelGet(api_token, id), observer);
    }

    /**
     * 编辑用户信息
     * @param userModel
     * @param observer
     */
    public static void editUserModel(UserModel userModel, Observer<Result> observer) {
        String jsonStr = getModeJson(userModel);
        setSubscribe(service.ediUserModelPost(getRequestBody(jsonStr)), observer);
    }

    /**
     * 获取报警列表
     * @param observer
     */
    public static void getAlarmList( Observer<Result<List<Alarm>>> observer) {
        setSubscribe(service.getAlarmListGet(), observer);
    }


    private static <T> String getModeJson(T t) {
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
    private static RequestBody getRequestBody(@NonNull String jsonStr) {
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
