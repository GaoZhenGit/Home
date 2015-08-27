package com.gz.home.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;

public class EditActivity extends BasePageActivity {
    private AQuery aq;
    private EditText editText;
    private String modify_typt;
    private User user;
    @Override
    protected void initData() {
        modify_typt=mBundle.getString(Constant.USER.MDF_TYPE);
        user=(User)mBundle.getSerializable(Constant.USER.DATA);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_edit);
        editText=(EditText)findViewById(R.id.edittext);
        aq=new AQuery(this);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_right_text).visible().text("确定");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("取消");
        //个性签名页面
        if(modify_typt.equals(Constant.KeyValue.USER_DETAIL_MDF)){
            aq.id(R.id.title_mid_text).text("个性签名修改");
            editText.setText(user.getDetail());
        }
        //名字页面
        if(modify_typt.equals(Constant.KeyValue.USER_NAME_MDF)){
            aq.id(R.id.title_mid_text).text("姓名修改");
            editText.setText("");
            editText.setHint("注意：名字只能修改一次");
        }
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "onBackPressed");
        aq.id(R.id.title_right_btn).clicked(this, "returnData");
    }

    public void returnData() {

        if (TextUtils.isEmpty(editText.getText())) {
            ShowToast("请输入!");
            return;
        }
        Intent result = new Intent();
        result.putExtra(Constant.KeyValue.USER_MODIFI, editText.getText().toString());
        setResult(RESULT_OK, result);
        finish();
    }
}
