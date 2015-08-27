package com.gz.home.utils;

import com.gz.home.datamodel.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by host on 2015/8/27.
 * 用于统一更新数据，观察者模式
 */
public class UpdataSubject {

    public static UpdataSubject instance;
    private List<UpdataListener> listeners;


    private UpdataSubject(){
        listeners=new ArrayList<>();
    }

    public synchronized static UpdataSubject getInstance(){
        if(instance==null){
            instance=new UpdataSubject();
        }
        return instance;
    }

    public void addListener(UpdataListener listener){
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public void removeListener(UpdataListener listener){
        if(listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

    public void callUpdata(User user){
        for(UpdataListener listener:listeners){
            listener.onUserChange(user);
        }
    }

    public interface UpdataListener{
        void onUserChange(User user);
    }

}
