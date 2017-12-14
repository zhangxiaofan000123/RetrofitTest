package com.zhang.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zhang.retrofittest.base.PageData;
import com.zhang.retrofittest.base.Result;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
    }

    private void getData() {
        RetrofitUtils.loginPost("1234@qq.com", "111111", new Observer<Result<LoginBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<LoginBean> loginBeanResult) {
                Log.i(TAG, "onNext: result -- > " + loginBeanResult);
                if (loginBeanResult.code == 1) {
                    RetrofitManager.loginBean = loginBeanResult.data;

                    testInterface(loginBeanResult.data);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void testInterface(LoginBean data) {
//        testDiagnosisList();
//        testDiagnosisPageList(); //500 status 大小写？
//        testUserList(data); //403
//        testUserPageList(data);//403
//        testLogout();//403
//        testUserModel(data); //403
//        testEditUserModel(data); //404
//        testAlarmList(data); //获取数据成功但是不知道那个设备报警
    }

    private void testAlarmList(LoginBean data) {
        RetrofitUtils.getAlarmList(new Observer<Result<List<Alarm>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<List<Alarm>> listResult) {
                Log.i(TAG, "testAlarmList onNext: " +listResult);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void testEditUserModel(LoginBean data) {
        RetrofitUtils.editUserModel(data.getUser(), new Observer<Result>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result result) {
                Log.i(TAG, "testEditUserModel onNext: " + result);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void testUserModel(LoginBean data) {
        RetrofitUtils.getUserModel(data.getUser().getId(), new Observer<Result<UserModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<UserModel> userModelResult) {
                Log.i(TAG, "onNext: testUserModel " + userModelResult);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void testLogout() {
        RetrofitUtils.logout(new Observer<Result>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result result) {
                Log.i(TAG, "testLogout onNext: " + result);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void testUserPageList(LoginBean data) {
        RetrofitUtils.getUserModelPageList(data.getUser().getDisplayName(), 0, false,
                1, 20, null, new Observer<Result<PageData<UserModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<PageData<UserModel>> pageDataResult) {
                        Log.i(TAG, "onNext: testUserPageList-->" + pageDataResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void testUserList(LoginBean data) {
        RetrofitUtils.getUserModelList(data.getUser().getDisplayName(), 0, false, new Observer<Result<List<UserModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<List<UserModel>> listResult) {
                Log.i(TAG, "onNext: testUserList-->" + listResult);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void testDiagnosisPageList() {
        RetrofitUtils.getDiagnosisPageList("", "1", 1, 20
                , null, new Observer<Result<PageData<DiagnosisApplyModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<PageData<DiagnosisApplyModel>> pageDataResult) {
                        Log.i(TAG, "onNext: result list -- > " + pageDataResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void testDiagnosisList() {
        RetrofitUtils.getDiagnosisList("", "3", new Observer<Result<List<DiagnosisApplyModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<List<DiagnosisApplyModel>> listResult) {
                Log.i(TAG, "onNext: result list -- > " + listResult);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void onClick(View view) {
        getData();
    }
}
