package com.gz.home.datamodel;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.home.app.Constant;
import com.gz.home.utils.SpUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by host on 2015/8/19.
 */
public class User extends BmobUser {
    private String id;
    private String name;
    private int sex;
    private String avatar;
    private String address;
    private String detail;
    private int age;
    private User father;
    private User mother;
    private User spouse;
    private boolean mdfName=true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setUsername(String userName){
        super.setUsername(userName);
        this.id=userName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User getFather() {
        return father;
    }

    public void setFather(User father) {
        this.father = father;
    }

    public User getMother() {
        return mother;
    }

    public void setMother(User mother) {
        this.mother = mother;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isMdfName() {
        return mdfName;
    }

    public void setMdfName(boolean mdfName) {
        this.mdfName = mdfName;
    }

    public User getSpouse() {
        return spouse;
    }

    public void setSpouse(User spouse) {
        this.spouse = spouse;
    }

    @Override
    public void update(final Context context, final String objectId, final UpdateListener listener){
        super.update(context, objectId, listener);
        saveInDb(context);
    }
    @Override
    public void update(Context context, UpdateListener listener) {
        super.update(context,listener);
        saveInDb(context);
    }
    @Override
    public void update(Context context){
        super.update(context);
        saveInDb(context);
    }
    public void saveInDb(Context context){
//        if(this.id==null||this.id.length()!=11)
//            return;
//        DbUtils db=DbUtils.create(context);
//        try {
//            db.saveBindingId(this);
//        } catch (DbException e) {
//            e.printStackTrace();
//            try {
//                db.update(this);
//            } catch (DbException e1) {
//                e1.printStackTrace();
//            }
//        }
        Gson gson =new GsonBuilder().disableHtmlEscaping().create();
        String userjson=gson.toJson(this);
        SpUtils sp=new SpUtils(context);
        sp.setValue(Constant.KeyValue.USER,userjson);
    }
    public static User readInDb(Context context){
//        DbUtils db=DbUtils.create(context);
//        try {
//            return db.findFirst(Selector.from(User.class));
//        } catch (DbException e) {
//            e.printStackTrace();
//            return null;
//        }
        Gson gson =new GsonBuilder().disableHtmlEscaping().create();
        SpUtils sp=new SpUtils(context);
        return gson.fromJson(sp.getValue(Constant.KeyValue.USER,""),User.class);
    }
    public static void clearFromDb(Context context){
//        DbUtils db=DbUtils.create(context);
//        try {
//            db.deleteAll(User.class);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        SpUtils sp=new SpUtils(context);
        sp.setValue(Constant.KeyValue.USER,"");
    }
    public void sameId(){
        this.id=this.getUsername();
    }
}
