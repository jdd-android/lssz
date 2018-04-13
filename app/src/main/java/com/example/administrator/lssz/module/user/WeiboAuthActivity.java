package com.example.administrator.lssz.module.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.lssz.eventbus.AuthReturnEvent;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import org.greenrobot.eventbus.EventBus;

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
        AuthReturnEvent event=new AuthReturnEvent(AuthReturnEvent.STATE_FAIL);
        event.setErrorMessage(wbConnectErrorMessage);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
        //返回AccessToken，然后用AccessToken获取用户信息
        AuthManager.getInstance(this).updateAccessToken(oauth2AccessToken);
        AuthManager.getInstance(this).refreshUserInfo(oauth2AccessToken.getToken(),oauth2AccessToken.getUid());

        EventBus.getDefault().post(new AuthReturnEvent(AuthReturnEvent.STATE_SUCCESS));

        finish();
    }

    @Override
    public void cancel() {
        EventBus.getDefault().post(new AuthReturnEvent(AuthReturnEvent.STATE_CANCEL));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mSsoHandle!=null){
            mSsoHandle.authorizeCallBack(requestCode,resultCode,data);
        }
        finish();
    }
}
