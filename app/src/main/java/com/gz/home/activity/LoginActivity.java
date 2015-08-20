package com.gz.home.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.datamodel.User;

import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BasePageActivity {
    private AQuery aq;
    private EditText name_et;
    private EditText password_et;
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
        name_et=(EditText)findViewById(R.id.et_username);
        password_et=(EditText)findViewById(R.id.et_password);
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "finish");
        aq.id(R.id.login_btn).clicked(this,"aq_login");
        aq.id(R.id.btn_register).clicked(this, "aq_register");
    }
    public void aq_register(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void aq_login(){
        String phone=name_et.getText().toString();
        String password=password_et.getText().toString();
        if (phone == null || phone.length() != 11) {
            ShowToast("请正确填写手机号");
            return;
        } else if (password == null || password.length() < 6) {
            ShowToast("请输入六位以上密码");
            return;
        }
        ShowToast("登录中...");
        User user=new User();
        user.setUsername(phone);
        user.setPassword(password);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ShowToast("登录成功");
                finish();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(int i, String s) {
                ShowToast("登录失败："+s);
            }
        });
    }
}

