package com.example.administrator.lssz.ui;

import android.app.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.adpters.StatusesAdapter;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PublicTimelineActivity extends Activity {

    private StatusesAdapter statusesAdapter;
    private RecyclerView statusesRecyclerView;
    private List<StatusBean> statuses = new ArrayList<>();
    private static Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_timeline);

        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        statusesAdapter = new StatusesAdapter();

        statusesAdapter.setContext(PublicTimelineActivity.this);
        statusesRecyclerView = (RecyclerView) findViewById(R.id.statuses_list);
        statusesRecyclerView.setAdapter(statusesAdapter);
        statusesRecyclerView.setLayoutManager(new LinearLayoutManager(PublicTimelineActivity.this, LinearLayoutManager.VERTICAL, false));

        // 请求 publicLine 数据并显示
        requestPublicLineData();

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

    private void requestUserData(){
        new ApiClient().requestUsersShow(mAccessToken.getToken(), mAccessToken.getUid(), new Callback<UserBean, IError>() {
            @Override
            public void success(UserBean data) {

            }

            @Override
            public void failure(IError error) {
                Log.i("Callback Error", "Callback Error");

            }
        });
    }

}
