package com.godhc.gifsy.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.godhc.gifsy.R;
import com.godhc.gifsy.activities.TagGifsActivity;
import com.godhc.gifsy.adapters.AllTagsAdapter;
import com.godhc.gifsy.api.AllTagsApi;
import com.godhc.gifsy.models.ApplicationError;
import com.godhc.gifsy.utlis.Constants;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment implements AllTagsAdapter.TagClickListener {

    List<String> tagsList;
    RecyclerView recyclerView;
    AllTagsAdapter allTagsAdapter;

    public ExploreFragment() {
        // Required empty public constructor
        this.tagsList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_explore_rv_allTags);
        allTagsAdapter = new AllTagsAdapter(getActivity(), null);
        allTagsAdapter.setTagClickListener(this);
        recyclerView.setAdapter(allTagsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);

        AllTagsApi allTagsApi = new AllTagsApi(getActivity());
        allTagsApi.getAllTags(new AllTagsApi.AllTagsDataLoadedListener() {
            @Override
            public void onAllTagsDataLoaded(List<String> allTags, ApplicationError applicationError) {
                if (applicationError == null) {
                    tagsList = allTags;
                    allTagsAdapter.setData(tagsList);
                    Logger.d("Got all tags (%d)", allTags.size());
                } else {
                    Logger.e(applicationError.getMessage());
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onTagClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);

        String selectedTag = tagsList.get(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), TagGifsActivity.class);
        intent.putExtra(Constants.GlobalConstants.ACTIVITY_TAG_GIFS_TAG_NAME, selectedTag);

        startActivity(intent);

    }
}
