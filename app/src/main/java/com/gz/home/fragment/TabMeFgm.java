package com.gz.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;
import com.gz.home.utils.LogUtil;
import com.gz.home.utils.NetworkUtil;

import cn.bmob.v3.BmobUser;

/**
 * Created by host on 2015/8/20.
 */
public class TabMeFgm extends Fragment {
    private User user;
    private View mView;
    private AQuery aq;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        mView=inflater.inflate(R.layout.fragment_tab_me, container, false);
        aq=new AQuery(mView);
        fetchUserData();
        return mView;
    }

    private void fetchUserData() {
        this.user= BmobUser.getCurrentUser(getActivity(),User.class);
        initView();
        //更新用户类
        if(user!=null){
            NetworkUtil.getUpdateUser(getActivity(), user, new NetworkUtil.UserListenr() {
                @Override
                public void onSuccess(User user) {
                    TabMeFgm.this.user = user;
                    user.update(getActivity());
                    initView();
                }

                @Override
                public void onFailure(int i, String s) {
                    LogUtil.i(i + " " + s);
                }
            });
        }else {
            LogUtil.i("user is null");
        }
    }

    private void initView(){
        LogUtil.i(user.getUsername());
        aq.id(R.id.me_name).text(user.getUsername());
        if(user.getAvatar()!=null) {
            aq.id(R.id.me_avatar).image(user.getAvatar());
        }
        if (user.getSex()==Constant.USER.MALE) {
            aq.id(R.id.me_sex).image(R.drawable.male);
        } else {
            aq.id(R.id.me_sex).image(R.drawable.female);
        }
    }

}
