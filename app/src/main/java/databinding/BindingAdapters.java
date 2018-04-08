package databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.adpters.ImageLoadAdapter;
import com.example.administrator.lssz.beans.PicUrlsBean;
import com.w4lle.library.NineGridlayout;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class BindingAdapters {
    @BindingAdapter({"imageUrl"})
    public static void loadRoundImage(ImageView imageView,String imageUrl){
        Glide.with(imageView)
                .load(imageUrl)
                .apply(new RequestOptions().circleCrop())
                .into(imageView);
    }

    @BindingAdapter({"picUrls"})
    public static void loadNineGrid(NineGridlayout statusPics, List<PicUrlsBean> picUrls){
        statusPics.setAdapter(new ImageLoadAdapter(statusPics.getContext(),picUrls));
    }
}
