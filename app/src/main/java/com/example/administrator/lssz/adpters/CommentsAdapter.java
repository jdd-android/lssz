package com.example.administrator.lssz.adpters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.CommentBean;
import com.example.administrator.lssz.common.utils.DateUtils;
import com.example.administrator.lssz.databinding.CommentItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private Context context;
    private List<CommentBean> comments = new ArrayList<>();
    private LayoutInflater mInflater;

    public CommentsAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding binding= DataBindingUtil.inflate(mInflater,R.layout.comment_item,parent,false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        CommentItemBinding binding=DataBindingUtil.getBinding(holder.itemView);
        binding.setComment(comments.get(position));
        binding.executePendingBindings();

    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{
        CommentViewHolder(CommentItemBinding binding){
            super(binding.getRoot());
        }
    }
}
