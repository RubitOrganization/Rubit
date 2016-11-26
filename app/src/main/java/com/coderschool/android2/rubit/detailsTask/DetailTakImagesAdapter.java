/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.detailsTask;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.coderschool.android2.rubit.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 20/11/16.
 */

public class DetailTakImagesAdapter extends RecyclerView.Adapter<DetailTakImagesAdapter.ViewHolder> {
    private Context mContext;
    private List<String> imagesUrl;

    public DetailTakImagesAdapter(Context context, List<String> imagesUrls) {
        this.mContext = context;
        this.imagesUrl = imagesUrls;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageResponse = imagesUrl.get(position);
        Glide.with(mContext)
                .load(imageResponse)
                .into(holder.imageViewItem);
    }

    @Override
    public int getItemCount() {
        return imagesUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewItem)
        AppCompatImageView imageViewItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
