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
import com.example.administrator.lssz.beans.CommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private Context context;
    private List<CommentBean> comments=new ArrayList<>();

    public CommentsAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        CommentViewHolder holder=new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        CommentBean comment=comments.get(position);
        Glide.with(context).load(comment.getUserBean().getAvatarLarge())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivCommentUserIamge);
        holder.tvCommentUserName.setText(comment.getUserBean().getName());
        holder.tvCommentUserText.setText(comment.getText());
        holder.tvCommentUserTime.setText(comment.getCreatedAt());

    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCommentUserIamge;
        TextView tvCommentUserName;
        TextView tvCommentUserText;
        TextView tvCommentUserTime;

        CommentViewHolder(View itemView) {
            super(itemView);
            ivCommentUserIamge = (ImageView) itemView.findViewById(R.id.iv_comment_user_image);
            tvCommentUserName = (TextView) itemView.findViewById(R.id.tv_comment_name);
            tvCommentUserText = (TextView) itemView.findViewById(R.id.tv_status_text);
            tvCommentUserTime = (TextView) itemView.findViewById(R.id.tv_comment_time);


        }
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }
}
