package com.example.administrator.lssz.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.PicUrlsBean;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class CompleteImageDialog extends Dialog {

    private Context context;
    private ViewPager completeImageViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;

    private List<String> mUrls;
    private List<File> mDownloadFiles;

    public CompleteImageDialog(Context context) {
        super(context);
        this.context = context;

        View completeIamgeDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_complete_image, null);
        completeImageViewPager = completeIamgeDialogView.findViewById(R.id.dialog_complete_iamge_pager);


    }

    public void setUrls(List<PicUrlsBean> picUrls) {
        if (mUrls == null) {

        }

    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public int getCount() {
            return 0;
        }

    }
}
