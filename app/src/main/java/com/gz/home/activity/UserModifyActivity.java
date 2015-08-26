package com.gz.home.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;

public class UserModifyActivity extends BasePageActivity {
    private User user;
    private AQuery aq;

    @Override
    protected void initData() {
        this.user=(User)mBundle.getSerializable(Constant.USER.DATA);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_user_modify);
        aq=new AQuery(this);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text("个人信息");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("返回");
        aq.id(R.id.mdf_name).text(user.getName());
        aq.id(R.id.mdf_avater).image(user.getAvatar());
        aq.id(R.id.mdf_phone).text(user.getUsername());
        aq.id(R.id.mdf_address).text(user.getAddress());
        aq.id(R.id.mdf_detail).text(user.getDetail());
        if(user.getSex()==Constant.USER.MALE){
            aq.id(R.id.mdf_sex).text("男");
        }else {
            aq.id(R.id.mdf_sex).text("女");
        }
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "onBackPressed");
    }

}
