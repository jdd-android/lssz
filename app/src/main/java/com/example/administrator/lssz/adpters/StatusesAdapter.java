package com.example.administrator.lssz.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.StatusBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class StatusesAdapter extends RecyclerView.Adapter<StatusesAdapter.StatusViewHolder> {

    private List<StatusBean> mStatusesList = new ArrayList<>();
    private Context context;

    public StatusesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(StatusViewHolder holder, int position) {
        StatusBean statusBean = mStatusesList.get(position);
        Glide.with(context).load(statusBean.getUser().getAvatarLarge())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.statusIamge);
        holder.statusName.setText(statusBean.getUser().getName());
        holder.statusTime.setText(statusBean.getCreatedAt());
        holder.statusText.setText(statusBean.getText());
    }

    @Override
    public int getItemCount() {
        return mStatusesList.size();
    }

    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_item, parent, false);
        StatusViewHolder holder = new StatusViewHolder(view);
        return holder;
    }

    public void setStatusesList(final List<StatusBean> statusesList) {
        mStatusesList = statusesList;
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {
        ImageView statusIamge;
        TextView statusName;
        TextView statusTime;
        TextView statusText;

        StatusViewHolder(View view) {
            super(view);
            statusIamge = view.findViewById(R.id.iv_status_image);
            statusName = view.findViewById(R.id.tv_status_name);
            statusTime = view.findViewById(R.id.tv_status_time);
            statusText = view.findViewById(R.id.tv_status_text);

        }
    }


}
