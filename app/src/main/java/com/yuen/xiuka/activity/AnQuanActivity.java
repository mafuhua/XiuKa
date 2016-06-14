package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuen.xiuka.R;

public class AnQuanActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private ImageView iv_user_icon;
    private TextView tv_anquandengji;
    private ImageView iv_shop_item_image;
    private RelativeLayout rl_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_quan);
        initView();
    }

    @Override
    public void initView() {

        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setOnClickListener(this);
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        iv_user_icon = (ImageView) findViewById(R.id.iv_user_icon);
        iv_user_icon.setOnClickListener(this);
        tv_anquandengji = (TextView) findViewById(R.id.tv_anquandengji);
        tv_anquandengji.setOnClickListener(this);
        iv_shop_item_image = (ImageView) findViewById(R.id.iv_shop_item_image);
        iv_shop_item_image.setOnClickListener(this);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_phone.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fanhui:
                finish();
                break;
            case R.id.btn_sousuo:

                break;
            case R.id.rl_phone:
                startActivity(BangDingTelActivity.class);
                break;
            case R.id.btn_tijiao:

                break;
        }
    }
}
