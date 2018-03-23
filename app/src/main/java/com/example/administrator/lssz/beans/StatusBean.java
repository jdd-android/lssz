package com.example.administrator.lssz.beans;

/**
 * Created by Administrator on 2018/3/20.
 */

public class StatusBean {
    private int repostsCount;
    private int commmentsCount;

    private String createdAt;
    private String text;
    private String source;
    private String geo;

    private UserBean user;

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
