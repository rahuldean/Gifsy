package com.godhc.gifsy.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.godhc.gifsy.models.ApplicationError;
import com.godhc.gifsy.network.VolleySingleton;
import com.godhc.gifsy.utlis.Constants.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AllTagsApi {
    Context context;

    public AllTagsApi(Context context) {
        super();

        this.context = context;
    }

    public void getAllTags(final AllTagsDataLoadedListener allTagsDataLoadedListener) {
        RequestQueue requestQueue = VolleySingleton.getInstance(this.context).getRequestQueue();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Urls.ALL_TAGS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<String> allTags = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                if (!response.getString(i).isEmpty())
                                    allTags.add(response.getString(i));
                            }

                            allTagsDataLoadedListener.onAllTagsDataLoaded(allTags, null);
                        } catch (JSONException jsonException) {
                            allTagsDataLoadedListener.onAllTagsDataLoaded(null,
                                    new ApplicationError(ErrorMessages.ALL_TAGS_FETCH_ERROR, jsonException.getMessage()));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        allTagsDataLoadedListener.onAllTagsDataLoaded(null,
                                new ApplicationError(ErrorMessages.ALL_TAGS_FETCH_ERROR, error.getMessage()));
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    public interface AllTagsDataLoadedListener {
        void onAllTagsDataLoaded(List<String> allTags, ApplicationError applicationError);
    }
}
