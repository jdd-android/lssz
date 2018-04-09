package com.example.administrator.lssz.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lssz.R;
import com.example.administrator.lssz.api.ApiClient;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.IError;
import com.example.administrator.lssz.module.user.UserInfoKeeper;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class UpdateStatusActivity extends Activity {
    private EditText mEditText;
    private Button mBtUpdateStatus;
    private TextView mTvUserName;
    private static Oauth2AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        accessToken = AccessTokenKeeper.readAccessToken(UpdateStatusActivity.this);
        mBtUpdateStatus = (Button) findViewById(R.id.bt_update_status);
        mTvUserName = (TextView) findViewById(R.id.tv_update_status_username);
        mTvUserName.setText(UserInfoKeeper.readUserInfo(UpdateStatusActivity.this).getName());
        mEditText = (EditText) findViewById(R.id.ed_update_status);
        mEditText.addTextChangedListener(new EditTextListener());
    }

    public void cancelNewStatus(View view) {
        finish();
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

    class EditTextListener implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(mEditText.getText())) {
                mBtUpdateStatus.setEnabled(true);
            } else {
                mBtUpdateStatus.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    }
}
