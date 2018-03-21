package com.example.administrator.lssz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lssz.ui.PublicTimelineActivity;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WBDemoMainActivity extends Activity {

    private static Oauth2AccessToken mAccessToken;
    private TextView tvShowToken;
    private TextView tvShowUser;
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShowToken = (TextView) findViewById(R.id.tv_show_token);
        tvShowUser = (TextView) findViewById(R.id.tv_show_userMessage);

        WbSdk.install(getApplicationContext(), new AuthInfo(getApplicationContext(), Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));

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

    public void getUserMessages(View view) {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().get().url("https://api.weibo.com/2/users/show.json?access_token=" + mAccessToken.getToken() + "&uid=" + mAccessToken.getUid()).build();
        new Thread() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getPublicMessages(View view){
        Intent intent=new Intent(WBDemoMainActivity.this,PublicTimelineActivity.class);
        startActivity(intent);

    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject statusObject = jsonObject.getJSONObject("status");
            String text = statusObject.getString("text");
            Log.i("TAG", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }




}
