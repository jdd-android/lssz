package com.example.administrator.lssz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.lssz.module.user.AuthManager;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class WeiboAuthActivity extends Activity implements WbAuthListener {

    private SsoHandler mSsoHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSsoHandle = new SsoHandler(this);
        mSsoHandle.authorize(this);
    }


    @Override
    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
        finish();
    }

    @Override
    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
        //返回AccessToken，然后用AccessToken获取用户信息
        AuthManager.getInstance(this).updateAccessToken(oauth2AccessToken);
        AuthManager.getInstance(this).refreshUserInfo(oauth2AccessToken.getToken(),oauth2AccessToken.getUid());

        finish();
    }

    @Override
    public void cancel() {
        finish();
    }
}
