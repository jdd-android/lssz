package com.example.administrator.lssz.eventbus;

import com.example.administrator.lssz.module.user.AuthManager;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;

/**
 * Created by Administrator on 2018/4/12.
 */

public class AuthReturnEvent {
    public  static final int STATE_SUCCESS = 0;
    public static final int STATE_FAIL = 1;
    public static final int STATE_CANCEL = 2;

    private int mState;
    private WbConnectErrorMessage errorMessage;

    public AuthReturnEvent(int state) {
        mState = state;
    }

    public void setErrorMessage(WbConnectErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public WbConnectErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return mState == STATE_SUCCESS;
    }

    public boolean isCancel() {
        return mState == STATE_CANCEL;
    }

    public boolean isFail() {
        return mState == STATE_FAIL;
    }


}
