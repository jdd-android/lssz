package com.example.administrator.lssz.module.home.timeline;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class PublicTimelineViewModel extends AndroidViewModel {

    private TimelineRepository mRepository;

    private Oauth2AccessToken accessToken;
    private MutableLiveData<Boolean> mObservableIsRefreshing;
    private MutableLiveData<Boolean> mObservableIsCompleteLoading;
    private MutableLiveData<List<StatusBean>> mObservableStatusesList;

    public PublicTimelineViewModel(Application application) {
        super(application);

        accessToken = AccessTokenKeeper.readAccessToken(application);

        mRepository = new TimelineRepository();
        mObservableIsRefreshing = new MutableLiveData<>();
        mObservableIsCompleteLoading = new MutableLiveData<>();
        mObservableStatusesList = new MutableLiveData<>();

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

    void refresh() {
        mObservableIsRefreshing.setValue(true);
        mRepository.requestPublicTimelineData(accessToken.getToken(), new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                if (!data.isEmpty()) {
                    mObservableStatusesList.postValue(data);
                } else {
                    mObservableIsCompleteLoading.postValue(true);
                }
                mObservableIsRefreshing.postValue(false);
            }
            @Override
            public void failure(IError error) {
                mObservableIsRefreshing.postValue(false);
            }
        });
    }
}
