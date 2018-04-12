package com.example.administrator.lssz.module.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.module.home.timeline.FriendsTimelineFragment;
import com.example.administrator.lssz.module.home.timeline.PublicTimelineFragment;

/**
 * Created by Administrator on 2018/3/27.
 */

public class WeiboFragment extends Fragment {
    static final int NUM_ITEMS = 2;

    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private TabLayout mTabLayout;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_home_weibo, null);
        mViewPager = mRoot.findViewById(R.id.weibo_pager);
        mTabLayout = mRoot.findViewById(R.id.weibo_tablayout);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return mRoot;
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PublicTimelineFragment.getNewInstance();
                case 1:
                    return FriendsTimelineFragment.getNewInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Public";
                case 1:
                    return "Follow";
                default:
                    return null;
            }
        }
    }
}
