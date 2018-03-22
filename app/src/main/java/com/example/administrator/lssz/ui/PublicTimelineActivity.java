package com.example.administrator.lssz.ui;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.adpters.StatusesAdapter;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

public class PublicTimelineActivity extends Activity {

    private StatusesAdapter statusesAdapter;
    private RecyclerView statusesRecyclerView;
    private ImageView ivUserIamge;
    private TextView tvUserName;
    private List<StatusBean> statuses = new ArrayList<>();
    private static Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_timeline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ivUserIamge = (ImageView) findViewById(R.id.iv_user_image);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);

        mAccessToken = AccessTokenKeeper.readAccessToken(this);

        statusesAdapter = new StatusesAdapter(this);
        statusesRecyclerView = (RecyclerView) findViewById(R.id.statuses_list);
        statusesRecyclerView.setLayoutManager(new LinearLayoutManager(PublicTimelineActivity.this, LinearLayoutManager.VERTICAL, false));
        statusesRecyclerView.setAdapter(statusesAdapter);

        // 请求 publicLine 数据并显示
        requestPublicLineData();
        requestUserData();

    }

    private void requestPublicLineData() {
        new ApiClient().requestPublicLine(mAccessToken.getToken(), new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                // TODO refresh ui
                statusesAdapter.setStatusesList(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statusesAdapter.notifyDataSetChanged();
                        Log.i("Callback Success", "Callback Success");
                    }
                });
            }

            @Override
            public void failure(IError error) {
                // TODO show error
                Log.i("Callback Error", "Callback Error");
            }
        });
    }

    private void requestUserData() {
        new ApiClient().requestUsersShow(mAccessToken.getToken(), mAccessToken.getUid(), new Callback<UserBean, IError>() {
            @Override
            public void success(final UserBean data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(PublicTimelineActivity.this)
                                .load(data.getProfileImageUrl())
                                .apply(RequestOptions.circleCropTransform())
                                .into(ivUserIamge);
                        tvUserName.setText(data.getName());
                    }
                });
            }

            @Override
            public void failure(IError error) {
                Log.i("Callback Error", "Callback Error");

            }
        });
    }

}
