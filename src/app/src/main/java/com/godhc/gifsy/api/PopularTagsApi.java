package com.godhc.gifsy.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.godhc.gifsy.models.ApplicationError;
import com.godhc.gifsy.models.PopularTag;
import com.godhc.gifsy.network.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

import com.godhc.gifsy.utlis.Constants.*;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PopularTagsApi {

    Context context;

    public PopularTagsApi(Context context) {
        super();

        this.context = context;
    }

    public void getPopularTags(final PopularTagsDataLoadedListener popularTagsDataLoadedListener) {
        //Get from network
        RequestQueue requestQueue = VolleySingleton.getInstance(this.context).getRequestQueue();

        JsonArrayRequest popularTagsJsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Urls.POPULAR_TAGS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        popularTagsDataLoadedListener.onPopularTagsDataLoaded(parseResponse(response), null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        popularTagsDataLoadedListener.onPopularTagsDataLoaded(parseResponse(null),
                                new ApplicationError(ErrorMessages.POPULAR_TAGS_FETCH_ERROR,
                                        error.getMessage()));
                    }
                }
        );

        requestQueue.add(popularTagsJsonArrayRequest);
    }

    private List<PopularTag> parseResponse(JSONArray popularTagsResponse) {
        List<PopularTag> popularTagList = new ArrayList<>();
        if (popularTagsResponse == null) {
            return popularTagList;
        }

        // Populate the popularTagList by parsing the response
        for (int i = 0; i < popularTagsResponse.length(); i++) {
            try {
                JSONObject popularTagJsonObject = popularTagsResponse.getJSONObject(i);

                popularTagList.add(new PopularTag(popularTagJsonObject.getString("tag"),
                        popularTagJsonObject.getString("img_url"),
                        popularTagJsonObject.getInt("count")));

            } catch (JSONException jsonException) {
                // TODO: Handle exception
            }
        }

        return popularTagList;
    }

    public interface PopularTagsDataLoadedListener {
        void onPopularTagsDataLoaded(List<PopularTag> popularTags, ApplicationError applicationError);
    }
}
