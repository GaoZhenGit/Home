package com.gz.home.activity;


import android.widget.CheckBox;
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
    private EditText address_et;
    private CheckBox cmale;
    private CheckBox cfemale;

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
        address_et = (EditText) findViewById(R.id.login_address_et);
        cmale = (CheckBox) findViewById(R.id.login_check_male);
        cfemale = (CheckBox) findViewById(R.id.login_check_female);
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "finish");
        aq.id(R.id.regist_btn).clicked(this, "aq_register");
        aq.id(R.id.login_check_male).clicked(this,"aq_male");
        aq.id(R.id.login_check_female).clicked(this,"aq_female");
    }

    public void aq_male(){
        cmale.setChecked(true);
        cfemale.setChecked(false);
    }
    public void aq_female(){
        cmale.setChecked(false);
        cfemale.setChecked(true);
    }

    public void aq_register() {
        ShowToast("注册中...");
        String phone = phone_et.getText().toString();
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();
        String address =address_et.getText().toString();
        Integer sex;
        if(cmale.isChecked()){
            sex=Constant.USER.MALE;
        } else {
            sex=Constant.USER.FEMALE;
        }
        if (phone == null || phone.length() != 11) {
            ShowToast("请正确填写手机号");
            return;
        } else if (email == null || email.length() == 0) {
            ShowToast("请正确填写邮箱号");
            return;
        } else if (password == null || password.length() < 6) {
            ShowToast("请输入六位以上密码");
            return;
        } else if(address==null||address.length()<10){
            ShowToast("出生地字数不得少于10个");
            return;
        }

        User user = new User();
        user.setUsername(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setSex(sex);
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
