package com.example.administrator.lssz.ui;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/22.
 */

public class MyToolbar extends Toolbar {

    private ImageView ivUserImage;
    private TextView tvUserName;

    public MyToolbar (Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    public MyToolbar(Context context){
        this(context,null,0);
    }

    public MyToolbar(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }
}
