package com.example.administrator.lssz.ui;

import android.app.Activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;


import com.example.administrator.lssz.R;

public class HomeActivity extends FragmentActivity {
    private FragmentManager mFragmentManager;
    private WeiboFragment mWeiboFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        setDefaultFragment();
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
