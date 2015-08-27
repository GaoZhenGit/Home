package com.gz.home.utils;

import android.content.Context;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.gz.home.datamodel.User;

import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

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

    public static void updateUser(Context context,final User user,final  UserListenr userListenr) {
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                userListenr.onSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {
                userListenr.onFailure(i, s);
            }
        });
    }
    public static void upload(Context context,String filepath,final FileUploadListener fileUploadListener){
        BTPFileResponse response = BmobProFile.getInstance(context).upload(filepath, new UploadListener() {
            @Override
            public void onError(int i, String s) {
                fileUploadListener.onError(i,s);
            }

            @Override
            public void onSuccess(String s, String s1, BmobFile bmobFile) {
                fileUploadListener.onSuccess(s,s1,bmobFile.getUrl());
            }

            @Override
            public void onProgress(int i) {
                fileUploadListener.onProgress(i);
            }
        });
    }
    public interface UserListenr{
        void onSuccess(User user);
        void onFailure(int i, String s);
    }

    public interface FileUploadListener{
        void onError(int i, String s);
        void onSuccess(String s, String s1, String fileUrl);
        void onProgress(int i);
    }
}



