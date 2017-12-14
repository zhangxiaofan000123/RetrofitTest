package com.zhang.retrofittest.base;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhang on 2016/12/15.
 */
public class ResultListData<T> extends BaseResult {


    /**
     * 数据
     */
    public List<T> data;


    public static <T> ResultListData<T> fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultListData.class, clazz);
        return gson.fromJson(json, objectType);
    }


}
