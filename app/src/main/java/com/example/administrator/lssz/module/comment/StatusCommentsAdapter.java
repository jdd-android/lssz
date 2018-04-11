package com.example.administrator.lssz.module.comment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.CommentBean;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.utils.PicUrlUtils;
import com.example.administrator.lssz.common.utils.StatusUtils;
import com.example.administrator.lssz.databinding.CommentItemBinding;
import com.example.administrator.lssz.databinding.RepostStatusItemBinding;
import com.example.administrator.lssz.databinding.StatusItemBinding;
import com.example.administrator.lssz.dialogs.CompleteImageDialog;
import com.w4lle.library.NineGridlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class StatusCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_ITEM_POST = 0;
    private final int TYPE_ITEM_REPOST = 1;
    private final int TYPE_ITEM_COMMENT = 2;

    private Context mContext;
    private StatusBean mStatus;
    private List<CommentBean> mCommentList = new ArrayList<>();

    public StatusCommentsAdapter(Context context) {
        mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_POST) {
            StatusItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.status_item, parent, false);
            return new StatusViewHolder(binding);
        } else if (viewType == TYPE_ITEM_REPOST) {
            RepostStatusItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.repost_status_item, parent, false);
            return new RepostStatusViewHolder(binding);
        } else if (viewType == TYPE_ITEM_COMMENT) {
            CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item, parent, false);
            return new CommentViewHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StatusViewHolder) {
            final StatusItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
            binding.setStatus(mStatus);
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
        }else if(holder instanceof RepostStatusViewHolder){
            final RepostStatusItemBinding binding=DataBindingUtil.getBinding(holder.itemView);
            binding.setStatus(mStatus);
            binding.setListener(new NineGridlayout.OnItemClickListerner() {
                @Override
                public void onItemClick(View view, int position) {
                    CompleteImageDialog myDialog = new CompleteImageDialog(mContext, PicUrlUtils.getOriginalPic(StatusUtils.getOriginStatus(binding.getStatus()).getPicUrlsList().get(position).getThumbnailPic()));
                    myDialog.show();
                    Window dialogWin = myDialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWin.getAttributes();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialogWin.setAttributes(lp);
                }
            });
            binding.executePendingBindings();
        }else if(holder instanceof CommentViewHolder){
            CommentItemBinding binding=DataBindingUtil.getBinding(holder.itemView);
            binding.setComment(mCommentList.get(position-1));
            binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        //加一个HeadView
        return mCommentList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0) {
            return TYPE_ITEM_COMMENT;
        } else if (position == 0 && mStatus.getRetweetedStatus() != null) {
            return TYPE_ITEM_REPOST;
        } else {
            return TYPE_ITEM_POST;
        }
    }

    public void setCommentList(List<CommentBean> commentList) {
        mCommentList = commentList;
    }

    public void setStatus(StatusBean status) {
        mStatus = status;
    }

    class StatusViewHolder extends RecyclerView.ViewHolder {
        StatusViewHolder(StatusItemBinding binding) {
            super(binding.getRoot());
        }
    }

    class RepostStatusViewHolder extends RecyclerView.ViewHolder {
        RepostStatusViewHolder(RepostStatusItemBinding binding) {
            super(binding.getRoot());
        }
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        CommentViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
        }
    }
}
