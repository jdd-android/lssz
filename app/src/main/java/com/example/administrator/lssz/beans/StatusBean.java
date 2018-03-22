package com.example.administrator.lssz.beans;

/**
 * Created by Administrator on 2018/3/20.
 */

public class StatusBean {
    private String created_at;
    private String text;
    private String source;
    private String geo;
    private int reposts_count;
    private int commments_count;
    private String name;
    private String profile_image_url;


    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public int getCommments_count() {
        return commments_count;
    }

    public void setCommments_count(int commments_count) {
        this.commments_count = commments_count;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

}
