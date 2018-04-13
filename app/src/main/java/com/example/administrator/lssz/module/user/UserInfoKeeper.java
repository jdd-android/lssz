package com.example.administrator.lssz.module.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.example.administrator.lssz.beans.UserBean;

/**
 * Created by Administrator on 2018/4/4.
 */

public class UserInfoKeeper {
    private static final String PREFERENCE_NAME = "user_info";
    private static final String FOLLOWERS_COUNT = "followers_count";
    private static final String FRIENDS_COUNT = "friends_count";
    private static final String STATUSES_COUNT = "statuses_count";
    private static final String NAME = "name";
    private static final String AVATAR_LARGE = "avatar_large";
    private static final String LOCATION = "location";
    private static final String DESCRIPTION = "description";
    private static final String CREATED_AT = "created_at";


    public UserInfoKeeper() {
    }

    public static void writeUserInfo(Context context, UserBean user) {
        if (context != null && user != null) {
            SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(FOLLOWERS_COUNT, user.getFollowersCount());
            editor.putInt(FRIENDS_COUNT, user.getFriendsCount());
            editor.putInt(STATUSES_COUNT, user.getStatusesCount());
            editor.putString(NAME, user.getName());
            editor.putString(AVATAR_LARGE, user.getAvatarLarge());
            editor.putString(LOCATION, user.getLocation());
            editor.putString(DESCRIPTION, user.getDescription());
            editor.putString(CREATED_AT, user.getCreatedAt());
            editor.apply();
        }
    }

    public static UserBean readUserInfo(Context context) {
        if (context == null){
            return null;
        }else{
            UserBean user=new UserBean();
            SharedPreferences pref=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
            user.setFollowersCount(pref.getInt(FOLLOWERS_COUNT,0));
            user.setFriendsCount(pref.getInt(FRIENDS_COUNT,0));
            user.setStatusesCount(pref.getInt(STATUSES_COUNT,0));
            user.setName(pref.getString(NAME,"未登录"));
            user.setAvatarLarge(pref.getString(AVATAR_LARGE,""));
            user.setLocation(pref.getString(LOCATION,""));
            user.setDescription(pref.getString(DESCRIPTION,""));
            user.setCreatedAt(pref.getString(CREATED_AT,""));
            return user;
        }
    }

    public static void clear(Context context){
        if(context!=null){
            SharedPreferences pref=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=pref.edit();
            editor.clear();
            editor.apply();
        }
    }
}
