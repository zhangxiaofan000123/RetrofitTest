package com.zhang.retrofittest.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhang on 2016/7/12.
 * 为NetworkUtils中的参数
 */
public class Param implements Parcelable {
    public String key;
    public String value;

    public Param() {
    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Param(String key, int value) {
        this.key = key;
        this.value = value + "";
    }
    public Param(String key, long value) {
        this.key = key;
        this.value = value + "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Param{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //必须要按照声明的顺序存储数据，不然会获取数据出错
        dest.writeString(key);
        dest.writeString(value);
    }

    public static final Creator<Param> CREATOR = new Creator<Param>() {
        @Override
        public Param createFromParcel(Parcel source) {
            Param param = new Param();
            param.setKey(source.readString());
            param.setValue(source.readString());
            return param;
        }


        @Override
        public Param[] newArray(int size) {
            return new Param[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Param) {
            Param tempParam = (Param) obj;
            return tempParam.getKey().equals(key);
        }
        return false;
    }
}
