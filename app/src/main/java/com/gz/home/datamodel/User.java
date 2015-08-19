package com.gz.home.datamodel;

import cn.bmob.v3.BmobUser;

/**
 * Created by host on 2015/8/19.
 */
public class User extends BmobUser {
    private String name;
    private Integer sex;
    private String avatar;
    private User father;
    private User mother;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
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
}
