package com.example.administrator.lssz;

import android.app.Application;

import com.example.administrator.lssz.api.ApiClient;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

/**
 * Created by Administrator on 2018/3/23.
 */

public class LsszApp extends Application {
    private static LsszApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        WbSdk.install(getApplicationContext(), new AuthInfo(getApplicationContext(), Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));


//    private void requestUserData() {
//        new ApiClient().requestUsersShow(mAccessToken.getToken(), mAccessToken.getUid(), new Callback<UserBean, IError>() {
//            @Override
//            public void success(UserBean data) {
//                loadToolbar(data);
//            }
//
//            @Override
//            public void failure(IError error) {
//                Log.i("Callback Error", "Callback Error");
//            }
//        });
//    }
//
//    private void loadToolbar(final UserBean data) {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Glide.with(getActivity())
//                        .load(data.getAvatarLarge())
//                        .apply(RequestOptions.circleCropTransform())
//                        .into(ivUserIamge);
//                tvUserName.setText(data.getName());
//            }
//        });
//    }


    }

    public static LsszApp getInstance() {
        return instance;
    }
}
