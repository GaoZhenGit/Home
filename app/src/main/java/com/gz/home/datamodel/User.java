package com.gz.home.datamodel;

import cn.bmob.v3.BmobUser;

/**
 * Created by host on 2015/8/19.
 */
public class User extends BmobUser {
    private String name;
    private int sex;
    private String avatar;
    private String address;
    private String detail;
    private int age;
    private User father;
    private User mother;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
