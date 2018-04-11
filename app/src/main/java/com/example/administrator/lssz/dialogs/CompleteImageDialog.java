package com.example.administrator.lssz.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.views.MatrixImageView;

/**
 * Created by Administrator on 2018/3/28.
 */

public class CompleteImageDialog extends Dialog {

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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Glide.with(context)
                .load(url)
                .into(imageView);
        super.onCreate(savedInstanceState);
        setContentView(dialogView);
    }
}
