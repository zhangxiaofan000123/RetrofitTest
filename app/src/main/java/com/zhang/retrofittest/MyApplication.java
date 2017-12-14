package com.zhang.retrofittest;

import android.app.Application;
import android.content.Context;



/**
 * Created by 29495 on 2017/11/6.
 */

public class MyApplication extends Application {

    /** 全局上下文 */
    private static Context _context;

    /**
     *
     * @name 方法名  getContext()
     * @desc 描述  获取全局上下文
     * @author 马文韬
     * @return 全局上下文
     */
    public static Context getContext() {
        return _context;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        _context = getApplicationContext();

        //if (!IMPluginHelper.shouldInit(this)) {
            //防止多进程初始化多次
        //    return;
        //}

        //SDKCoreHelper.setContext(this);
        // 插件日志开启(放在setContext之后)
        //LogUtil.setDebugMode(true);
        //DaoHelper.init(this, new IMDao());


    }

}
