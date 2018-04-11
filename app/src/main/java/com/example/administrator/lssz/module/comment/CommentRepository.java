package com.example.administrator.lssz.module.comment;

import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.CommentBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class CommentRepository {
    void requestCommentData(String accessToken, String statusId, Callback<List<CommentBean>,IError>callback){
        new ApiClient().requestStatusComment(accessToken,statusId,callback);
    }
}
