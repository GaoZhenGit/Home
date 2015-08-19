package com.gz.home.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.androidquery.AQuery;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gz.home.R;
import com.gz.home.datamodel.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;


public class SplashActivity extends BasePageActivity {
    private AQuery aq;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_splash);
        aq=new AQuery(this);
        YoYo.with(Techniques.Pulse).duration(5000).playOn(aq.id(R.id.splash).getView());
        if(BmobUser.getCurrentUser(this, User.class)!=null){
            this.user=BmobUser.getCurrentUser(this,User.class);
            postRedirectToActivity(MainActivity.class, 3000);
        } else {
            postRedirectToActivity(LoginActivity.class, 3000);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    public void postRedirectToActivity(final Class<?> targetActivity, int postTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,targetActivity));
                finish();
            }
        },postTime);
    }


}
