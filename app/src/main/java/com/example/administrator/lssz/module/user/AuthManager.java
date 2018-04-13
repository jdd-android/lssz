package com.example.administrator.lssz.module.user;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.eventbus.UserInfoRefreshEvent;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/4/11.
 */

public class AuthManager {
    private static volatile AuthManager sInstance;

    private Context mContext;
    private MutableLiveData<UserBean> mObservableUser;
    private MutableLiveData<IError> mObservableUpdateUserError;


    private AuthManager(Context context) {
        mContext = context.getApplicationContext();
        mObservableUpdateUserError = new MutableLiveData<>();
        mObservableUser = new MutableLiveData<>();
        UserBean mUser = UserInfoKeeper.readUserInfo(context);
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

    public UserBean getCurrentUser() {
        return UserInfoKeeper.readUserInfo(mContext);
    }

    public boolean isAuthSuccess() {
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mContext);
        if (accessToken.isSessionValid()) {
            return true;
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

    public void refreshUserInfo(final String accessToken, String uid) {
        new ApiClient().requestUsersShow(accessToken, uid, new Callback<UserBean, IError>() {
            @Override
            public void success(UserBean data) {
                UserInfoKeeper.clear(mContext);
                UserInfoKeeper.writeUserInfo(mContext, data);
                mObservableUser.postValue(data);
                EventBus.getDefault().post(new UserInfoRefreshEvent(true));
            }

            @Override
            public void failure(IError error) {
                mObservableUpdateUserError.postValue(error);
            }
        });
    }
}
