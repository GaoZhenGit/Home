package com.gz.home.app;

import android.app.Application;

import com.gz.home.utils.ActivityManagerUtils;

import cn.bmob.v3.Bmob;

/**
 * Created by host on 2015/8/18.
 */
public class HomeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,Constant.ID.BMOBID);
    }

    public void exit(){
        ActivityManagerUtils.getInstance().removeAllActivity();
        ActivityManagerUtils.getInstance().exit();
    }
}
