package com.example.administrator.lssz.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/26.
 */

public class DateUtils {

    private static final SimpleDateFormat POST_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

    public static String readableDate(String dateOrigin) {
        try {
            Date date = POST_DATE_FORMAT.parse(dateOrigin);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateOrigin;
        }
    }

    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(currentTime);
    }
}
