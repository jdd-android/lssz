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

public class LoadMoreRecyclerView extends RecyclerView{

    private PullActionListener mPullActionListener;

    public LoadMoreRecyclerView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void setPullActionListener(PullActionListener pullActionListener) {
        mPullActionListener = pullActionListener;
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public LoadMoreRecyclerView(Context context){
        this(context,null);
    }


    private class LoadMoreController extends OnScrollListener{
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager manager=(LinearLayoutManager)recyclerView.getLayoutManager();
            if(newState==RecyclerView.SCROLL_STATE_IDLE){
                int lastCompletelyVisibleItemPostition=manager.findLastCompletelyVisibleItemPosition();
                if(lastCompletelyVisibleItemPostition==manager.getItemCount()){
                    if(mPullActionListener!=null){
                        mPullActionListener.onPullUpLoadMore();
                    }
                }
            }
        }
    }
    public interface PullActionListener{
        void onPullUpLoadMore();
    }
}
