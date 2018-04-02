package com.example.administrator.lssz.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/2.
 */

public class Indicator extends LinearLayout {

    public Indicator(Context context) {
        this(context, null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextView generateText(String text){
        TextView textView=new TextView(getContext());
        return textView;
    }
}
