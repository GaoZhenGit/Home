package com.gz.home.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.gz.home.R;

import cn.bmob.v3.BmobUser;


public class MainActivity extends BasePageActivity {
    private AQuery aq;

    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_main);
        aq=new AQuery(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        aq.id(R.id.logoff).clicked(this,"aq_logoff");
    }

    public void aq_logoff(){
        BmobUser.logOut(this);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

}
