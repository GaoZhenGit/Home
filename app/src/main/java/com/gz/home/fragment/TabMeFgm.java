package com.gz.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.activity.UserModifyActivity;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;
import com.gz.home.utils.LogUtil;
import com.gz.home.utils.NetworkUtil;
import com.gz.home.utils.UpdataSubject;

import cn.bmob.v3.BmobUser;

/**
 * Created by host on 2015/8/20.
 */
public class TabMeFgm extends Fragment implements UpdataSubject.UpdataListener{
    private User user;
    private View mView;
    private AQuery aq;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        mView=inflater.inflate(R.layout.fragment_tab_me, container, false);
        UpdataSubject.getInstance().addListener(this);
        aq=new AQuery(mView);
        fetchUserData();
        setListener();
        return mView;
    }


    private void fetchUserData() {
        //从本地数据库读取数据
        this.user=User.readInDb(getActivity());
    }

    private void initView(){
        LogUtil.i("tab me initview");
        aq.id(R.id.me_name).text(user.getName());
        aq.id(R.id.me_detail).text(user.getDetail());
        if(user.getAvatar()!=null) {
            aq.id(R.id.me_avatar).image(user.getAvatar());
        }
        if (user.getSex()==Constant.USER.MALE) {
            aq.id(R.id.me_sex).image(R.drawable.male);
        } else {
            aq.id(R.id.me_sex).image(R.drawable.female);
        }
    }

    private void setListener() {
        aq.id(R.id.btn_user).clicked(this, "aq_user_modify");
    }

    public void aq_user_modify(){
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.USER.DATA,user);
        Intent intent=new Intent(getActivity(), UserModifyActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        user=User.readInDb(getActivity());
        initView();
    }
    @Override
    public void onDestroy(){
        //移除更新监听器，防止内存泄露
        UpdataSubject.getInstance().removeListener(this);
        LogUtil.i("tab me destroy");
        super.onDestroy();
    }

    //用于接收更新后的user，更新监听器
    @Override
    public void onUserChange(User user) {
//        if(user==null)
//            return;
//        this.user=user;
//        initView();
    }
}
