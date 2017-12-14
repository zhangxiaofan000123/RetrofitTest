package com.zhang.retrofittest;

/**
 * Created by 19051 on 2017/12/15.
 */

public class Alarm {

    /**
     * id : 01521e9d-1227-c865-1e57-08d541c560d3
     * name : 开极限报警
     * describe : 建议方案：1.XXXXXXXXXX；2.XXXXXXXXX32
     */

    private String id;
    private String name;
    private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
