package com.example.administrator.lssz.common;

/**
 * @author lc. 2018-03-22 10:16
 * @since 1.0.0
 */

public interface Callback<T, E> {

    /***
     * 成功时回调
     * @param data 成功数据
     */
    void success(T data);

    /**
     * 失败时回调
     *
     * @param error 错误信息
     */
    void failure(E error);
}
