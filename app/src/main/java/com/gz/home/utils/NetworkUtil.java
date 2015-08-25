package com.gz.home.utils;

import android.content.Context;

import com.gz.home.datamodel.User;

import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by host on 2015/8/25.
 * 网络请求接口类，用于解耦网络连接，可以换成其他网络后台请求方式
 */
public class NetworkUtil {
    public static void getUpdateUser(Context context,User oldUser, final UserListenr userListenr){
        BmobQuery<User> query=new BmobQuery<User>();
        query.getObject(context, oldUser.getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                userListenr.onSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {
                userListenr.onFailure(i,s);
            }
        });
    }
    public interface UserListenr{
        void onSuccess(User user);
        void onFailure(int i, String s);
    }

}
