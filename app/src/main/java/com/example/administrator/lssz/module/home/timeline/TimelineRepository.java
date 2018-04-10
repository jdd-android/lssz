package com.example.administrator.lssz.module.home.timeline;

import android.support.v4.os.IResultReceiver;
import android.telecom.Call;

import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.beans.StatusesListBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.common.SimpleCallback;

import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by Administrator on 2018/4/10.
 */

public class TimelineRepository {

    void requestPublicTimelineData(String accessToken,Callback<List<StatusBean>,IError>callback){
       new ApiClient().requestPublicLine(accessToken,callback);
    }

    void requestfFriendTimelieData(String accessToken,int page,Callback<List<StatusBean>,IError>callback){
        new ApiClient().requestFriendsLine(accessToken,page,callback);
    }
}
