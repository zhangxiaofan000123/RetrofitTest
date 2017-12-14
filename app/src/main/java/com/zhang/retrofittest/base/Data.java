package com.zhang.retrofittest.base;

import java.util.List;

/**
 * Created by zhang on 2016/7/25.
 *
 */
public class Data<T> {
    public int currentpageindex;
    public int pagesize;
    public int totalitemcount;
    public int totalpagecount;
    public int startrecordindex;
    public int endrecordindex;
    public List<Integer>  pages;
    public T data;

    @Override
    public String toString() {
        return "Data{" +
                "CurrentPageIndex=" + currentpageindex +
                ", PageSize=" + pagesize +
                ", TotalItemCount=" + totalitemcount +
                ", TotalPageCount=" + totalpagecount +
                ", StartRecordIndex=" + startrecordindex +
                ", EndRecordIndex=" + endrecordindex +
                ", Pages=" + pages +
                ", Data=" + data +
                '}';
    }
}
