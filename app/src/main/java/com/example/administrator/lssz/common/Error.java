package com.example.administrator.lssz.common;

/**
 * @author lc. 2018-03-22 10:19
 * @since 1.0.0
 */

public class Error implements IError {

    private final int code;
    private final String msg;

    public Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
