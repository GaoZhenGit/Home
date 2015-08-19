package com.gz.home.activity;


import android.widget.EditText;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;

import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BasePageActivity {
    private AQuery aq;
    private EditText phone_et;
    private EditText email_et;
    private EditText password_et;

    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_register);
        aq = new AQuery(this);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("返回");
        aq.id(R.id.title_mid_text).text("注册用户");
        phone_et = (EditText) findViewById(R.id.login_phone_et);
        email_et = (EditText) findViewById(R.id.login_email_et);
        password_et = (EditText) findViewById(R.id.login_password_et);
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "finish");
        aq.id(R.id.regist_btn).clicked(this, "aq_register");
    }

    public void aq_register() {
        ShowToast("注册中...");
        String phone = phone_et.getText().toString();
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();
        if (phone == null || phone.length() != 11) {
            ShowToast("请正确填写手机号");
            return;
        } else if (email == null || email.length() == 0) {
            ShowToast("请正确填写邮箱号");
            return;
        } else if (password == null || password.length() < 6) {
            ShowToast("请输入六位以上密码");
            return;
        }

        User user = new User();
        user.setUsername(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setSex(Constant.USER.MALE);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ShowToast("注册成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ShowToast("注册失败：" + s);
            }
        });
    }
}
