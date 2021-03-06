package com.example.administrator.lssz.api;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.lssz.beans.CommentBean;
import com.example.administrator.lssz.beans.StatusBean;
import com.example.administrator.lssz.beans.UserBean;
import com.example.administrator.lssz.common.Callback;
import com.example.administrator.lssz.common.Error;
import com.example.administrator.lssz.common.IError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author lc. 2018-03-22 10:12
 * @since 1.0.0
 */

public class ApiClient {

    private static final OkHttpClient sClient;

    private static final String BASE_API_URL = "https://api.weibo.com/2/";
    private static final String CALL_BACK_SUCCESS="Success";
    private static final String CALL_BACK_FAIL="Fail";

    static {
        // 设置一个全局的 OkHttpClient 可以复用连接池，减少资源占用
        sClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
    }

    /**
     * 请求公共微博数据
     *
     * @param accessToken 授权令牌
     */

    public void requestPublicLine(String accessToken, final Callback<List<StatusBean>, IError> callback) {
        String url = BASE_API_URL + "statuses/public_timeline.json?access_token=" + accessToken;
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        sClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new Error(-1, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    List<StatusBean> statuses = new ArrayList<>();
                    String data = response.body().string();
                    Log.i("Response", data);
                    JSONObject jsonObject = JSONObject.parseObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("statuses");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StatusBean status = JSONObject.parseObject(jsonObject1.toJSONString(), StatusBean.class);
                        statuses.add(status);
                    }
                    callback.success(statuses);
                } else {
                    // FIXME 请求失败了也要把错误的信息回传给接口调用方
                    callback.failure(new Error(-2,response.message()));
                }

            }
        });
    }

    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     *
     * @param accessToken 授权令牌
     * @param page        请求页码
     */
    public void requestFriendsLine(String accessToken, int page, final Callback<List<StatusBean>, IError> callback) {
        String url = BASE_API_URL + "statuses/friends_timeline.json?access_token=" + accessToken + "&page=" + page;
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        sClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new Error(-1, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    List<StatusBean> statuses = new ArrayList<>();
                    String data = response.body().string();
                    Log.i("Response", data);
                    JSONObject jsonObject = JSONObject.parseObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("statuses");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StatusBean status = JSONObject.parseObject(jsonObject1.toJSONString(), StatusBean.class);
                        statuses.add(status);
                    }
                    callback.success(statuses);
                } else {
                    callback.failure(new Error(-2,response.message()));
                }

            }
        });
    }

    /**
     * 请求用户信息
     *
     * @param accessToken 授权令牌
     * @param uid         用户id
     */

    public void requestUsersShow(String accessToken, String uid, final Callback<UserBean, IError> callback) {
        String url = BASE_API_URL + "users/show.json?access_token=" + accessToken + "&uid=" + uid;
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        sClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new Error(-1, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    UserBean user = JSONObject.parseObject(response.body().string(), UserBean.class);
                    callback.success(user);
                } else {
                    callback.failure(new Error(-2,response.message()));
                }
            }
        });
    }

    /**
     * 请求评论列表
     *
     * @param accessToken 授权令牌
     * @param id          微博id
     */
    public void requestStatusComment(String accessToken, String id, final Callback<List<CommentBean>, IError> callback) {
        String url = BASE_API_URL + "comments/show.json?access_token=" + accessToken + "&id=" + id;
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        sClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new Error(-1, e.getMessage()));

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    Log.i("Response", data);
                    List<CommentBean> comments = new ArrayList<>();
                    JSONArray jsonArray = JSONObject.parseObject(data).getJSONArray("comments");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        CommentBean comment = JSONObject.parseObject(jsonArray.get(i).toString(), CommentBean.class);
                        comments.add(comment);
                    }
                    callback.success(comments);
                } else {
                    callback.failure(new Error(-2,response.message()));
                }
            }
        });
    }

    /**
     * 获取单条微博内容
     *
     * @param accessToken 授权令牌
     * @param id          微博id
     */
    public void requestSingleStatus(String accessToken, String id, final Callback<StatusBean, IError> callback) {
        String url = BASE_API_URL + "statuses/show.json?access_token=" + accessToken + "&id=" + id;
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        sClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new Error(-1, e.getMessage()));

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    Log.i("Response", data);
                    StatusBean statusBean = JSONObject.parseObject(data, StatusBean.class);
                    callback.success(statusBean);
                } else {
                    callback.failure(new Error(-2,response.message()));
                }
            }
        });
    }

    /**
     * 发布一条带链接的新微博
     *
     * @param accessToken 授权令牌
     * @param status      微博内容
     */

    public void postNewStatus(String accessToken, String status, final Callback<StatusBean , IError> callback) {
        String url = BASE_API_URL + "statuses/share.json";
        RequestBody formBody = new FormBody.Builder()
                .add("access_token", accessToken)
                .add("status", status+"http://www.baidu.com")
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        sClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new Error(-1, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    Log.i("Response", data);
                    StatusBean statusBean = JSONObject.parseObject(data, StatusBean.class);
                    callback.success(statusBean);
                } else {
                    callback.failure(new Error(-2,response.message()));
                }

            }
        });

    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Log.i("OkHttp", "request:" + request.toString());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Log.i("OkHttp", String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            if (!TextUtils.isEmpty(content) && content.length() > 2000) {
                for (int i = 0; i < content.length(); i += 2000) {
                    Log.i("OkHttp", "response body:" + content.substring(i, i + 2000 > content.length() ? content.length() : (i + 2000)));
                }
            } else {
                Log.i("OkHttp", "response body:" + content);
            }

            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }
}
