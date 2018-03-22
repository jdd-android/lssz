package com.example.administrator.lssz.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.utils.CircleCrop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class StatusesAdapter extends RecyclerView.Adapter<StatusesAdapter.StatusViewHolder> {
    List<StatusBean> mStatusesList = new ArrayList<>();
    private Context context;

    public void setStatusesList(final List<StatusBean> statusesList) {
        mStatusesList = statusesList;
    }

    // TODO 不会变的成员属性，在构造函数里传，保证本类中任何时候获取都不会是null
    public void setContext(final Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(StatusViewHolder holder, int position) {
        StatusBean statusBean = mStatusesList.get(position);
        Glide.with(context).load(statusBean.getUser().getProfileImageUrl())
                .transform(new CircleCrop(context))
                // FIXME 传递 width height 时想一下应该传递的是 px 还是 dp，没发现头像模糊么
                //override 传递px
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

    static class StatusViewHolder extends RecyclerView.ViewHolder {
        ImageView statusIamge;
        TextView statusName;
        TextView statusTime;
        TextView statusText;

        public StatusViewHolder(View view) {
            super(view);
            statusIamge = view.findViewById(R.id.iv_status_image);
            statusName = view.findViewById(R.id.tv_status_name);
            statusTime = view.findViewById(R.id.tv_status_time);
            statusText = view.findViewById(R.id.tv_status_text);

        }
    }


}
