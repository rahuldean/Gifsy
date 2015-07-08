package com.godhc.gifsy.models;

public class PopularTag {
    String tag;
    String imgUrl;

    public PopularTag() {
        super();
    }

    public PopularTag(String tag, String imgUrl) {
        this.tag = tag;
        this.imgUrl = imgUrl;
    }

    public String getTag() {
        return tag;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
