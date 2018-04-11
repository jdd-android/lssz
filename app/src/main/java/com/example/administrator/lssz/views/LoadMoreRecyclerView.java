package com.example.administrator.lssz.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.w4lle.library.NineGridlayout;

import org.w3c.dom.Attr;

/**
 * Created by Administrator on 2018/4/10.
 */

public class LoadMoreRecyclerView extends RecyclerView {

    private PullActionListener mPullActionListener;

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new LoadMoreController());
    }

    public void setPullActionListener(PullActionListener pullActionListener) {
        mPullActionListener = pullActionListener;
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }


    private class LoadMoreController extends OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//            if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
//                int lastCompletelyVisibleItemPostition = manager.findLastCompletelyVisibleItemPosition();
//                if (lastCompletelyVisibleItemPostition == manager.getItemCount() - 1) {
//                    if (mPullActionListener != null) {
//                        mPullActionListener.onPullUpLoadMore();
//                    }
//                }
//            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0 && !recyclerView.canScrollVertically(1)) {
                if (mPullActionListener != null) {
                    mPullActionListener.onPullUpLoadMore();
                }
            }
        }
    }

    public interface PullActionListener {
        void onPullUpLoadMore();
    }
}
