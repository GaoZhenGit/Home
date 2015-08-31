package com.gz.home.utils;

import android.content.Context;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.gz.home.datamodel.User;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by host on 2015/8/25.
 * 网络请求接口类，用于解耦网络连接，可以换成其他网络后台请求方式
 */
public class NetworkUtil {
    public static void getUpdateUser(Context context,User oldUser, final UserListener userListenr){
        BmobQuery<User> query=new BmobQuery<User>();
        query.include("father,mother,spouse");
        query.getObject(context, oldUser.getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                if(user==null){
                    LogUtil.i("--tag--","user is null");
                }else{
                    LogUtil.i("--tag--","user not null");
                }
                userListenr.onSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {
                userListenr.onFailure(i,s);
            }
        });
    }

    public static void updateUser(Context context,final User user,final  UserListener userListenr) {
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
    public static void addFather(final Context context,final User me,String fatherPhone, final UserListener userListener){
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereEqualTo("username",fatherPhone);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list.size()>0){
                    User father=list.get(0);
                    me.setFather(father);
                    me.update(context, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            userListener.onSuccess(me);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            userListener.onFailure(i,s);
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public static void getParents(Context context,User me, final UserListListener userListListener){
        BmobQuery<User> query=new BmobQuery<>();
    }
    public static void searchPhone(Context context,String phone,final UserListListener userListListener){
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereMatches("username",phone);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                userListListener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                userListListener.onFailure(i,s);
            }
        });
    }

    public static void searchName(Context context,String phone,final UserListListener userListListener){
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereMatches("name",phone);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                userListListener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                userListListener.onFailure(i,s);
            }
        });
    }

    public interface UserListListener{
        void onSuccess(List<User> list);
        void onFailure(int i,String s);
    }

    public interface UserListener{
        void onSuccess(User user);
        void onFailure(int i, String s);
    }

    public interface FileUploadListener{
        void onError(int i, String s);
        void onSuccess(String s, String s1, String fileUrl);
        void onProgress(int i);
    }
}



