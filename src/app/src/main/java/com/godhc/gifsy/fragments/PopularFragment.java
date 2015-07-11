package com.godhc.gifsy.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.godhc.gifsy.R;
import com.godhc.gifsy.activities.TagGifsActivity;
import com.godhc.gifsy.adapters.PopularTagsAdapter;
import com.godhc.gifsy.api.PopularTagsApi;
import com.godhc.gifsy.models.ApplicationError;
import com.godhc.gifsy.models.PopularTag;
import com.orhanobut.logger.Logger;

import com.godhc.gifsy.utlis.Constants.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment implements PopularTagsAdapter.PopularTagClickListener {


    RecyclerView recyclerView;
    PopularTagsAdapter popularTagsAdapter;
    List<PopularTag> popularTagList;

    public PopularFragment() {
        popularTagList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.activity_main_rv_popularTags);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, 1));

        popularTagsAdapter = new PopularTagsAdapter(getActivity(), null);
        popularTagsAdapter.setPopularTagClickListener(this);
        recyclerView.setAdapter(popularTagsAdapter);

        recyclerView.setHasFixedSize(false);

        loadData();
    }

    @Override
    public void onPopularTagClick(View view) {
        int position = this.recyclerView.getChildLayoutPosition(view);
        PopularTag selectedPopularTag = this.popularTagList.get(position);

        Intent intent = new Intent();
        intent.setClass(getActivity(), TagGifsActivity.class);
        intent.putExtra(GlobalConstants.ACTIVITY_TAG_GIFS_TAG_NAME, selectedPopularTag.getTag());

        startActivity(intent);
    }

    private void loadData() {
        PopularTagsApi popularTagsApi = popularTagsApi = new PopularTagsApi(getActivity());
        popularTagsApi.getPopularTags(new PopularTagsApi.PopularTagsDataLoadedListener() {
            @Override
            public void onPopularTagsDataLoaded(List<PopularTag> popularTags, ApplicationError applicationError) {
                if (applicationError == null) {
                    popularTagList = popularTags;
                    popularTagsAdapter.setData(popularTagList);
                } else {
                    Logger.e(applicationError.getMessage());
                    Logger.e(applicationError.getInternalMessage());
                    // TODO: Show error on ui
                }
            }
        });
    }
}
