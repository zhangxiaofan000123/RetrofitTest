package com.zhang.retrofittest.base;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by zhang on 2016/7/5.
 */
public class Result<T> extends BaseResult {
    public T data;


    public static Result fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(Result.class, clazz);
        return gson.fromJson(json, objectType);
    }


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}