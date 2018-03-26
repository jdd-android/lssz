package com.example.administrator.lssz.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/3/26.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            int lastItemPosition = manager.findLastVisibleItemPosition();
            int itemCount = manager.getItemCount();

            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        isSlidingUpward = dy > 0;
    }

    public abstract void onLoadMore();
}
