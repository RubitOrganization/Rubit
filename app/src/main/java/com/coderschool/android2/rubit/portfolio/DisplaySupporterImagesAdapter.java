package com.coderschool.android2.rubit.portfolio;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.models.SupporterImagesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 20/11/16.
 */

public class DisplaySupporterImagesAdapter extends RecyclerView.Adapter<DisplaySupporterImagesAdapter.ViewHolder> {
    private Context mContext;
    private List<SupporterImagesModel> mLoadFakeImages;

    public DisplaySupporterImagesAdapter(Context context, List<SupporterImagesModel> images) {
        this.mContext = context;
        this.mLoadFakeImages = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SupporterImagesModel supporterImagesModel = mLoadFakeImages.get(position);
        Glide.with(mContext)
                .load(supporterImagesModel.getSupporterImageUrl())
                .into(holder.imageViewItem);
    }

    @Override
    public int getItemCount() {
        return mLoadFakeImages.size();
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
