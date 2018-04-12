package com.example.administrator.lssz.module.comment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.administrator.lssz.beans.CommentBean;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class StatusCommentViewModel extends AndroidViewModel {

    private Oauth2AccessToken accessToken;
    private CommentRepository mRepository;
    private MutableLiveData<List<CommentBean>> mObservableCommentList;
    private MutableLiveData<Boolean> mObservableNoComments;
    private MutableLiveData<StatusBean> mObservableStatus;

   public StatusCommentViewModel(Application application) {
        super(application);

        accessToken = AccessTokenKeeper.readAccessToken(application);
        mRepository = new CommentRepository();
        mObservableCommentList = new MutableLiveData<>();
        mObservableNoComments = new MutableLiveData<>();
        mObservableStatus = new MutableLiveData<>();

        mObservableNoComments.setValue(false);
    }

    MutableLiveData<List<CommentBean>> getObservableCommentList() {
        return mObservableCommentList;
    }

    MutableLiveData<StatusBean> getObservableStatus() {
        return mObservableStatus;
    }

    MutableLiveData<Boolean> getObservableNoComments() {
        return mObservableNoComments;
    }

    void loadComment() {
       // FIXME mObservableStatus.getValue().getId() 有空值危险，别忽略这些警告
        mRepository.requestCommentData(accessToken.getToken(), mObservableStatus.getValue().getId(), new Callback<List<CommentBean>, IError>() {
            @Override
            public void success(List<CommentBean> data) {
                if (!data.isEmpty()) {
                    mObservableCommentList.postValue(data);
                } else {
                    mObservableNoComments.postValue(true);
                }
            }

            @Override
            public void failure(IError error) {

            }
        });
    }

    void setObservableStatus(StatusBean status) {
        mObservableStatus.setValue(status);
    }

}
