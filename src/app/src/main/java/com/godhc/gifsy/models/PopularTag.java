package com.godhc.gifsy.models;

public class PopularTag {
    String tag;
    String imgUrl;
    int count;

    public PopularTag() {
        super();
    }

    public PopularTag(String tag, String imgUrl, int count) {
        this.tag = tag;
        this.imgUrl = imgUrl;
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getCount() {
        return count;
    }
}
