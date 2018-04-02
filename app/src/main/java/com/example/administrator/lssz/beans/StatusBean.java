package com.example.administrator.lssz.beans;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public class StatusBean {
    @JSONField(name = "reposts_count")
    private int repostsCount;
    @JSONField(name = "comments_count")
    private int commmentsCount;
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "created_at")
    private String createdAt;
    @JSONField(name = "text")
    private String text;
    @JSONField(name = "source")
    private String source;
    @JSONField(name = "geo")
    private String geo;
    @JSONField(name = "user")
    private UserBean user;
    @JSONField(name = "pic_urls")
    private List<PicUrlsBean> picUrlsList;
    @JSONField(name = "retweeted_status")
    private StatusBean retweetedStatus;

    public StatusBean getRetweetedStatus() {
        return retweetedStatus;
    }

    public void setRetweetedStatus(StatusBean retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    public List<PicUrlsBean> getPicUrlsList() {
        return picUrlsList;
    }

    public void setPicUrlsList(List<PicUrlsBean> picUrlsList) {
        this.picUrlsList = picUrlsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public int getCommmentsCount() {
        return commmentsCount;
    }

    public void setCommmentsCount(int commmentsCount) {
        this.commmentsCount = commmentsCount;
    }

    public int getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        this.repostsCount = repostsCount;
    }

}
