package com.example.administrator.lssz.adpters;

import android.content.Context;
import android.databinding.DataBindingUtil;

import android.support.annotation.Nullable;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.StatusClickCallback;

import com.example.administrator.lssz.common.utils.PicUrlUtils;
import com.example.administrator.lssz.databinding.RepostStatusItemBinding;
import com.example.administrator.lssz.databinding.StatusItemBinding;
import com.example.administrator.lssz.dialogs.CompleteImageDialog;
import com.w4lle.library.NineGridlayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class StatusesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //POST_STATUS
    private final int TYPE_ITEM_POST = 0;
    //RE_POST_STATUS
    private final int TYPE_ITEM_REPOST = 1;
    //脚布局
    private final int TYPE_FOOTER = 2;
    //加载
    public final int LOADING = 1;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    private int loadState = 2;

    private List<StatusBean> mStatusesList = new ArrayList<>();

    private Context mContext;
    @Nullable
    private StatusClickCallback mStatusClickCallback;

    public StatusesAdapter(Context context, @Nullable StatusClickCallback statusClickCallback) {
        mContext=context;
        mStatusClickCallback = statusClickCallback;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else if (mStatusesList.get(position).getRetweetedStatus() != null) {
            return TYPE_ITEM_REPOST;
        } else {
            return TYPE_ITEM_POST;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StatusViewHolder) {
            final StatusItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
            binding.setStatus(mStatusesList.get(position));
            binding.setCallback(mStatusClickCallback);
            binding.setListener(new NineGridlayout.OnItemClickListerner() {
                @Override
                public void onItemClick(View view, int position) {
                    CompleteImageDialog myDialog = new CompleteImageDialog(mContext, PicUrlUtils.getOriginalPic(binding.getStatus().getPicUrlsList().get(position).getThumbnailPic()));
                    myDialog.show();
                    Window dialogWin = myDialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWin.getAttributes();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialogWin.setAttributes(lp);
                }
            });
            binding.executePendingBindings();
//
//            StatusViewHolder statusViewHolder = (StatusViewHolder) holder;
//            final StatusBean statusBean = mStatusesList.get(position);
//            //给微博状态视图设置tag，传入当前statusBean
//            statusViewHolder.itemView.setTag(statusBean);
//
//            Glide.with(context).load(statusBean.getUser().getAvatarLarge())
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(statusViewHolder.statusUserIamge);
//            statusViewHolder.statusUserName.setText(statusBean.getUser().getName());
//            statusViewHolder.statusTime.setText(DateUtils.readableDate(statusBean.getCreatedAt()));
//            statusViewHolder.statusText.setText(StatusUtils.getClickableSpan(statusBean.getText()));
//            statusViewHolder.statusText.setMovementMethod(LinkMovementMethod.getInstance());
//
//            //加载图片，设置图片点击监听
//            statusViewHolder.statusPics.setAdapter(new ImageLoadAdapter(context, statusBean.getPicUrlsList()));
//            statusViewHolder.statusPics.setOnItemClickListerner(new NineGridlayout.OnItemClickListerner() {
//                @Override
//                public void onItemClick(View view, int position) {
////                    Toast.makeText(context, "这是第 " + (position + 1) + " 张图，url为 " + statusBean.getPicUrlsList().get(position).getThumbnailPic(), Toast.LENGTH_SHORT).show();
//                    CompleteImageDialog myDialog = new CompleteImageDialog(context, PicUrlUtils.getOriginalPic(statusBean.getPicUrlsList().get(position).getThumbnailPic()));
//                    myDialog.show();
//                    Window dialogWin = myDialog.getWindow();
//                    WindowManager.LayoutParams lp = dialogWin.getAttributes();
//                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                    dialogWin.setAttributes(lp);
//                }
//            });
        } else if (holder instanceof RepostStatusViewHolder) {
            final RepostStatusItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
            binding.setCallback(mStatusClickCallback);
            binding.setStatus(mStatusesList.get(position));
            binding.setListener(new NineGridlayout.OnItemClickListerner() {
                @Override
                public void onItemClick(View view, int position) {
                    CompleteImageDialog myDialog = new CompleteImageDialog(mContext, PicUrlUtils.getOriginalPic(binding.getStatus().getPicUrlsList().get(position).getThumbnailPic()));
                    myDialog.show();
                    Window dialogWin = myDialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWin.getAttributes();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialogWin.setAttributes(lp);
                }
            });
            binding.executePendingBindings();

        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING:
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        //加一个FooterView
        return mStatusesList.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_POST) {
            StatusItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.status_item, parent, false);
            return new StatusViewHolder(binding);
        } else if (viewType == TYPE_ITEM_REPOST) {
            RepostStatusItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.repost_status_item, parent, false);
            return new RepostStatusViewHolder(binding);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.refresh_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    public void setStatusesList(final List<StatusBean> statusesList) {
        mStatusesList = statusesList;
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {

        StatusViewHolder(StatusItemBinding binding) {
            super(binding.getRoot());
        }
    }

    static class RepostStatusViewHolder extends RecyclerView.ViewHolder {
        RepostStatusViewHolder(RepostStatusItemBinding binding) {
            super(binding.getRoot());
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
