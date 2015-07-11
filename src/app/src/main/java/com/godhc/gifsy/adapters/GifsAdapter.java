package com.godhc.gifsy.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.godhc.gifsy.R;
import com.godhc.gifsy.models.GifInfo;
import com.godhc.gifsy.utlis.TimeAgo;
import com.orhanobut.logger.Logger;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
import java.util.List;

public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.ViewHolder> {

    Context context;
    List<GifInfo> gifInfoList;

    public GifsAdapter(Context context, List<GifInfo> gifInfoList) {
        this.context = context;

        this.gifInfoList = gifInfoList;

        if (gifInfoList == null)
            this.gifInfoList = new ArrayList<>();
    }

    @Override
    public GifsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.activity_tag_gifs_rv_row_gifs, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GifsAdapter.ViewHolder holder, int position) {
        GifInfo currentGifInfo = this.gifInfoList.get(position);

        if (currentGifInfo != null) {
            Uri uri = Uri.parse(currentGifInfo.getUrl());

            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(uri)
                    .setAutoRotateEnabled(true)
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .setProgressiveRenderingEnabled(false)
                    .build();

            PipelineDraweeController draweeController = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(true)
                    .build();

            holder.imageView.setDrawingCacheEnabled(true);
            holder.imageView.setController(draweeController);

            long updatedAtDate = Long.parseLong(currentGifInfo.getDate());
            Logger.i(Long.toString(updatedAtDate));

            TimeAgo timeAgo = new TimeAgo(this.context);

//            Logger.d(timeAgo.timeAgo(updatedAtDate));

            holder.lastUpdatedAt.setText(timeAgo.timeAgo(updatedAtDate));
            holder.lastUpdatedAt.setVisibility(View.GONE);

            holder.username.setText(currentGifInfo.getUsername());
            holder.username.setTypeface(EasyFonts.walkwayUltraBold(this.context));

            holder.tags.setText(getFormattedTags(currentGifInfo.getTags()));
            holder.tags.setTypeface(EasyFonts.droidSerifRegular(this.context));
        }

    }

    @Override
    public int getItemCount() {
        return this.gifInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView lastUpdatedAt;
        TextView username;
        TextView tags;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.activity_tag_gifs_rv_row_image);
            lastUpdatedAt = (TextView) itemView.findViewById(R.id.activity_tag_gifs_rv_row_updatedTime);
            username = (TextView) itemView.findViewById(R.id.activity_tag_gifs_rv_row_username);
            tags = (TextView) itemView.findViewById(R.id.activity_tag_gifs_rv_row_tags);
        }
    }

    public void setData(List<GifInfo> data) {
        this.gifInfoList = data;
        notifyDataSetChanged();
    }

    public void appendData(List<GifInfo> appendData) {
        this.gifInfoList.addAll(appendData);
        notifyDataSetChanged();
    }

    private String getFormattedTags(List<String> tagsList) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tagsList.size(); i++) {
            sb.append("#");
            sb.append(tagsList.get(i).toUpperCase());
            if (i < tagsList.size() - 1)
                sb.append(" | ");
        }

        return sb.toString();
    }
}
