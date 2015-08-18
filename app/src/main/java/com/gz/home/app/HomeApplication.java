package com.gz.home.app;

import android.app.Application;

import com.gz.home.utils.ActivityManagerUtils;

/**
 * Created by host on 2015/8/18.
 */
public class HomeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void exit(){
        ActivityManagerUtils.getInstance().removeAllActivity();
    }
}
