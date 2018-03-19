package com.example.administrator.lssz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.WeiboAppManager;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.statistic.WBAgent;
import com.sina.weibo.sdk.utils.WbUtils;


public class WBDemoMainActivity extends Activity {

    private static Oauth2AccessToken mAccessToken;
    private TextView tvShowToken;
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShowToken = (TextView) findViewById(R.id.tv_show_token);

        WbSdk.install(this, new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));

        mSsoHandler = new SsoHandler(this);

        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            tvShowToken.setText(mAccessToken.toString());

        } else {
            auth2AllInOne();
        }
    }

    private void auth2AllInOne(){
        mSsoHandler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(final Oauth2AccessToken oauth2AccessToken) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAccessToken = oauth2AccessToken;
                        AccessTokenKeeper.writeAccessToken(WBDemoMainActivity.this, mAccessToken);
                        Toast.makeText(WBDemoMainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                        tvShowToken.setText(mAccessToken.toString());
                    }
                });

            }

            @Override
            public void cancel() {
                Toast.makeText(WBDemoMainActivity.this, "取消授权", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                Toast.makeText(WBDemoMainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    //    @Override
//    public void onResume() {
//        super.onResume();
//        //统计应用启动时间
//        WBAgent.onPageStart("WBDemoMainActivity");
//        WBAgent.onResume(this);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        //统计页面退出
//        WBAgent.onPageEnd("WBDemoMainActivity");
//        WBAgent.onPause(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //退出应用时关闭统计进程
//        WBAgent.onKillProcess();
//    }


}
