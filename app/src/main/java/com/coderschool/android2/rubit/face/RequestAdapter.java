/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.face;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.detailsTask.DetailsTaskActivity;
import com.coderschool.android2.rubit.models.RequestSimpleModel;
import com.coderschool.android2.rubit.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RequestAdapter
 *
 * @author TienNguyen
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private final Context mContext;
    private final List<RequestSimpleModel> mRequests;
    private final Handler mHandler;
    private final Runnable mRunnable;

    /**
     * Constructor
     *
     * @param context  Context
     * @param requests List<RequestSimpleModel>
     * @param handler  Handler
     * @param runnable Runnable
     */
    public RequestAdapter(Context context, List<RequestSimpleModel> requests, Handler handler, Runnable runnable) {
        this.mContext = context;
        this.mRequests = requests;
        this.mHandler = handler;
        this.mRunnable = runnable;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final View view = layoutInflater.inflate(R.layout.item_single_request, parent, false);
        return new RequestViewHolder(view, mContext, mRequests, mHandler, mRunnable);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        final RequestSimpleModel model = mRequests.get(position);
        if (model != null) {
            holder.txtPop.setText(model.getRequestModel().getSubject());
            ImageUtils.loadingImageWithRoundTransform(mContext, holder.imgProfilePicture, model.getUserModel().getPhotoUrl(), true);
        }
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final List<RequestSimpleModel> mRequests;
        private final Handler mHandler;
        private final Runnable mRunnable;
        @BindView(R.id.txtPop)
        TextView txtPop;
        @BindView(R.id.imgProfilePicture)
        ImageView imgProfilePicture;

        RequestViewHolder(View itemView, Context context, List<RequestSimpleModel> requests, Handler handler, Runnable runnable) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            this.mContext = context;
            this.mRequests = requests;
            this.mHandler = handler;
            this.mRunnable = runnable;

            txtPop.setOnClickListener(view -> selectRequest());
            imgProfilePicture.setOnClickListener(view -> selectRequest());
        }

        /**
         * selectRequest
         */
        private void selectRequest() {
            final int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                final RequestSimpleModel requestSimple = mRequests.get(position);
                if (requestSimple != null) {
                    mHandler.removeCallbacks(mRunnable);

                    final Intent intent = new Intent(mContext, DetailsTaskActivity.class);
                    intent.putExtra(IntentConstants.REQUEST_MODEL, requestSimple.getRequestModel());
                    intent.putExtra(IntentConstants.USER, requestSimple.getUserModel());
                    mContext.startActivity(intent);
                }
            }
        }
    }
}
