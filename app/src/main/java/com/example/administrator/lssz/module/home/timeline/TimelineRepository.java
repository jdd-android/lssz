package com.example.administrator.lssz.module.home.timeline;

import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;

import java.util.List;


/**
 * Created by Administrator on 2018/4/10.
 */

// FIXME 对于类、函数访问权限，最好是遵循最小知识原则，能用 private 的就不要用 public

public class TimelineRepository {

    void requestPublicTimelineData(String accessToken,Callback<List<StatusBean>,IError>callback){
       new ApiClient().requestPublicLine(accessToken,callback);
    }

    void requestfFriendTimelieData(String accessToken,int page,Callback<List<StatusBean>,IError>callback){
        new ApiClient().requestFriendsLine(accessToken,page,callback);
    }
}
