/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.tag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.models.TagItems;
import com.coderschool.android2.rubit.utils.RoundedImg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 12/11/16.
 */
public class ChooseTagAdapter extends RecyclerView.Adapter<ChooseTagAdapter.ViewHolder> {

    private List<TagItems> mTagTitles;
    private List<TagItems> mSelectedTags = new ArrayList<>();
    private Context context;

    private TagScreenAdapterCallback mTagScreenAdapterCallback;

    public ChooseTagAdapter(List<TagItems> tagTitles, TagScreenAdapterCallback tagScreenAdapterCallback) {
        mTagTitles = tagTitles;
        this.mTagScreenAdapterCallback = tagScreenAdapterCallback;
    }


//    public void setTagScreenAdapterCallback(TagScreenAdapterCallback tagScreenAdapterCallback) {
//        mTagScreenAdapterCallback = tagScreenAdapterCallback;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View tagView = layoutInflater.inflate(R.layout.tag_item_view, parent, false);
        return new ViewHolder(tagView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setRandomBackgroundToTags(holder);
        int position1 = holder.getAdapterPosition();
        TagItems tagItems = mTagTitles.get(position1);
        holder.text.setText(tagItems.getTagName());
        Bitmap bm = BitmapFactory.decodeResource(holder.text.getResources(), tagItems.getTagImages());
        RoundedImg roundedImg = new RoundedImg(bm);
        holder.image.setImageDrawable(roundedImg);

        holder.image.setOnClickListener(new View.OnClickListener() {
            int buttonClickPosition = 0;

            @Override
            public void onClick(View v) {
                if (buttonClickPosition == 0) {
                    holder.overlay_image.setVisibility(View.VISIBLE);
                    buttonClickPosition = 1;
                    mSelectedTags.add(mTagTitles.get(position));
                    Log.d("user like tech:-", mSelectedTags.toString() + mSelectedTags.size());
                } else {
                    holder.overlay_image.setVisibility(View.GONE);
                    buttonClickPosition = 0;
                    mSelectedTags.remove(mTagTitles.get(position).getTagName().toString());
                    Log.d("user unselected tech:-", mSelectedTags.toString() + mSelectedTags.size());
                }
                mTagScreenAdapterCallback.onTagSelectedCallback(mSelectedTags);
            }
        });
    }

    private void setRandomBackgroundToTags(ViewHolder holder) {
        int randomColor = getRandomColor(holder.text.getContext());

        GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]
                {randomColor, randomColor});
        gradient.setShape(GradientDrawable.OVAL);
        gradient.setCornerRadius(10.f);
        holder.lay_rel_img.setBackground(gradient);
    }

    private int getRandomColor(Context context) {
        int[] randomColors = context.getResources().getIntArray(R.array.randomcolors);
        return randomColors[new Random().nextInt(randomColors.length)];
    }

    @Override
    public int getItemCount() {
        return mTagTitles.size();
    }

    public interface TagScreenAdapterCallback {
        void onTagSelectedCallback(List<TagItems> selectedTags);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView1)
        ImageView image;

        @BindView(R.id.text)
        TextView text;

        @BindView(R.id.overlay_image)
        RelativeLayout overlay_image;

        @BindView(R.id.lay_rel_img)
        RelativeLayout lay_rel_img;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
