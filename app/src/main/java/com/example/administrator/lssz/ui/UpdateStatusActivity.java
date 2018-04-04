package com.example.administrator.lssz.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class UpdateStatusActivity extends Activity {
    private EditText mEditText;
    private static Oauth2AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        accessToken = AccessTokenKeeper.readAccessToken(UpdateStatusActivity.this);
        mEditText = (EditText) findViewById(R.id.ed_update_status);
    }

    public void updateStatus(View view) {
        String status = mEditText.getText().toString();
        if (!status.isEmpty()) {
            new ApiClient().postNewStatus(accessToken.getToken(), status, new Callback<StatusBean, IError>() {
                @Override
                public void success(StatusBean data) {
                    finish();
                }

                @Override
                public void failure(IError error) {

                }
            });

        } else {
            Toast.makeText(UpdateStatusActivity.this, "编辑内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
