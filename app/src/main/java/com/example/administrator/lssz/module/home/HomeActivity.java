package com.example.administrator.lssz.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.module.home.timeline.PublicTimelineFragment;
import com.example.administrator.lssz.module.user.UserInfoKeeper;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class HomeActivity extends FragmentActivity {
    private FragmentManager mFragmentManager;
    private WeiboFragment mWeiboFragment;
    private PublicTimelineFragment mPublicTimelineFragment;
    private static Oauth2AccessToken accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        accessToken = AccessTokenKeeper.readAccessToken(HomeActivity.this);
        loadUserInfo();
        initView();
        setDefaultFragment();
    }

    private void loadUserInfo() {
        new ApiClient().requestUsersShow(accessToken.getToken(), accessToken.getUid(), new Callback<UserBean, IError>() {
            @Override
            public void success(UserBean data) {
                UserInfoKeeper.writeUserInfo(HomeActivity.this, data);
            }

            @Override
            public void failure(IError error) {
            }
        });
    }

    private void initView() {
        findViewById(R.id.home_tv_weibo).setOnClickListener(homeTabListener);
        findViewById(R.id.home_tv_message).setOnClickListener(homeTabListener);
        findViewById(R.id.home_tv_add).setOnClickListener(homeTabListener);
        findViewById(R.id.home_tv_discover).setOnClickListener(homeTabListener);
        findViewById(R.id.home_tv_me).setOnClickListener(homeTabListener);

        mFragmentManager = getSupportFragmentManager();
    }

    private View.OnClickListener homeTabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.home_tv_weibo:
                    setSelectedItem(id);
                    break;
                case R.id.home_tv_message:
                    setSelectedItem(id);
                    break;
                case R.id.home_tv_add:
//                    setSelectedItem(id);
                    startActivity(new Intent(HomeActivity.this, UpdateStatusActivity.class));
                    break;
                case R.id.home_tv_discover:
                    setSelectedItem(id);
                    break;
                case R.id.home_tv_me:
                    setSelectedItem(id);
                    break;
                default:
                    break;
            }
        }
    };

    private void setDefaultFragment() {
        mWeiboFragment = new WeiboFragment();
        mFragmentManager.beginTransaction().add(R.id.home_content, mWeiboFragment).commit();
        setSelectedItem(R.id.home_tv_weibo);

//        mPublicTimelineFragment = new PublicTimelineFragment();
//        mFragmentManager.beginTransaction().add(R.id.home_content, mPublicTimelineFragment).commit();
//        setSelectedItem(R.id.home_tv_weibo);


    }

    private void setSelectedItem(@IdRes int viewId) {
        findViewById(R.id.home_tv_weibo).setSelected(false);
        findViewById(R.id.home_tv_message).setSelected(false);
        findViewById(R.id.home_tv_add).setSelected(false);
        findViewById(R.id.home_tv_discover).setSelected(false);
        findViewById(R.id.home_tv_me).setSelected(false);

        findViewById(viewId).setSelected(true);
    }
}
