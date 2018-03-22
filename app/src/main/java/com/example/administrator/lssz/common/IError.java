package com.example.administrator.lssz.common;

/**
 * @author lc. 2018-03-22 10:18
 * @since 1.0.0
 */

public interface IError {

    /**
     * 返回错误码
     *
     * @return 错误码
     */
    int code();

    /**
     * 返回错误信息
     *
     * @return 错误信息
     */
    String msg();
}
