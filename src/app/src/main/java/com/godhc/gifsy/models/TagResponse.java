package com.godhc.gifsy.models;

import java.util.List;

public class TagResponse {
    String tag;
    int gifCount;
    int pageCurrent;
    int pageCount;
    List<GifInfo> gifInfoList;

    public TagResponse(String tag, int gifCount, int pageCurrent, int pageCount, List<GifInfo> gifInfoList) {
        this.tag = tag;
        this.gifCount = gifCount;
        this.pageCurrent = pageCurrent;
        this.pageCount = pageCount;
        this.gifInfoList = gifInfoList;
    }

    public String getTag() {
        return tag;
    }

    public int getGifCount() {
        return gifCount;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public int getPageCount() {
        return pageCount;
    }

    public List<GifInfo> getGifInfoList() {
        return gifInfoList;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setGifCount(int gifCount) {
        this.gifCount = gifCount;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setGifInfoList(List<GifInfo> gifInfoList) {
        this.gifInfoList = gifInfoList;
    }
}
