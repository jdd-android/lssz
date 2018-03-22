package com.example.administrator.lssz.api;

import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.Error;
import com.example.administrator.lssz.common.IError;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author lc. 2018-03-22 10:12
 * @since 1.0.0
 */

public class ApiClient {

    private static final OkHttpClient sClient;

    private static final String BASE_API_URL = "https://api.weibo.com/2";

    static {
        // 设置一个全局的 OkHttpClient 可以复用连接池，减少资源占用
        sClient = new OkHttpClient();
    }

    /**
     * 请求公共微博数据
     *
     * @param accessToken 授权令牌
     */
    public void requestPublicLine(String accessToken, final Callback<List<StatusBean>, IError> callback) {
        String url = BASE_API_URL + "/public_timeline.json";
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("access_token", accessToken)
                .build();

        sClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new Error(-1, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // TODO 解析数据 -> List<StatusBean>，通过 callback.success() 回调
                } else {
                    // TODO 回调错误数据
                }

            }
        });
    }
}
