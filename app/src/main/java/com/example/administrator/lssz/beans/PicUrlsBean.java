package com.example.administrator.lssz.beans;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/3/28.
 */

public class PicUrlsBean {
    @JSONField(name = "thumbnail_pic")
    private String thumbnailPic;

    public String getThumbnailPic() {
        return thumbnailPic;
    }

    public void setThumbnailPic(String thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }
}
