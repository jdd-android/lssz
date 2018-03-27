package com.example.administrator.lssz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.administrator.lssz.LsszApp;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.adpters.StatusesAdapter;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.listener.EndlessRecyclerOnScrollListener;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class FriendsTimelineFragment extends Fragment {
    private final static String STATUS_ID = "id";
    private final static String STATUS = "status";
    private final int PAGE_NUMBER = 6;
    private int page = 1;

    private StatusesAdapter statusesAdapter;
    private SwipeRefreshLayout mRefrshLayout;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;
    private RecyclerView statusesRecyclerView;
    private static Oauth2AccessToken mAccessToken;
    private List<StatusBean> totalStatuses = new ArrayList<>();
    private List<StatusBean> pageStatuses = new ArrayList<>();

    public static FriendsTimelineFragment getNewInstance() {
        return new FriendsTimelineFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statuses_timeline, null);

        //读取令牌
        mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());

        //初始化adapter
        statusesAdapter = new StatusesAdapter(getActivity());
        statusesAdapter.setOnRecyclerViewListener(new StatusesAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(View view, StatusBean statusBean) {
                //跳转评论页面
                String statusBeanString = JSON.toJSONString(statusBean);
                Intent intent = new Intent(getActivity(), StatusCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(STATUS_ID, statusBean.getId());
                bundle.putString(STATUS, statusBeanString);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //设置RecyclerView
        statusesRecyclerView = (RecyclerView) view.findViewById(R.id.statuses_list);
        statusesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        statusesRecyclerView.setAdapter(statusesAdapter);

        //下拉刷新
        mRefrshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestFriendsLineData();
                mRefrshLayout.setRefreshing(false);
            }
        };
        mRefrshLayout.setOnRefreshListener(mRefreshListener);
        //上滑加载
        statusesRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                statusesAdapter.setLoadState(statusesAdapter.LOADING);
                if (pageStatuses.size() < totalStatuses.size()) {
                    loadFriendsLine(getPageStatuses());
                } else {
                    statusesAdapter.setLoadState(statusesAdapter.LOADING_END);
                }
            }
        });
        //显示数据
        if (totalStatuses.isEmpty()) {
            requestFriendsLineData();
        } else {
            loadFriendsLine(getPageStatuses());
        }

        return view;
    }

    private void requestFriendsLineData() {
        new ApiClient().requestFriendsLine(mAccessToken.getToken(), new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                if (!data.isEmpty()) {
                    totalStatuses = data;
                    loadFriendsLine(getPageStatuses());
                }
            }

            @Override
            public void failure(IError error) {
                Log.i("Callback Error", "Callback Error");
            }
        });
    }

    private List<StatusBean> getPageStatuses() {
        if (totalStatuses.size() < pageStatuses.size() + PAGE_NUMBER) {
            for (int i = (page - 1) * PAGE_NUMBER; i < totalStatuses.size(); i++) {
                pageStatuses.add(totalStatuses.get(i));
            }
        } else {
            for (int i = (page - 1) * PAGE_NUMBER; i < page * PAGE_NUMBER; i++) {
                pageStatuses.add(totalStatuses.get(i));
            }
            page++;
        }
        return pageStatuses;
    }

    private void loadFriendsLine(List<StatusBean> data) {
        statusesAdapter.setStatusesList(data);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusesAdapter.setLoadState(statusesAdapter.LOADING_COMPLETE);
                statusesAdapter.notifyDataSetChanged();
            }
        });
    }
}
