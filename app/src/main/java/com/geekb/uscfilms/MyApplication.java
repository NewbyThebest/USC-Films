package com.geekb.uscfilms;

import android.app.Application;

import com.tencent.mmkv.MMKV;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MainManager.getInstance().initRetrofit();
        MMKV.initialize(this);
    }
}
