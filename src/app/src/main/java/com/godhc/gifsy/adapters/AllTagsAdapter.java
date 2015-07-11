package com.godhc.gifsy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.godhc.gifsy.R;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
import java.util.List;

public class AllTagsAdapter extends RecyclerView.Adapter<AllTagsAdapter.ViewHolder>{

    Context context;
    List<String> allTagsList;
    TagClickListener tagClickListener;

    public AllTagsAdapter(Context context, List<String> allTagsList) {
        super();

        this.context = context;
        this.allTagsList = allTagsList;

        if (allTagsList == null)
            this.allTagsList = new ArrayList<>();

    }

    @Override
    public AllTagsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.fragment_explore_rv_row_alltags, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllTagsAdapter.ViewHolder holder, int position) {
        String currentTag = this.allTagsList.get(position);
        if (currentTag !=null){
            holder.tag.setText(currentTag);

            ColorGenerator generator = ColorGenerator.MATERIAL;

            TextDrawable textDrawable = TextDrawable.builder()
                    .buildRound(currentTag.substring(0, 1), generator.getColor(currentTag));

            holder.image.setImageDrawable(textDrawable);
        }
    }

    @Override
    public int getItemCount() {
        return this.allTagsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tag;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);

            tag = (TextView) itemView.findViewById(R.id.fragment_explore_rv_row_alltags_tag);
            tag.setTypeface(EasyFonts.caviarDreamsBold(context));

            image = (ImageView) itemView.findViewById(R.id.fragment_explore_rv_row_alltags_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (tagClickListener != null)
                tagClickListener.onTagClick(view);
        }
    }

    public void setData(List<String> allTagsList){
        this.allTagsList = allTagsList;
        notifyDataSetChanged();
    }

    public void setTagClickListener(TagClickListener tagClickListener){
        this.tagClickListener = tagClickListener;
    }
    public interface TagClickListener {
        void onTagClick(View view);
    }
}
