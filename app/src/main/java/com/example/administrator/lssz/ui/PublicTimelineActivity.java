package com.example.administrator.lssz.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.adpters.StatusesAdapter;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.listener.EndlessRecyclerOnScrollListener;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

public class PublicTimelineActivity extends Activity {
    private final static String STATUS_ID = "id";
    private final static String STATUS = "status";
    private int page = 1;

    private StatusesAdapter statusesAdapter;
    private SwipeRefreshLayout mRefrshLayout;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;
    private RecyclerView statusesRecyclerView;
    private ImageView ivUserIamge;
    private TextView tvUserName;
    private static Oauth2AccessToken mAccessToken;
    private List<StatusBean> statuses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_timeline);

        //读取令牌
        mAccessToken = AccessTokenKeeper.readAccessToken(this);

        //初始化顶端栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivUserIamge = (ImageView) findViewById(R.id.iv_user_image);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);

        statusesAdapter = new StatusesAdapter(this);
        statusesAdapter.setOnRecyclerViewListener(new StatusesAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(View view, StatusBean statusBean) {
                //跳转评论页面
                String statusBeanString = JSON.toJSONString(statusBean);
                Intent intent = new Intent(PublicTimelineActivity.this, StatusCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(STATUS_ID, statusBean.getId());
                bundle.putString(STATUS, statusBeanString);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //设置RecyclerView
        statusesRecyclerView = (RecyclerView) findViewById(R.id.statuses_list);
        statusesRecyclerView.setLayoutManager(new LinearLayoutManager(PublicTimelineActivity.this, LinearLayoutManager.VERTICAL, false));
        statusesRecyclerView.setAdapter(statusesAdapter);

        //下拉刷新
        mRefrshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestPublicLineData();
                mRefrshLayout.setRefreshing(false);
            }
        };
        mRefrshLayout.setOnRefreshListener(mRefreshListener);
        //上滑加载
//        statusesRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                statusesAdapter.setLoadState(statusesAdapter.LOADING);
//                requestPublicLineData();
//            }
//        });


        // 请求数据并显示
        requestPublicLineData();
        requestUserData();
    }

    private void requestPublicLineData() {
        new ApiClient().requestPublicLine(mAccessToken.getToken(), new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                loadPublicLine(data);
                Log.i("Callback Success", "Callback Success");
            }

            @Override
            public void failure(IError error) {
                Log.i("Callback Error", "Callback Error");
            }
        });
    }

    private void loadPublicLine(List<StatusBean> data) {
        statusesAdapter.setStatusesList(data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusesAdapter.notifyDataSetChanged();
            }
        });
    }

    private void requestUserData() {
        new ApiClient().requestUsersShow(mAccessToken.getToken(), mAccessToken.getUid(), new Callback<UserBean, IError>() {
            @Override
            public void success(UserBean data) {
                loadToolbar(data);
            }

            @Override
            public void failure(IError error) {
                Log.i("Callback Error", "Callback Error");
            }
        });
    }

    private void loadToolbar(final UserBean data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(PublicTimelineActivity.this)
                        .load(data.getAvatarLarge())
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivUserIamge);
                tvUserName.setText(data.getName());
            }
        });
    }

}
