package com.example.administrator.lssz.common.utils;

import com.example.administrator.lssz.beans.StatusBean;

/**
 * Created by Administrator on 2018/4/2.
 */

public class StatusUtils {
    public static StatusBean getOriginStatus(StatusBean nowStatusBean) {
        do {
            nowStatusBean = nowStatusBean.getRetweetedStatus();
        } while (nowStatusBean.getRetweetedStatus() != null);
        return nowStatusBean;
    }

    public static String getRepostText(StatusBean statusBean) {
        StringBuilder repostText = new StringBuilder();
        repostText.append(statusBean.getText());
        statusBean = statusBean.getRetweetedStatus();
        while (statusBean.getRetweetedStatus() != null) {
            repostText.append("//<font color='blue'>@").append(statusBean.getUser().getName()).append("</font>").append(":").append(statusBean.getText());
            statusBean = statusBean.getRetweetedStatus();
        }
        return repostText.toString();
    }

    public static String getOriginText(StatusBean statusBean) {
        StringBuilder originText = new StringBuilder();
        originText.append("<font color='blue'>@").append(statusBean.getUser().getName()).append("</font>").append(":").append(statusBean.getText());
        return originText.toString();
    }

    public static String setTopicColor(String text) {
        StringBuilder sb = new StringBuilder(text);
        int start = text.indexOf("#");
        int end = text.indexOf("#", start + 1);
        if (start >= 0 && end > 0) {
            sb.insert(end + 1, "</font>");
            sb.insert(start, "<font color='blue'>");
        }
        return sb.toString();
    }
}
