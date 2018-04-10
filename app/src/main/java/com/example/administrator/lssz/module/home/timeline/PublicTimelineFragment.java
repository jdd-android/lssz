package com.example.administrator.lssz.module.home.timeline;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.common.StatusClickCallback;
import com.example.administrator.lssz.listener.EndlessRecyclerOnScrollListener;
import com.example.administrator.lssz.module.comment.StatusCommentActivity;
import com.example.administrator.lssz.views.LoadMoreRecyclerView;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class PublicTimelineFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.PullActionListener, StatusClickCallback {

    private final static String STATUS_ID = "id";
    private final static String STATUS = "status";

    private SwipeRefreshLayout mRefreshLy;
    private LoadMoreRecyclerView mRecyclerView;
    private StatusesAdapter mAdapter;

    private PublicTimelineViewModel mViewModel;

    public static PublicTimelineFragment getNewInstance() {
        return new PublicTimelineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statuses_timeline, null);
        mRefreshLy = rootView.findViewById(R.id.layout_swipe_refresh);
        mRefreshLy.setOnRefreshListener(this);
        mAdapter = new StatusesAdapter(getActivity(), this);
        mRecyclerView = rootView.findViewById(R.id.statuses_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setPullActionListener(this);
        mRecyclerView.setAdapter(mAdapter);

        //创建ViewModel
        mViewModel = ViewModelProviders.of(this).get(PublicTimelineViewModel.class);
        //监听
        subscribeUi(mViewModel);
        //刷新
        mViewModel.refresh();


        return rootView;
    }

    private void subscribeUi(PublicTimelineViewModel viewModel) {
        //监听刷新状态
        viewModel.getObservableIsRefreshing().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean refreshing) {
                mRefreshLy.setRefreshing(refreshing != null && refreshing);
            }
        });

        //监听列表数据变化
        viewModel.getObservableStatusesList().observe(this, new Observer<List<StatusBean>>() {
            @Override
            public void onChanged(@Nullable List<StatusBean> statusBeans) {
                mAdapter.setStatusesList(statusBeans);
            }
        });

        //监听是否加载完全
        viewModel.getObservableIsCompleteLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean completeLoading) {
                if (completeLoading) {
                    mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        mViewModel.refresh();
    }

    @Override
    public void onPullUpLoadMore() {
    }

    @Override
    public void onClick(StatusBean status) {
        //跳转评论页面
        String statusBeanString = JSON.toJSONString(status);
        Intent intent = new Intent(getActivity(), StatusCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(STATUS_ID, status.getId());
        bundle.putString(STATUS, statusBeanString);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}