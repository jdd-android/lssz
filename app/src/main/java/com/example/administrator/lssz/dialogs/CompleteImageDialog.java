package com.example.administrator.lssz.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.PicUrlsBean;
import com.example.administrator.lssz.common.utils.PicUrlUtils;
import com.example.administrator.lssz.views.MatrixImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class CompleteImageDialog extends Dialog implements MatrixImageView.ImageClickListener {

    private Context context;
    private ImageView imageView;
    private String url;

    public CompleteImageDialog(Context context, String url) {
        super(context, R.style.NoFrameDialog);
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_complete_image, null);

        imageView = (ImageView) dialogView.findViewById(R.id.iv_dialog_complete_image);
        Glide.with(context)
                .load(url)
                .into(imageView);
        super.onCreate(savedInstanceState);
        setContentView(dialogView);
    }

    @Override
    public void imageListener(boolean isClick) {
        if (isClick) {
            dismiss();
        }
    }

    //    public void setUrls(List<PicUrlsBean> picUrls) {
//        if (mUrls == null) {
//            mUrls = new ArrayList<>();
//        } else {
//            mUrls.clear();
//        }
//        for (PicUrlsBean picUrl : picUrls) {
//            mUrls.add(PicUrlUtils.getOriginalPic(picUrl.getThumbnailPic()));
//        }
//    }
}
