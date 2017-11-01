package com.zhuwb.horseracelampdemo;

import android.app.Application;
import android.os.Handler;

/**
 * 作者： ZhuWB
 * 创建时间 ： 2017/10/18 16:36
 */

public class MyApp extends Application {
    private Handler handler = null;

    //set方法
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    //get方法
    public Handler getHandler() {
        return handler;
    }
}
