package com.gz.home.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.adapter.TabPagerAdapter;
import com.gz.home.app.Constant;
import com.gz.home.app.HomeApplication;
import com.gz.home.customerview.PagerTabWidget;
import com.gz.home.datamodel.User;
import com.gz.home.fragment.FirstFragment;
import com.gz.home.fragment.TabMeFgm;
import com.gz.home.listener.OnTabSelectedListener;
import com.gz.home.utils.LogUtil;
import com.gz.home.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;


public class MainActivity extends BasePageActivity {
    private PagerTabWidget mTabWidget;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private AQuery aq;

    private TabMeFgm tabMeFgm;
    private long exitTime=0;

    private User user;

    @Override
    protected void initData() {
        tabMeFgm=new TabMeFgm();
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_main);
        aq=new AQuery(this);
    }

    @Override
    protected void initView() {
        initTab();
        aq.id(R.id.title_mid_text).text("家族网");

    }

    @Override
    protected void setListener() {
//        aq.id(R.id.logoff).clicked(this,"aq_logoff");
    }

    public void aq_logoff(){
        BmobUser.logOut(this);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    private void initTab(){
        mTabWidget=(PagerTabWidget)findViewById(R.id.me_tabwidget);
        mTabWidget.setDividerInvisible();
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_me, null));
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_me, null));
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_me, null));

        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new FirstFragment());
        fragmentList.add(new FirstFragment());
        fragmentList.add(tabMeFgm);

        mPagerAdapter=new TabPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager=(ViewPager)findViewById(R.id.me_viewpager);
        mViewPager.setAdapter(mPagerAdapter);

        mTabWidget.setmViewPager(mViewPager);
        mTabWidget.setmOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
                switch(position){
                    case 0:
                        aq.id(R.id.tab_me_img).image(R.drawable.me_grey);
                        break;
                    case 1:
                        aq.id(R.id.tab_me_img).image(R.drawable.me_grey);
                        break;
                    case 2:
                        aq.id(R.id.tab_me_img).image(R.drawable.me_blue);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        if ((System.currentTimeMillis() - exitTime) > 10000) {
            ShowToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            ((HomeApplication)getApplication()).exit();
        }
    }

}
