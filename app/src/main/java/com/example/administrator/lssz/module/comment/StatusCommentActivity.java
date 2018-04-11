package com.example.administrator.lssz.module.comment;

import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.CommentBean;
import com.example.administrator.lssz.beans.StatusBean;

import java.util.List;

public class StatusCommentActivity extends FragmentActivity {
    private final static String STATUS_ID = "id";
    private final static String STATUS = "status";

    private RecyclerView mRecyclerView;
    private StatusCommentsAdapter mAdapter;
    private StatusCommentViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_comment);
        String statusId = this.getIntent().getExtras().getString(STATUS_ID);
        StatusBean status = JSONObject.parseObject(this.getIntent().getExtras().getString(STATUS), StatusBean.class);

        mAdapter = new StatusCommentsAdapter(StatusCommentActivity.this);

        mRecyclerView = (RecyclerView) findViewById(R.id.comments_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StatusCommentActivity.this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(StatusCommentActivity.this).get(StatusCommentViewModel.class);
        mViewModel.setObservableStatus(status);
        subscribeUi(mViewModel);
        mViewModel.loadComment();
    }

    void subscribeUi(StatusCommentViewModel viewModel) {
        viewModel.getObservableStatus().observe(this, new Observer<StatusBean>() {
            @Override
            public void onChanged(@Nullable StatusBean statusBean) {
                mAdapter.setStatus(statusBean);
            }
        });

        viewModel.getObservableCommentList().observe(this, new Observer<List<CommentBean>>() {
            @Override
            public void onChanged(@Nullable List<CommentBean> commentBeans) {
                mAdapter.setCommentList(commentBeans);
                mAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getObservableNoComments().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean noComments) {
                if (noComments != null && noComments) {
                    Toast.makeText(StatusCommentActivity.this, "No Comment or Comment can't see", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
