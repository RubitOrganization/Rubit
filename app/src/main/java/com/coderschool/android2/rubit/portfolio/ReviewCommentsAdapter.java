/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.portfolio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.models.ReviewCommentsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 20/11/16.
 */

public class ReviewCommentsAdapter extends RecyclerView.Adapter<ReviewCommentsAdapter.ViewHolder> {
    private Context mContext;
    private List<ReviewCommentsModel> mComments;

    public ReviewCommentsAdapter(Context context, List<ReviewCommentsModel> reviewCommentsModels) {
        this.mContext = context;
        this.mComments = reviewCommentsModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_review, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewCommentsModel commentsModel = mComments.get(position);
        doesCommentContainImage(holder, commentsModel);
        loadReviewerAvatar(holder, commentsModel);
        holder.reviewerUserName.setText(commentsModel.getReviewerName());
        holder.ratingBar.setRating(commentsModel.getRatingCount());
        holder.requestNameTv.setText(commentsModel.getHeadingRequestName());
        holder.reviewerComments.setText(commentsModel.getCommentText());
    }

    private void loadReviewerAvatar(ViewHolder holder, ReviewCommentsModel commentsModel) {
        Glide.with(mContext)
                .load(commentsModel
                        .getReviewerAvatar()).fitCenter().centerCrop()
                .into(holder.reviewerImageAvatar);
    }

    private void doesCommentContainImage(ViewHolder holder, ReviewCommentsModel commentsModel) {
        if (commentsModel.getImageUrl() != null) {
            holder.commentImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(commentsModel
                            .getImageUrl())
                    .into(holder.commentImageView);
        } else {
            holder.commentImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.commentImageView)
        ImageView commentImageView;
        @BindView(R.id.reviewerImageAvatar)
        ImageView reviewerImageAvatar;
        @BindView(R.id.reviewerUserName)
        TextView reviewerUserName;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.requestNameTv)
        TextView requestNameTv;
        @BindView(R.id.reviewerComments)
        TextView reviewerComments;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
