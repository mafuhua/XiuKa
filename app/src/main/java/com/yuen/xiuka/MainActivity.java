package com.yuen.xiuka;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.fragment.FragmentFractory;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FragmentManager supportFragmentManager;
    private FrameLayout fl_home_content;
    private RadioButton rb_home_faxian;
    private RadioButton rb_home_xiaoxi;
    private RadioButton rb_home_fabu;
    private RadioButton rb_home_xiuquan;
    private RadioButton rb_home_wode;
    private RadioGroup rg_home;
    private Fragment faxianFragment;
    private Fragment xiaoxiFragment;
    private Fragment xiuquanFragment;
    private Fragment woDeFragment;
    private Fragment currentFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d("mafuhua", "savedInstanceState----------:" + savedInstanceState);
            savedInstanceState = null;
            if (savedInstanceState == null) {
                Log.d("mafuhua", "savedInstanceState**********----------:" + savedInstanceState);
            }
        }
        super.onCreate(savedInstanceState);
        SysExitUtil.activityList.add(this);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void initView() {
        fl_home_content = (FrameLayout) findViewById(R.id.fl_home_content);
        fl_home_content.setOnClickListener(this);
        rb_home_faxian = (RadioButton) findViewById(R.id.rb_home_faxian);
        rb_home_faxian.setOnClickListener(this);
        rb_home_xiaoxi = (RadioButton) findViewById(R.id.rb_home_xiaoxi);
        rb_home_xiaoxi.setOnClickListener(this);
        rb_home_fabu = (RadioButton) findViewById(R.id.rb_home_fabu);
        rb_home_fabu.setOnClickListener(this);
        rb_home_xiuquan = (RadioButton) findViewById(R.id.rb_home_xiuquan);
        rb_home_xiuquan.setOnClickListener(this);
        rb_home_wode = (RadioButton) findViewById(R.id.rb_home_wode);
        rb_home_wode.setOnClickListener(this);
        rg_home = (RadioGroup) findViewById(R.id.rg_home);
        rg_home.setOnClickListener(this);
        Drawable drawable = getResources().getDrawable(R.drawable.faxian);
        int dp = MyUtils.dip2px(context, 30);
        drawable.setBounds(0, 0, dp, dp);
        rb_home_faxian.setCompoundDrawables(null, drawable, null, null);
        Drawable drawable1 = getResources().getDrawable(R.drawable.xiaoxi);
        drawable1.setBounds(0, 0, dp, dp);
        rb_home_xiaoxi.setCompoundDrawables(null, drawable1, null, null);
        Drawable drawable2 = getResources().getDrawable(R.drawable.xiuquan);
        drawable2.setBounds(0, 0, dp, dp);
        rb_home_xiuquan.setCompoundDrawables(null, drawable2, null, null);
        Drawable drawable3 = getResources().getDrawable(R.drawable.wode);
        drawable3.setBounds(0, 0, dp, dp);
        rb_home_wode.setCompoundDrawables(null, drawable3, null, null);
        Drawable drawable4 = getResources().getDrawable(R.drawable.fabu);
        drawable4.setBounds(0, 0, dp, dp);
        rb_home_fabu.setCompoundDrawables(null, drawable4, null, null);
        rg_home.check(R.id.rb_home_faxian);
        supportFragmentManager = getSupportFragmentManager();
        faxianFragment = FragmentFractory.getInstance().createFragment(0);
        xiaoxiFragment = FragmentFractory.getInstance().createFragment(1);
        xiuquanFragment = FragmentFractory.getInstance().createFragment(2);
        woDeFragment = FragmentFractory.getInstance().createFragment(3);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_home_content, faxianFragment, "faxianFragment")
                .add(R.id.fl_home_content, xiaoxiFragment, "xiaoxiFragment").hide(xiaoxiFragment)
                .add(R.id.fl_home_content, xiuquanFragment, "xiuquanFragment").hide(xiuquanFragment)
                .add(R.id.fl_home_content, woDeFragment, "woDeFragment").hide(woDeFragment)
                .show(faxianFragment)
                .commit();
        currentFragment = faxianFragment;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        transaction = supportFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.rb_home_faxian:
                if (currentFragment != faxianFragment) {
                    switchContent(currentFragment, faxianFragment, "首页", View.GONE);
                }
                break;
            case R.id.rb_home_xiaoxi:
                switchContent(currentFragment, xiaoxiFragment, "快递", View.GONE);
                break;
            case R.id.rb_home_xiuquan:
                switchContent(currentFragment, xiuquanFragment, "购物车", View.VISIBLE);
                break;
            case R.id.rb_home_wode:
                switchContent(currentFragment, woDeFragment, "个人中心", View.GONE);
                break;
        }
    }

    public void switchContent(Fragment from, Fragment to, String title, int gone) {
        currentFragment = to;
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.fl_home_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }
}
