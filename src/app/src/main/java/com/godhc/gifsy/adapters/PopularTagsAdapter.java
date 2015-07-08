package com.godhc.gifsy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.godhc.gifsy.R;
import com.godhc.gifsy.models.PopularTag;

import java.util.ArrayList;
import java.util.List;

public class PopularTagsAdapter extends RecyclerView.Adapter<PopularTagsAdapter.ViewHolder> {

    Context context;
    List<PopularTag> popularTags;

    public PopularTagsAdapter(Context context, List<PopularTag> popularTags) {
        super();
        this.context = context;
        this.popularTags = new ArrayList<>();

        if (popularTags != null) {
            this.popularTags = popularTags;
        }
    }

    @Override
    public PopularTagsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_main_rv_row_popular_tags, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PopularTagsAdapter.ViewHolder holder, int position) {
        PopularTag currentPopularTag = this.popularTags.get(position);

        if (currentPopularTag != null){
            holder.nameTV.setText(currentPopularTag.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return this.popularTags.size();
    }

    // Set the data source and notify the change
    public void setData(List<PopularTag> popularTags){
        this.popularTags = popularTags;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;
        public ViewHolder(View itemView) {
            super(itemView);

            nameTV = (TextView) itemView.findViewById(R.id.activity_main_rv_row_popularTags_name);
        }
    }
}
