package com.example.administrator.lssz.adpters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.utils.DateUtils;
import com.example.administrator.lssz.common.utils.PicUrlUtils;
import com.example.administrator.lssz.dialogs.CompleteImageDialog;
import com.w4lle.library.NineGridlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class StatusesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    //普通布局
    private final int TYPE_ITEM = 1;
    //脚布局
    private final int TYPE_FOOTER = 2;
    //加载
    public final int LOADING = 1;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    private int loadState = 2;

    private List<StatusBean> mStatusesList = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;
    private Context context;

    public StatusesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        //设置最后一个item为Footerview
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StatusViewHolder) {
            StatusViewHolder statusViewHolder = (StatusViewHolder) holder;
            final StatusBean statusBean = mStatusesList.get(position);
            //给微博状态视图设置tag，传入当前statusBean
            statusViewHolder.itemView.setTag(statusBean);

            Glide.with(context).load(statusBean.getUser().getAvatarLarge())
                    .apply(RequestOptions.circleCropTransform())
                    .into(statusViewHolder.statusUserIamge);
            statusViewHolder.statusUserName.setText(statusBean.getUser().getName());
            statusViewHolder.statusTime.setText(DateUtils.readableDate(statusBean.getCreatedAt()));
            statusViewHolder.statusText.setText(statusBean.getText());

            //加载图片，设置图片点击监听
            statusViewHolder.statusPics.setAdapter(new ImageLoadAdapter(context, statusBean.getPicUrlsList()));
            statusViewHolder.statusPics.setOnItemClickListerner(new NineGridlayout.OnItemClickListerner() {
                @Override
                public void onItemClick(View view, int position) {
//                    Toast.makeText(context, "这是第 " + (position + 1) + " 张图，url为 " + statusBean.getPicUrlsList().get(position).getThumbnailPic(), Toast.LENGTH_SHORT).show();
                    CompleteImageDialog myDialog = new CompleteImageDialog(context, PicUrlUtils.getOriginalPic(statusBean.getPicUrlsList().get(position).getThumbnailPic()));
                    myDialog.show();
                    Window dialogWin = myDialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWin.getAttributes();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialogWin.setAttributes(lp);
                }
            });
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING:
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        //加一个FooterView
        return mStatusesList.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_item, parent, false);
            view.setOnClickListener(this);
            return new StatusViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.refresh_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    public void setStatusesList(final List<StatusBean> statusesList) {
        mStatusesList = statusesList;
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {
        ImageView statusUserIamge;
        TextView statusUserName;
        TextView statusTime;
        TextView statusText;
        NineGridlayout statusPics;

        StatusViewHolder(View view) {
            super(view);
            statusUserIamge = view.findViewById(R.id.iv_status_image);
            statusUserName = view.findViewById(R.id.tv_status_name);
            statusTime = view.findViewById(R.id.tv_status_time);
            statusText = view.findViewById(R.id.tv_status_text);
            statusPics = view.findViewById(R.id.nineGrid_status_pics);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public interface OnRecyclerViewListener {
        void onItemClick(View view, StatusBean statusBean);
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewListener != null) {
            onRecyclerViewListener.onItemClick(v, (StatusBean) v.getTag());
        }
    }
}
