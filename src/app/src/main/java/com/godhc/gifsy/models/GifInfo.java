package com.godhc.gifsy.models;

import com.godhc.gifsy.utlis.Constants.*;

import java.util.Arrays;
import java.util.List;

public class GifInfo {
    String id;
    String url;
    String username;
    String date;
    List<String> tags;
    String tagsAsString;

    public GifInfo(String id, String url, String username, String date, String tagsString) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.date = date;
        this.tags = this.getTagsFromString(tagsString);
        this.tagsAsString = tagsString;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getTagsAsString() {
        return tagsAsString;
    }

    protected List<String> getTagsFromString(String tagsString) {
        return Arrays.asList(tagsString.split(GlobalConstants.COMMA_SEPARATOR));
    }
}
