package com.example.administrator.lssz.common.utils;

/**
 * Created by Administrator on 2018/3/28.
 */

public class PicUrlUtils {
    public static String getBmiddlePic(String thumbnailPic) {
        String bmiddlePic = thumbnailPic.replaceFirst("thumbnail", "bmiddle");
        return bmiddlePic;
    }

    public static String getOriginalPic(String thumbnailPic) {
        String largePic = thumbnailPic.replaceFirst("thumbnail", "large");
        return largePic;
    }
}
