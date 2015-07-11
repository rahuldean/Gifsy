package com.godhc.gifsy.utlis;

public final class Constants {

    /* Urls */
    public final class Urls {
        public static final String POPULAR_TAGS = "https://gist.githubusercontent.com/rahuldean/86d743abaa246340f073/raw/gifsy-popular-tags";
        public static final String ALL_TAGS = "http://www.gifbase.com/js/tags.json";
        public static final String SEARCH_BY_TAG = "http://www.gifbase.com/tag/%s?format=json&p=%d";
    }

    /* User facing error messages */
    public final class  ErrorMessages {
        public static final String POPULAR_TAGS_FETCH_ERROR = "Failed to fetch popular tags. We are looking into this";
        public static final String ALL_TAGS_FETCH_ERROR = "Failed to fetch all tags. We are looking into this";
        public static final String SEARCH_BY_TAG_FETCH_ERROR = "Failed to fetch information for this tag. We are looking into this";
    }

    /* Global constants */

    public final class  GlobalConstants {
        public static final String COMMA_SEPARATOR = ",";
        public static final String ACTIVITY_TAG_GIFS_TAG_NAME = "ACTIVITY_TAG_GIFS_TAG_NAME";
    }
}
