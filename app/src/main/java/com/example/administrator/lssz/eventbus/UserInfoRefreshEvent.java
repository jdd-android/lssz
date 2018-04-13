package com.example.administrator.lssz.eventbus;

/**
 * Created by Administrator on 2018/4/13.
 */

public class UserInfoRefreshEvent {
    private boolean refreshSuccess;

    public UserInfoRefreshEvent(boolean isSuccess) {
        refreshSuccess = isSuccess;
    }

    public boolean isRefreshSuccess() {
        return refreshSuccess;
    }
}
