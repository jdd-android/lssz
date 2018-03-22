package com.example.administrator.lssz.ui;

import android.app.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.adpters.StatusesAdapter;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        //FIXME 尽量不要手动创建线程，创建线程-销毁线程耗资源，有必要的话可以使用线程池
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getPublic(mAccessToken.getToken());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        // 请求 publicLine 数据并显示
        requestPublicLineData();
    }

    private void requestPublicLineData() {
        new ApiClient().requestPublicLine("accessToken", new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                // TODO refresh ui
                
            }

            @Override
            public void failure(IError error) {
                // TODO show error
            }
        });
    }

    private void getPublic(final String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url("https://api.weibo.com/2/statuses/public_timeline.json?access_token=" + token).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        Log.i("PublicTimeline", data);
        loadStatus(data);

    }

    private void loadStatus(String jsonData) {
        try {
            if (!TextUtils.isEmpty(jsonData)) {
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray jsonArray = jsonObject.getJSONArray("statuses");
                Log.i("STATUES", jsonArray.toString());
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                        StatusBean status = new StatusBean();
                        status.setName(jsonObject1.optJSONObject("user").optString("name"));
                        status.setProfile_image_url(jsonObject1.optJSONObject("user").optString("profile_image_url"));
                        status.setCreated_at(jsonObject1.optString("created_at"));
                        status.setText(jsonObject1.optString("text"));
                        status.setCommments_count(jsonObject1.optInt("comments_count"));
                        status.setSource(jsonObject1.optString("source"));
                        status.setReposts_count(jsonObject1.optInt("reposts_count"));
                        status.setGeo(jsonObject1.optString("geo"));
                        statuses.add(status);
                    }
                }

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 这里显示列表数据的顺序不是很理解啊
                    // 不是应该先给 RecyclerView 设置 adapter,layoutManager
                    // 数据获取到后，交给 adapter 去布局显示么
                    // 每次数据来了后，给 adapter 设置数据，让后调用  statusesRecyclerView.setAdapter 来显示数据，很怪啊
                    statusesAdapter.setStatusesList(statuses);
                    statusesAdapter.setContext(PublicTimelineActivity.this);
                    statusesRecyclerView = (RecyclerView) findViewById(R.id.statuses_list);
                    statusesRecyclerView.setAdapter(statusesAdapter);
                    statusesRecyclerView.setLayoutManager(new LinearLayoutManager(PublicTimelineActivity.this, LinearLayoutManager.VERTICAL, false));

                    // 正常不是应该
                    // onCreate
                    // statusesRecyclerView = (RecyclerView) findViewById(R.id.statuses_list);
                    // statusesRecyclerView.setAdapter(statusesAdapter);
                    // statusesRecyclerView.setLayoutManager(new LinearLayoutManager(PublicTimelineActivity.this, LinearLayoutManager.VERTICAL, false));

                    // 数据返回 callback.success(data)
                    // statusesAdapter.setData(data) 设置数据
                    // statusesAdapter.notifyDataSetChanged() 更新 UI

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
