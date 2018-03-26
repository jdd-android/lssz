package com.example.administrator.lssz.beans;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/3/20.
 */

public class UserBean {
    @JSONField(name = "followers_count")
    private int followersCount;
    @JSONField(name = "friends_count")
    private int friendsCount;
    @JSONField(name = "statuses_count")
    private int statusesCount;

    @JSONField(name = "name")
    private String name;
    @JSONField(name = "avatar_large")
    private String avatarLarge;
    @JSONField(name = "profile_image_url")
    private String profileImageUrl;
    @JSONField(name = "location")
    private String location;
    @JSONField(name = "description")
    private String description;
    @JSONField(name = "created_at")
    private String createdAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getAvatarLarge() {
        return avatarLarge;
    }

    public void setAvatarLarge(String avatarLarge) {
        this.avatarLarge = avatarLarge;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(int statusesCount) {
        this.statusesCount = statusesCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
