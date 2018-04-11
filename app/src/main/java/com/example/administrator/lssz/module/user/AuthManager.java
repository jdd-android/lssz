package com.example.administrator.lssz.module.user;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.administrator.lssz.WeiboAuthActivity;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.common.utils.DateUtils;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/11.
 */

public class AuthManager {
    private static volatile AuthManager sInstance;

    private Context mContext;
    private UserBean mUser;
    private MutableLiveData<UserBean> mObservableUser;


    private AuthManager(Context context) {
        mContext = context.getApplicationContext();
        mUser = UserInfoKeeper.readUserInfo(context);
        mObservableUser = new MutableLiveData<>();
        mObservableUser.setValue(mUser);
    }

    public static AuthManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AuthManager.class) {
                if (sInstance == null) {
                    sInstance = new AuthManager(context);
                }
            }
        }
        return sInstance;
    }

    public boolean isAuthSuccess() {
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mContext);
        if (!TextUtils.isEmpty(accessToken.getToken())) {
            return true;
//            if (DateUtils.isAfterCurrentTime(accessToken.getExpiresTime())) {
//                return true;
//            }
        }
        return false;
    }

    public void startAuth(Activity activity) {
        activity.startActivity(new Intent(activity, WeiboAuthActivity.class));
    }

    public void updateAccessToken(Oauth2AccessToken accessToken) {
        AccessTokenKeeper.clear(mContext);
        AccessTokenKeeper.writeAccessToken(mContext, accessToken);
    }

    public void refreshUserInfo(String accessToken, String uid) {
        new ApiClient().requestUsersShow(accessToken, uid, new Callback<UserBean, IError>() {
            @Override
            public void success(UserBean data) {
                mObservableUser.postValue(data);
            }

            @Override
            public void failure(IError error) {

            }
        });
    }
}
