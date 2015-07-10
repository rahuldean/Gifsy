package com.godhc.gifsy.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.godhc.gifsy.models.ApplicationError;
import com.godhc.gifsy.models.GifInfo;
import com.godhc.gifsy.models.TagResponse;
import com.godhc.gifsy.network.VolleySingleton;
import com.godhc.gifsy.utlis.Constants.*;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchByTagApi {

    Context context;

    public SearchByTagApi(Context context) {
        super();
        this.context = context;
    }

    public void searchGifByTag(String tag, int page, final SearchGifByTagResponseListener searchGifByTagResponseListener) {

        //Build the url
        String url = String.format(Urls.SEARCH_BY_TAG, tag, page);

        RequestQueue requestQueue = VolleySingleton.getInstance(this.context).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(response.toString());
                        searchGifByTagResponseListener.onSearchGifByTagResponse(parseResponse(response), null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        searchGifByTagResponseListener.onSearchGifByTagResponse(null, new ApplicationError(ErrorMessages.SEARCH_BY_TAG_FETCH_ERROR,
                                error.getMessage()));
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    protected TagResponse parseResponse(JSONObject response) {
        List<GifInfo> gifInfoList = new ArrayList<>();
        TagResponse tagResponse = new TagResponse("", 0, 0, 0, gifInfoList);
        if (response == null)
            return tagResponse;

        try {
            tagResponse.setTag(response.getString("tag"));
            tagResponse.setGifCount(response.getInt("gif_count"));
            tagResponse.setPageCurrent(response.getInt("page_current"));
            tagResponse.setPageCount(response.getInt("page_count"));

            JSONArray gifsArray = response.getJSONArray("gifs");

            for (int i = 0; i < gifsArray.length(); i++) {
                JSONObject currentGifJsonObject = gifsArray.getJSONObject(i);

                GifInfo gifInfo = new GifInfo(currentGifJsonObject.getString("id"),
                        currentGifJsonObject.getString("url"),
                        currentGifJsonObject.getString("username"),
                        currentGifJsonObject.getString("date"),
                        currentGifJsonObject.getString("tags"));

                gifInfoList.add(gifInfo);
            }

            tagResponse.setGifInfoList(gifInfoList);

        } catch (JSONException jsonException) {
            // TODO Handle exception

        }

        return tagResponse;
    }

    public interface SearchGifByTagResponseListener {
        void onSearchGifByTagResponse(TagResponse tagResponse, ApplicationError applicationError);
    }
}
