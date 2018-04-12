package com.example.administrator.lssz.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.eventbus.AuthReturnEvent;
import com.example.administrator.lssz.module.home.timeline.PublicTimelineFragment;
import com.example.administrator.lssz.module.user.AuthManager;
import com.example.administrator.lssz.module.user.UserInfoKeeper;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeActivity extends FragmentActivity {
    private FragmentManager mFragmentManager;
    private WeiboFragment mWeiboFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EventBus.getDefault().register(this);
        mFragmentManager = getSupportFragmentManager();

        if (!AuthManager.getInstance(this).isAuthSuccess()) {
            AuthManager.getInstance(this).startAuth(this);
        }

        mWeiboFragment = new WeiboFragment();
        mFragmentManager.beginTransaction().add(R.id.home_content, mWeiboFragment).commit();
    }

    public void addNewStatus(View view){
        startActivity(new Intent(HomeActivity.this,UpdateStatusActivity.class));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAuthCallbackEvent(AuthReturnEvent event){
        if(event.isSuccess()){
            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        }else if(event.isFail()){
            Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show();
        }else if(event.isCancel()){
            Toast.makeText(this,"登录取消",Toast.LENGTH_SHORT).show();
        }
    }

}
