package com.example.administrator.lssz.ui;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;

/**
 * Created by Administrator on 2018/3/22.
 */

public class MyToolbar extends Toolbar {

    private ImageView ivUserImage;
    private TextView tvUserName;

    public MyToolbar (Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    public MyToolbar(Context context){
        this(context,null,0);
    }

    public MyToolbar(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }

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
