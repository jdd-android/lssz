package com.example.administrator.lssz;

import android.app.Application;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

/**
 * Created by Administrator on 2018/3/23.
 */

public class LsszApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WbSdk.install(getApplicationContext(), new AuthInfo(getApplicationContext(), Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
    }
}
