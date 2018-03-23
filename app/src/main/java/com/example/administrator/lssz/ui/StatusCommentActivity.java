package com.example.administrator.lssz.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.adpters.CommentsAdapter;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.CommentBean;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.List;

public class StatusCommentActivity extends Activity {

    private final static String STATUS_ID = "id";
    private static Oauth2AccessToken mAccessToken;
    private ImageView ivStatusUserImage;
    private TextView tvStatusUserName;
    private TextView tvStatusTime;
    private TextView tvStatusText;
    private RecyclerView commentRecyclerView;
    private CommentsAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_comment);
        String statusId = this.getIntent().getExtras().getString(STATUS_ID);

        //读取令牌
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        //初始化微博Status
        ivStatusUserImage = (ImageView) findViewById(R.id.iv_status_image);
        tvStatusUserName = (TextView) findViewById(R.id.tv_status_name);
        tvStatusTime = (TextView) findViewById(R.id.tv_status_time);
        tvStatusText = (TextView) findViewById(R.id.tv_status_text);

        //初始化评论RecyclerView
        commentRecyclerView = (RecyclerView) findViewById(R.id.comments_list);
        commentAdapter = new CommentsAdapter(StatusCommentActivity.this);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(StatusCommentActivity.this, LinearLayoutManager.VERTICAL, false));
        commentRecyclerView.setAdapter(commentAdapter);
        //请求微博数据
        requestSingleStatusData(statusId);
        requestStatusComment(statusId);
    }

    private void requestSingleStatusData(String statusId) {
        new ApiClient().requestSingleStatus(mAccessToken.getToken(), statusId, new Callback<StatusBean, IError>() {
            @Override
            public void success(StatusBean data) {
                loadStatus(data);
                Log.i("Callback Success", "SingleStatusCallback Success");
            }

            @Override
            public void failure(IError error) {
                Log.i("Callback Error", "SingleStatusCallback Error");
            }
        });
    }

    private void loadStatus(final StatusBean statusBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(StatusCommentActivity.this)
                        .load(statusBean.getUser().getAvatarLarge())
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivStatusUserImage);
                tvStatusUserName.setText(statusBean.getUser().getName());
                tvStatusText.setText(statusBean.getText());
                tvStatusTime.setText(statusBean.getCreatedAt());
            }
        });

    }

    private void requestStatusComment(String statusId) {
        new ApiClient().requestStatusComment(mAccessToken.getToken(), statusId, new Callback<List<CommentBean>, IError>() {
            @Override
            public void success(List<CommentBean> data) {
                Log.i("Callback Success", "StatusCommentCallback Success");
                commentAdapter.setComments(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commentAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void failure(IError error) {
                Log.i("Callback Error", "StatusCommentCallback Error");

            }
        });
    }
}
