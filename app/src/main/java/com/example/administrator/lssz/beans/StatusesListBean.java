package com.example.administrator.lssz.beans;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class StatusesListBean {
    @JSONField(name = "previous_cursor")
    private long previousCursor;
    @JSONField(name = "next_cursor")
    private long nextCursor;
    @JSONField(name = "total_number")
    private long totalNumber;

    @JSONField(name = "statuses")
    private List<StatusBean> list;

    public long getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(long previousCursor) {
        this.previousCursor = previousCursor;
    }

    public long getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(long nextCursor) {
        this.nextCursor = nextCursor;
    }

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public void setList(List<StatusBean> list) {
        this.list = list;
    }

    public List<StatusBean> getList() {
        return list;
    }
}
