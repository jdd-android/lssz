package com.example.administrator.lssz.module.home.timeline;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class FriendsTimelineViewModel extends AndroidViewModel {

    private int mCurrentPage;

    private Oauth2AccessToken accessToken;

    private TimelineRepository mRepository;
    private MutableLiveData<Boolean> mObservableIsRefreshing;
    private MutableLiveData<Boolean> mObservableIsCompleteLoading;
    private MutableLiveData<Boolean> mObservableIsLoadingMore;
    private MutableLiveData<List<StatusBean>> mObservableStatusesList;

    public FriendsTimelineViewModel(Application application) {
        super(application);

        accessToken = AccessTokenKeeper.readAccessToken(application);

        mRepository = new TimelineRepository();
        mObservableIsRefreshing = new MutableLiveData<>();
        mObservableIsCompleteLoading = new MutableLiveData<>();
        mObservableIsLoadingMore = new MutableLiveData<>();
        mObservableStatusesList = new MutableLiveData<>();

        mObservableIsCompleteLoading.setValue(false);
        mObservableIsRefreshing.setValue(false);
        mObservableIsLoadingMore.setValue(false);
    }

    MutableLiveData<Boolean> getObservableIsRefreshing() {
        return mObservableIsRefreshing;
    }

    MutableLiveData<Boolean> getObservableIsCompleteLoading() {
        return mObservableIsCompleteLoading;
    }

    MutableLiveData<Boolean>getObservableIsLoadingMore(){
        return mObservableIsLoadingMore;
    }

    MutableLiveData<List<StatusBean>> getObservableStatusesList() {
        return mObservableStatusesList;
    }

    void refresh() {
        mCurrentPage = 1;
        mObservableIsRefreshing.setValue(true);
        mObservableIsCompleteLoading.setValue(false);
        mRepository.requestfFriendTimelieData(accessToken.getToken(), mCurrentPage, new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                if (!data.isEmpty()) {
                    mObservableStatusesList.postValue(data);
                }
                mCurrentPage++;
                mObservableIsRefreshing.postValue(false);
            }

            @Override
            public void failure(IError error) {
                mObservableIsRefreshing.postValue(false);
            }
        });
    }

    void loadMore() {
        mObservableIsLoadingMore.setValue(true);
        mRepository.requestfFriendTimelieData(accessToken.getToken(), mCurrentPage, new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                if (!data.isEmpty()) {
                    List<StatusBean> statuses = mObservableStatusesList.getValue();
                    if (statuses == null) {
                        statuses = new ArrayList<>();
                    }
                    statuses.addAll(data);
                    mObservableStatusesList.postValue(statuses);
                    mObservableIsLoadingMore.postValue(false);
                    mCurrentPage++;
                } else {
                    mObservableIsCompleteLoading.setValue(true);
                }
            }

            @Override
            public void failure(IError error) {
                mObservableIsLoadingMore.postValue(false);
            }
        });
    }
}
