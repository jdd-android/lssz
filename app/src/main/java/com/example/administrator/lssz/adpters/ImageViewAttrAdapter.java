package com.example.administrator.lssz.adpters;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ImageViewAttrAdapter {
    @BindingAdapter("android:src")
    public static void setSrc(ImageView view,Bitmap bitmap){
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view,int resId){
        view.setImageResource(resId);
    }

    @BindingAdapter({"android:src"})
    public static void loadIamge(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);

    }
}
