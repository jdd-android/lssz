package com.example.administrator.lssz.module.home.timeline;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.Error;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class PublicTimelineViewModel extends AndroidViewModel {

    private TimelineRepository mRepository;

    private MutableLiveData<Boolean> mObservableIsRefreshing;
    private MutableLiveData<Boolean> mObservableIsCompleteLoading;
    private MutableLiveData<List<StatusBean>> mObservableStatusesList;
    private MutableLiveData<IError> mObservableError;

    public PublicTimelineViewModel(Application application) {
        super(application);

        mRepository = new TimelineRepository();
        mObservableIsRefreshing = new MutableLiveData<>();
        mObservableIsCompleteLoading = new MutableLiveData<>();
        mObservableStatusesList = new MutableLiveData<>();
        mObservableError=new MutableLiveData<>();

        mObservableIsCompleteLoading.setValue(false);
        mObservableIsRefreshing.setValue(false);
    }

    MutableLiveData<Boolean> getObservableIsRefreshing() {
        return mObservableIsRefreshing;
    }

    MutableLiveData<List<StatusBean>> getObservableStatusesList() {
        return mObservableStatusesList;
    }

    MutableLiveData<Boolean> getObservableIsCompleteLoading() {
        return mObservableIsCompleteLoading;
    }

    MutableLiveData<IError> getObservableError(){
        return mObservableError;
    }

    void refresh() {
        Oauth2AccessToken accessToken=AccessTokenKeeper.readAccessToken(this.getApplication());
        mObservableIsRefreshing.setValue(true);
        mRepository.requestPublicTimelineData(accessToken.getToken(), new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                mObservableIsRefreshing.postValue(false);
                mObservableIsCompleteLoading.postValue(true);
                if (data.isEmpty()) {
                    mObservableError.postValue(new Error(-2,"刷新失败，请求返回为空"));
                }
                mObservableStatusesList.postValue(data);
            }
            @Override
            public void failure(IError error) {
                mObservableIsRefreshing.postValue(false);
                mObservableError.postValue(error);
            }
        });
    }
}
