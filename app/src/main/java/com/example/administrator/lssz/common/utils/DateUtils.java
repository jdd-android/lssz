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
    private static final String MINITE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DAY_DATE_FORMAT = "yyyy-MM-dd";

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
        SimpleDateFormat format = new SimpleDateFormat();
        return format.format(currentTime);
    }

//    public String getTimeDiffer(String fromDate, String NowDate) {
//        try {
//            long from = DAY_DATE_FORMAT.parse(fromDate).getTime();
//            long now = DAY_DATE_FORMAT.parse(NowDate).getTime();
//            int day = (int) ((now - from) / (1000 * 60 * 60 * 24));
//            if (day < 1) {
//                return String.valueOf(day);
//            } else {
//                return DAY_DATE_FORMAT.format(DAY_DATE_FORMAT.parse(fromDate));
//            }
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return fromDate;
//        }
//    }
}
