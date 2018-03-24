package com.example.administrator.lssz.beans;

import com.alibaba.fastjson.annotation.JSONField;

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
    @JSONField(name = "create_at")
    private String createdAt;
    @JSONField(name = "text")
    private String text;
    @JSONField(name = "source")
    private String source;
    @JSONField(name = "geo")
    private String geo;
    @JSONField(name = "user")
    private UserBean user;

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
