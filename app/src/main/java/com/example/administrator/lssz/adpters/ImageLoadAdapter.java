package com.example.administrator.lssz.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.PicUrlsBean;
import com.example.administrator.lssz.common.utils.PicUrlUtils;
import com.w4lle.library.NineGridAdapter;
import com.w4lle.library.NineGridlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class ImageLoadAdapter extends NineGridAdapter {
    private Context context;
    private List<PicUrlsBean> picUrls = new ArrayList<>();

    public ImageLoadAdapter(Context context, List<PicUrlsBean> picUrls) {
        super(context, picUrls);
        this.context = context;
        this.picUrls = picUrls;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return (picUrls == null) ? 0 : picUrls.size();
    }

    @Override
    public String getUrl(int position) {
        return getItem(position) == null ? null : picUrls.get(position).getThumbnailPic();
    }

    @Override
    public Object getItem(int position) {
        return (picUrls == null) ? null : picUrls.get(position);
    }

    @Override
    public View getView(int position, View view) {
        ImageView pic = new ImageView(context);
        Glide.with(context)
                .load(PicUrlUtils.getOriginalPic(getUrl(position)))
                .apply(new RequestOptions().fitCenter())
                .into(pic);
        return pic;
    }
}




