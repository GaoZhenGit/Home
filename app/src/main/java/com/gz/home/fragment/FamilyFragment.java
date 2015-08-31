package com.gz.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.adapter.MemberAdapter;
import com.gz.home.datamodel.User;
import com.gz.home.utils.LogUtil;
import com.gz.home.utils.NetworkUtil;
import com.gz.home.utils.UpdataSubject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by host on 2015/8/25.
 */
public class FamilyFragment extends Fragment implements UpdataSubject.UpdataListener {
    private User user;
    private View mView;
    private ListView listView;
    private AQuery aq;
    private List<User> memberList;
    private MemberAdapter memberAdapter;
    private boolean needReflesh=false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        UpdataSubject.getInstance().addListener(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_family, container, false);
        aq=new AQuery(mView);
        listView=(ListView)mView.findViewById(R.id.famliy_list);
        fetchUserData();
        setListener();
        return mView;
    }

    private void fetchUserData() {
        memberList=new ArrayList<>();
        user=BmobUser.getCurrentUser(getActivity(),User.class);
        NetworkUtil.getUpdateUser(getActivity(),user, new NetworkUtil.UserListener() {
            @Override
            public void onSuccess(User user) {
                FamilyFragment.this.user=user;
                initView(user);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
//        User mother=new User();
//        mother.setObjectId("th1");
//        mother.setAvatar("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2584355946,4148531126&fm=116&gp=0.jpg");
//        mother.setName("老妈");
//        mother.setSex(Constant.USER.FEMALE);
//        mother.setDetail("母亲的爱");
//        user.setMother(mother);
//        User father=new User();
//        father.setObjectId("th2");
//        father.setAvatar("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1511007589,3103936439&fm=111&gp=0.jpg");
//        father.setName("老爸");
//        father.setSex(Constant.USER.MALE);
//        father.setDetail("爸爸去哪儿");
//        user.setFather(father);
//        memberList=new ArrayList<>();
//        memberList.add(father);
//        memberList.add(mother);
//        memberAdapter=new MemberAdapter(getActivity(),memberList,user);
//        setListAdapter(memberAdapter);
    }

    private void setListener() {

    }
    private void initView(User user) {
        memberList.clear();
        if (user.getFather() != null && user.getFather().getObjectId() != null) {
            memberList.add(user.getFather());
        }
        if (user.getMother() != null && user.getMother().getObjectId() != null) {
            memberList.add(user.getMother());
        }
        if (user.getSpouse() != null && user.getSpouse().getObjectId() != null) {
            memberList.add(user.getSpouse());
        }

        if(memberAdapter==null) {
            memberAdapter = new MemberAdapter(getActivity(), memberList, user);
            listView.setAdapter(memberAdapter);
        }else {
            memberAdapter.setMe(user);
            memberAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        if(needReflesh){
            LogUtil.i("needreflesh",""+needReflesh);
            NetworkUtil.getUpdateUser(getActivity(), user, new NetworkUtil.UserListener() {
                @Override
                public void onSuccess(User user) {
                    LogUtil.i("refresh","family");
                    FamilyFragment.this.user=user;
                    initView(user);
                    needReflesh=false;
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }
    }
    @Override
    public void onDestroy(){
        UpdataSubject.getInstance().removeListener(this);
    }
    @Override
    public void onUserChange(User user) {
        needReflesh=true;
    }
}
