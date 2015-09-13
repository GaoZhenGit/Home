package com.gz.home.activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.adapter.MemberAdapter;
import com.gz.home.app.Constant;
import com.gz.home.datamodel.User;
import com.gz.home.utils.NetworkUtil;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class SearchFamliyActivity extends BasePageActivity {
    private AQuery aq;
    private User user;
    private EditText searchEdit;
    private ListView listView;
    private MemberAdapter memberAdapter;
    private List<User> userList;

    @Override
    protected void initData() {
        user= User.readInDb(this);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_search_famliy);
        aq=new AQuery(this);
        searchEdit=(EditText)findViewById(R.id.search_et);
        listView=(ListView)findViewById(R.id.search_list);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
        aq.id(R.id.title_mid_text).text("添加亲友");
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "finish");
        aq.id(R.id.btn_search).clicked(this, "aq_search");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User clickUser=userList.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable(Constant.USER.DATA,clickUser);
                Intent intent=new Intent(SearchFamliyActivity.this,MemberActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void aq_search(){
        String searchKey=searchEdit.getText().toString();
        if(searchKey.matches("[0-9]+")){
            //手机号查询
            NetworkUtil.searchPhone(this, searchKey, new NetworkUtil.UserListListener() {
                @Override
                public void onSuccess(List<User> list) {
                    SearchFamliyActivity.this.userList=list;
                    if(memberAdapter==null){
                        memberAdapter=new MemberAdapter(SearchFamliyActivity.this,list,user);
                        listView.setAdapter(memberAdapter);
                    }else {
                        memberAdapter.setUserList(list);
                        memberAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    ShowToast("查询失败："+s);
                }
            });
        }else{
            //名字查询
            NetworkUtil.searchName(this, searchKey, new NetworkUtil.UserListListener() {
                @Override
                public void onSuccess(List<User> list) {
                    SearchFamliyActivity.this.userList = list;
                    if (memberAdapter == null) {
                        memberAdapter = new MemberAdapter(SearchFamliyActivity.this, list, user);
                        listView.setAdapter(memberAdapter);
                    } else {
                        memberAdapter.setUserList(list);
                        memberAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    ShowToast("查询失败：" + s);
                }
            });
        }
    }

}
