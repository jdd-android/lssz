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
    private MutableLiveData<IError> mObservableError;

    public FriendsTimelineViewModel(Application application) {
        super(application);

        accessToken = AccessTokenKeeper.readAccessToken(application);

        mRepository = new TimelineRepository();
        mObservableIsRefreshing = new MutableLiveData<>();
        mObservableIsCompleteLoading = new MutableLiveData<>();
        mObservableIsLoadingMore = new MutableLiveData<>();
        mObservableStatusesList = new MutableLiveData<>();
        mObservableError = new MutableLiveData<>();

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

    MutableLiveData<Boolean> getObservableIsLoadingMore() {
        return mObservableIsLoadingMore;
    }

    MutableLiveData<List<StatusBean>> getObservableStatusesList() {
        return mObservableStatusesList;
    }

    MutableLiveData<IError> getObservableError() {
        return mObservableError;
    }

    void refresh() {
        mCurrentPage = 1;
        mObservableIsRefreshing.setValue(true);
        mObservableIsCompleteLoading.setValue(false);
        mRepository.requestfFriendTimelieData(accessToken.getToken(), mCurrentPage, new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                mObservableIsRefreshing.postValue(false);
                if (data.isEmpty()) {
                    mObservableError.postValue(new Error(-2, "刷新失败，请求返回为空"));
                    return;
                }
                mObservableStatusesList.postValue(data);
                mCurrentPage++;
            }

            @Override
            public void failure(IError error) {
                mObservableIsRefreshing.postValue(false);
                mObservableError.postValue(error);
            }
        });
    }

    void loadMore() {
        mObservableIsLoadingMore.setValue(true);
        mRepository.requestfFriendTimelieData(accessToken.getToken(), mCurrentPage, new Callback<List<StatusBean>, IError>() {
            @Override
            public void success(List<StatusBean> data) {
                mObservableIsLoadingMore.postValue(false);
                if (data.isEmpty()) {
                    mObservableError.postValue(new Error(-2, "加载失败，请求返回为空"));
                    mObservableIsCompleteLoading.postValue(true);
                    return;
                }
                List<StatusBean> statuses = mObservableStatusesList.getValue();
                if (statuses == null) {
                    statuses = new ArrayList<>();
                }
                statuses.addAll(data);
                mObservableStatusesList.postValue(statuses);
                mCurrentPage++;
            }

            @Override
            public void failure(IError error) {
                mObservableError.postValue(error);
                mObservableIsLoadingMore.postValue(false);
            }
        });
    }
}
