package com.gz.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;
import com.gz.home.utils.NetworkUtil;
import com.gz.home.utils.UpdataSubject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class MemberActivity extends BasePageActivity {
    private User user;
    private User me;
    private AQuery aq;
    private CheckBox checkFather;
    private CheckBox checkMother;
    private CheckBox checkSpouse;

    @Override
    protected void initData() {
        user=(User)mBundle.getSerializable(Constant.USER.DATA);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_member);
        aq=new AQuery(this);
        checkFather=(CheckBox)findViewById(R.id.check_father);
        checkMother=(CheckBox)findViewById(R.id.check_mother);
        checkSpouse=(CheckBox)findViewById(R.id.check_spouse);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
        aq.id(R.id.title_mid_text).text("添加亲友");

        //用户信息显示
        aq.id(R.id.mem_avater).image(user.getAvatar());
        aq.id(R.id.mem_name).text(user.getName());
        aq.id(R.id.mem_phone).text(user.getUsername());
        aq.id(R.id.mem_address).text(user.getAddress());
        aq.id(R.id.mem_detail).text(user.getDetail());
        if(user.getSex()==Constant.USER.MALE){
            aq.id(R.id.mem_sex).text("男");
        }else {
            aq.id(R.id.mem_sex).text("女");
        }
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "onBackPressed");


        //设置checkbox单选
        checkFather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFather.setChecked(true);
                checkMother.setChecked(false);
                checkSpouse.setChecked(false);
            }
        });
        checkMother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFather.setChecked(false);
                checkMother.setChecked(true);
                checkSpouse.setChecked(false);
            }
        });
        checkSpouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFather.setChecked(false);
                checkMother.setChecked(false);
                checkSpouse.setChecked(true);
            }
        });

        //设置添加按钮
        aq.id(R.id.btn_mem_add).clicked(this, "aq_mem_add");
    }

    public void aq_mem_add(){
        User oldme=BmobUser.getCurrentUser(this, User.class);
        NetworkUtil.getUpdateUser(this, oldme, new NetworkUtil.UserListener() {
            @Override
            public void onSuccess(User user) {
                me=user;
                if(checkFather.isChecked()){
                    me.setFather(MemberActivity.this.user);
                    NetworkUtil.updateUser(MemberActivity.this, me, new NetworkUtil.UserListener() {
                        @Override
                        public void onSuccess(User user) {
                            ShowToast("设置成功");
//                            UpdataSubject.getInstance().callUpdata(null);
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ShowToast("设置失败");
                        }
                    });
                }else if(checkMother.isChecked()){
                    me.setMother(MemberActivity.this.user);
                    NetworkUtil.updateUser(MemberActivity.this, me, new NetworkUtil.UserListener() {
                        @Override
                        public void onSuccess(User user) {
                            ShowToast("设置成功");
//                            UpdataSubject.getInstance().callUpdata(null);
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ShowToast("设置失败");
                        }
                    });
                }else if(checkSpouse.isChecked()){
                    me.setSpouse(MemberActivity.this.user);
                    NetworkUtil.updateUser(MemberActivity.this, me, new NetworkUtil.UserListener() {
                        @Override
                        public void onSuccess(User user) {
                            ShowToast("设置成功");
//                            UpdataSubject.getInstance().callUpdata(null);
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ShowToast("设置失败");
                        }
                    });
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

}
