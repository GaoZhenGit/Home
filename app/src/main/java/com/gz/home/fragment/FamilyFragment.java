package com.gz.home.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.adapter.MemberAdapter;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;
import com.gz.home.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by host on 2015/8/25.
 */
public class FamilyFragment extends ListFragment {
    private User user;
    private View mView;
    private AQuery aq;
    private List<User> memberList;
    private MemberAdapter memberAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_family, container, false);
        aq=new AQuery(mView);
        fetchUserData();
        setListener();
        return mView;
    }

    private void fetchUserData() {
        user= BmobUser.getCurrentUser(getActivity(),User.class);
//        NetworkUtil.updateUser(getActivity(), user, new NetworkUtil.UserListenr() {
//            @Override
//            public void onSuccess(User user) {
//                FamilyFragment.this.user=user;
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
        User mother=new User();
        mother.setObjectId("th1");
        mother.setAvatar("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2584355946,4148531126&fm=116&gp=0.jpg");
        mother.setName("老妈");
        mother.setSex(Constant.USER.FEMALE);
        mother.setDetail("母亲的爱");
        user.setMother(mother);
        User father=new User();
        father.setObjectId("th2");
        father.setAvatar("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1511007589,3103936439&fm=111&gp=0.jpg");
        father.setName("老爸");
        father.setSex(Constant.USER.MALE);
        father.setDetail("爸爸去哪儿");
        user.setFather(father);
        memberList=new ArrayList<>();
        memberList.add(father);
        memberList.add(mother);
        memberAdapter=new MemberAdapter(getActivity(),memberList,user);
        setListAdapter(memberAdapter);
    }

    private void setListener() {

    }
}
