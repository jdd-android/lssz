package com.example.administrator.lssz.common.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.lssz.beans.StatusBean;

import java.util.ArrayList;

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
            repostText.append("//@").append(statusBean.getUser().getName()).append(":").append(statusBean.getText());
            statusBean = statusBean.getRetweetedStatus();
        }
        return repostText.toString();
    }

    public static String getOriginText(StatusBean statusBean) {
        StringBuilder originText = new StringBuilder();
        originText.append("@").append(statusBean.getUser().getName()).append(":").append(statusBean.getText());
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

    public static SpannableString getClickableSpan(String str) {
        SpannableString spannableString = new SpannableString(str);
        //话题标蓝
        ArrayList<Integer> topicIndexs = getTopicIndex(str);
        for (int i = 0; i < topicIndexs.size(); i++) {
            int start = topicIndexs.get(i);
            int end = topicIndexs.get(++i) + 1;
            spannableString.setSpan(new TextClick(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //艾特标蓝
        ArrayList<Integer> atIndexs = getAtIndex(str);
        for (int i = 0; i < atIndexs.size(); i++) {
            spannableString.setSpan(new TextClick(), atIndexs.get(i), atIndexs.get(++i), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    private static ArrayList<Integer> getTopicIndex(String str) {
        ArrayList<Integer> topicIndexs = new ArrayList<>();
        int index = -1;
        do {
            index = str.indexOf("#", index + 1);
            if (index >= 0) {
                topicIndexs.add(index);
            }
        } while (index >= 0);
        if (topicIndexs.size() % 2 == 1) {
            topicIndexs.remove(topicIndexs.size() - 1);
        }
        return topicIndexs;
    }

    private static ArrayList<Integer> getAtIndex(String str) {
        ArrayList<Integer> atIndexs = new ArrayList<>();
        String strNew = str.replaceAll("[\\s();,.:\'\"!]", "*");
        int start = -1;
        int end = -1;
        do {
            start = strNew.indexOf("@", end + 1);
            end = strNew.indexOf("*", start + 1);
            if (start >= 0 && end > 0 && end - start <= 15) {
                atIndexs.add(start);
                atIndexs.add(end);
            }
        } while (start >= 0 && end > 0);
        return atIndexs;
    }

    private static class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            Toast.makeText(widget.getContext(), "蓝色", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }
    }
}
