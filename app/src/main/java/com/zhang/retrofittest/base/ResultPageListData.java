package com.zhang.retrofittest.base;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by zhang on 2017/4/21.
 */

public class ResultPageListData<T> extends BaseResult {

    /**
     * 数据
     */
    public PageData<T> data;

    public static <T> ResultPageListData<T> fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultPageListData.class, clazz);
        return gson.fromJson(json, objectType);
    }


}
