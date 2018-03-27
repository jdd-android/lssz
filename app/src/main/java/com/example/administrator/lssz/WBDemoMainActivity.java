package com.example.administrator.lssz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lssz.ui.HomeActivity;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import java.text.SimpleDateFormat;


public class WBDemoMainActivity extends Activity {

    private static Oauth2AccessToken mAccessToken;
    private TextView tvShowToken;
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShowToken = (TextView) findViewById(R.id.tv_show_token);

        mSsoHandler = new SsoHandler(this);
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            updateTokenView(true);

        } else {
            auth2AllInOne();
        }
    }

    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH.mm.ss").format(new java.util.Date(mAccessToken.getExpiresTime()));
        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
        tvShowToken.setText(String.format(format, mAccessToken.getToken(), date));

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
        tvShowToken.setText(message);

    }

    private void auth2AllInOne() {
        mSsoHandler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(final Oauth2AccessToken oauth2AccessToken) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAccessToken = oauth2AccessToken;
                        if (mAccessToken.isSessionValid()) {
                            updateTokenView(false);
                            AccessTokenKeeper.writeAccessToken(WBDemoMainActivity.this, mAccessToken);
                            Toast.makeText(WBDemoMainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();

                        }

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


    public void getPublicMessages(View view) {
        Intent intent = new Intent(WBDemoMainActivity.this, HomeActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


}
