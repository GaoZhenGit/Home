package com.gz.home.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.androidquery.AQuery;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gz.home.R;
import com.gz.home.adapter.TabPagerAdapter;
import com.gz.home.app.HomeApplication;
import com.gz.home.customerview.PagerTabWidget;
import com.gz.home.datamodel.User;
import com.gz.home.fragment.FamilyFragment;
import com.gz.home.fragment.FirstFragment;
import com.gz.home.fragment.TabMeFgm;
import com.gz.home.listener.OnTabSelectedListener;
import com.gz.home.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;


public class MainActivity extends BasePageActivity {
    private PagerTabWidget mTabWidget;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private AQuery aq;

    private FamilyFragment familyFragment;
    private TabMeFgm tabMeFgm;

    private long exitTime=0;

    private User user;

    @Override
    protected void initData() {
        familyFragment=new FamilyFragment();
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
        aq.id(R.id.title_right_btn).clicked(this, "aq_add").clickable(false);
    }

    public void aq_logoff(){
        BmobUser.logOut(this);
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void initTab(){
        mTabWidget=(PagerTabWidget)findViewById(R.id.me_tabwidget);
        mTabWidget.setDividerInvisible();
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_discovery, null));
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_family, null));
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_me, null));

        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new FirstFragment());
        fragmentList.add(familyFragment);
        fragmentList.add(tabMeFgm);

        mPagerAdapter=new TabPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager=(ViewPager)findViewById(R.id.me_viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        mTabWidget.setmViewPager(mViewPager);
        mTabWidget.setmOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
                switch(position){
                    /**调整下方tabwidget的图标和字体颜色随划动而改变
                     * 标题栏右上角按钮的出现和消失
                     */
                    case 0:
                        aq.id(R.id.tab_discovery_img).image(R.drawable.discovery_blue);
                        aq.id(R.id.tab_discovery_text).textColor(getResources().getColor(R.color.color_theme));
                        YoYo.with(Techniques.SlideOutRight).duration(300).playOn(aq.id(R.id.title_right_img).getView());
                        aq.id(R.id.title_right_btn).clickable(false);

                        aq.id(R.id.tab_family_img).image(R.drawable.family_grey);
                        aq.id(R.id.tab_family_text).textColor(getResources().getColor(R.color.light_grey));
                        aq.id(R.id.tab_me_img).image(R.drawable.me_grey);
                        aq.id(R.id.tab_me_text).textColor(getResources().getColor(R.color.light_grey));
                        break;
                    case 1:
                        aq.id(R.id.tab_family_img).image(R.drawable.family_blue);
                        aq.id(R.id.tab_family_text).textColor(getResources().getColor(R.color.color_theme));
                        aq.id(R.id.title_right_img).visible().image(R.drawable.add);//右上角添加按钮出现
                        YoYo.with(Techniques.SlideInRight).duration(300).playOn(aq.id(R.id.title_right_img).getView());
                        aq.id(R.id.title_right_btn).clickable(true);

                        aq.id(R.id.tab_discovery_img).image(R.drawable.discovery_grey);
                        aq.id(R.id.tab_discovery_text).textColor(getResources().getColor(R.color.light_grey));
                        aq.id(R.id.tab_me_img).image(R.drawable.me_grey);
                        aq.id(R.id.tab_me_text).textColor(getResources().getColor(R.color.light_grey));
                        break;
                    case 2:
                        aq.id(R.id.tab_me_img).image(R.drawable.me_blue);
                        aq.id(R.id.tab_me_text).textColor(getResources().getColor(R.color.color_theme));
                        YoYo.with(Techniques.SlideOutRight).duration(300).playOn(aq.id(R.id.title_right_img).getView());
                        aq.id(R.id.title_right_btn).clickable(false);

                        aq.id(R.id.tab_discovery_img).image(R.drawable.discovery_grey);
                        aq.id(R.id.tab_discovery_text).textColor(getResources().getColor(R.color.light_grey));
                        aq.id(R.id.tab_family_img).image(R.drawable.family_grey);
                        aq.id(R.id.tab_family_text).textColor(getResources().getColor(R.color.light_grey));
                        break;
                }
            }
        });
    }

    //这个本来不应该在这里的，但是，titlebar是这个整个Activity的，我现在还转移不了
    public void aq_add(){
        //弹出添加按钮
        View addView =getLayoutInflater().inflate(R.layout.pop_add,null);
        final PopupWindow popupWindow = new PopupWindow(addView, OtherUtils.dip2px(this,130), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.blank));
        popupWindow.showAsDropDown(findViewById(R.id.title_right_btn));
        popupWindow.update();
        YoYo.with(Techniques.BounceInDown).duration(400).playOn(addView);

        //设置按键监听
        //查找添加亲友
        View searchBtn=addView.findViewById(R.id.btn_search_add);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchFamliyActivity.class));
                popupWindow.dismiss();
            }
        });
        //扫二维码添加亲友
        View codeBtn=addView.findViewById(R.id.btn_code_add);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
