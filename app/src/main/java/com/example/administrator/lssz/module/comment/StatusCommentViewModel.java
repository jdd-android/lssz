package com.example.administrator.lssz.module.comment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.example.administrator.lssz.beans.CommentBean;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.Error;
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
    private MutableLiveData<StatusBean> mObservableStatus;
    private MutableLiveData<IError> mObservableError;

   public StatusCommentViewModel(Application application) {
        super(application);

        accessToken = AccessTokenKeeper.readAccessToken(application);
        mRepository = new CommentRepository();
        mObservableCommentList = new MutableLiveData<>();
        mObservableStatus = new MutableLiveData<>();
        mObservableError=new MutableLiveData<>();

    }

    MutableLiveData<List<CommentBean>> getObservableCommentList() {
        return mObservableCommentList;
    }

    MutableLiveData<StatusBean> getObservableStatus() {
        return mObservableStatus;
    }


    MutableLiveData<IError> getObservableError(){
       return mObservableError;
    }

    void loadComment() {
        StatusBean value = mObservableStatus.getValue();
        if(value!=null){
            mRepository.requestCommentData(accessToken.getToken(), value.getId(), new Callback<List<CommentBean>, IError>() {
                @Override
                public void success(List<CommentBean> data) {
                    if (!data.isEmpty()) {
                        mObservableCommentList.postValue(data);
                    } else {
                        mObservableError.setValue(new Error(-1,"没有评论或者作者设置了隐私"));
                    }
                }

                @Override
                public void failure(IError error) {
                    mObservableError.postValue(error);

                }
            });
        }else{
            mObservableError.setValue(new Error(-1,"获取微博信息失败"));
        }
    }

    void setObservableStatus(StatusBean status) {
        mObservableStatus.setValue(status);
    }

}
