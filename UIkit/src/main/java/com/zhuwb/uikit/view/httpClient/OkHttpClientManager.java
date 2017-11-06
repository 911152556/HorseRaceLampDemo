package com.zhuwb.uikit.view.httpClient;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * 作者： ZhuWB
 * 创建时间 ： 2017/10/18 14:41
 */

public class OkHttpClientManager {

    /**
     * OkHttpClient;
     */
    private OkHttpClient mOkHttpClient;

    /**
     *
     */
    private static OkHttpClientManager mInstance;

    private Handler mDelivery;


    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();

                }
            }

        }
        return mInstance;
    }


    /**
     * 同步的get请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    private Response _getAsyn(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    private String _getAsString(String url) throws IOException {
        Response execute = _getAsyn(url);
        return execute.body().string();
    }

    public static Response getAsyn(String url) throws IOException {
        return getInstance()._getAsyn(url);
    }


    public static String getAsString(String url) throws IOException {
        return getInstance()._getAsString(url);
    }


}
