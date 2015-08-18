package com.gz.home.activity;


import android.os.Bundle;

import com.androidquery.AQuery;
import com.gz.home.R;

public class LoginActivity extends BasePageActivity {
    private AQuery aq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_login);
        aq=new AQuery(this);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_left_tv).visible().text("退出");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_mid_text).text("登录");
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this,"finish");
    }
}

