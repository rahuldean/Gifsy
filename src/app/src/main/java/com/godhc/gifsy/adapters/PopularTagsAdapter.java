package com.godhc.gifsy.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
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

        if (currentPopularTag != null) {
            holder.nameTV.setText(currentPopularTag.getTag());

            String imageUrl = "http://lorempixel.com/120/120/sports/" + position % 10;
            if (!currentPopularTag.getImgUrl().isEmpty())
                imageUrl = currentPopularTag.getImgUrl();

            Uri uri = Uri.parse(imageUrl);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .build();

            holder.imageDraweeView.setController(draweeController);
        }
    }

    @Override
    public int getItemCount() {
        return this.popularTags.size();
    }

    // Set the data source and notify the change
    public void setData(List<PopularTag> popularTags) {
        this.popularTags = popularTags;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;
        DraweeView imageDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTV = (TextView) itemView.findViewById(R.id.activity_main_rv_row_popularTags_name);
            imageDraweeView = (DraweeView) itemView.findViewById(R.id.activity_main_rv_row_popularTags_image);
        }
    }
}
