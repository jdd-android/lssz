package com.example.administrator.lssz.beans;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CommentBean {
    @JSONField(name = "create_at")
    private String createdAt;
    @JSONField(name = "mid")
    private String mid;
    @JSONField(name = "text")
    private String text;
    @JSONField(name = "source")
    private String source;
    @JSONField(name = "user")
    private UserBean userBean;
    @JSONField(name = "status")
    private StatusBean statusBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public StatusBean getStatusBean() {
        return statusBean;
    }

    public void setStatusBean(StatusBean statusBean) {
        this.statusBean = statusBean;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMid() {
        return mid;
    }

}
