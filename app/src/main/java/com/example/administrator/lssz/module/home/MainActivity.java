package com.example.administrator.lssz.module.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.databinding.NavHeaderMainBinding;
import com.example.administrator.lssz.eventbus.AuthReturnEvent;
import com.example.administrator.lssz.eventbus.UserInfoRefreshEvent;
import com.example.administrator.lssz.module.user.AuthManager;
import com.example.administrator.lssz.module.user.UserInfoKeeper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private FragmentManager mFragmentManager;
    private WeiboFragment mWeiboFragment;
    private View headerView;
    private RelativeLayout navHeader;
    private NavigationView navigationView;
    private NavHeaderMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        EventBus.getDefault().register(this);

        if (!AuthManager.getInstance(this).isAuthSuccess()) {
            AuthManager.getInstance(this).startAuth(this);
        }
    }


    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //ActionBarDrawerToggle 是DrawerLayout.DrawerListener的实现
        drawerLayout.addDrawerListener(toggle);
//        drawerLayout.addDrawerListener(new MyDrawerListener());
        toggle.syncState();

        //抽屉界面
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //头部视图
        headerView = navigationView.getHeaderView(0);
        navHeader = headerView.findViewById(R.id.nav_header);
        binding = DataBindingUtil.bind(navHeader);
        binding.setUser(UserInfoKeeper.readUserInfo(this));
        binding.setOnClickListener(this);
        binding.executePendingBindings();

        //主界面
        mFragmentManager = getSupportFragmentManager();
        mWeiboFragment = new WeiboFragment();
        mFragmentManager.beginTransaction().add(R.id.home_content, mWeiboFragment).commit();

        fab = findViewById(R.id.fab_newState);
        fab.setOnClickListener(this);
    }

    class MyDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerClosed(View drawerView) {

        }

        @Override
        public void onDrawerOpened(View drawerView) {
            binding.setUser(AuthManager.getInstance(MainActivity.this).getCurrentUser());
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAuthCallbackEvent(AuthReturnEvent event) {
        if (event.isSuccess()) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        } else if (event.isFail()) {
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
        } else if (event.isCancel()) {
            Toast.makeText(this, "登录取消", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoRefreshEvent(UserInfoRefreshEvent event) {
        if (event.isRefreshSuccess()) {
            binding.setUser(UserInfoKeeper.readUserInfo(this));
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_newState:
                startActivity(new Intent(MainActivity.this, UpdateStatusActivity.class));
                break;
            case R.id.nav_header:
                if (!AuthManager.getInstance(this).isAuthSuccess()) {
                    AuthManager.getInstance(this).startAuth(this);
                }
                break;
            default:
                break;
        }

    }
}
