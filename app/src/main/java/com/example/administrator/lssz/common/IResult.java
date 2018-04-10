package com.example.administrator.lssz.common;

/**
 * @author lc. 2018-03-16 18:42
 * @since 1.0.0
 *
 * 表示一个处理结果。
 */

public interface IResult<TData, TError> {
    /**
     * 返回处理结果数据
     *
     * @return 成功情况的结果
     */
    TData data();

    /**
     * 返回处理结果数据
     *
     * @return 失败情况的结果
     */
    TError error();

    /**
     * 返回本次处理结果是否是成功的
     *
     * @return true-成功 false-失败
     */
    boolean isOk();
}
