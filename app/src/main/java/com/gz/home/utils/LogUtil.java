package com.gz.home.utils;

import android.util.Log;

/**
 * Created by host on 2015/8/26.
 */
public class LogUtil {
    public static void i(String tag,String info){
        Log.i(tag,info);
    }
    public static void i(String info){
        Log.i("------------------",info);
    }
}
