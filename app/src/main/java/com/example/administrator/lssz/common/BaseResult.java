package com.example.administrator.lssz.common;

/**
 * @author lc. 2018-03-16 18:45
 * @since 1.0.0
 */

public abstract class BaseResult<TData, TError> implements IResult<TData, TError> {
    private TData data;
    private TError error;

    public void setData(TData data) {
        this.data = data;
    }

    @Override
    public TData data() {
        return data;
    }

    public void setError(TError error) {
        this.error = error;
    }

    @Override
    public TError error() {
        return error;
    }

    @Override
    public boolean isOk() {
        return error() == null;
    }
}
