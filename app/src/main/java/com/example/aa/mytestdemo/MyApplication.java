package com.example.aa.mytestdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by aa on 17/3/27.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
