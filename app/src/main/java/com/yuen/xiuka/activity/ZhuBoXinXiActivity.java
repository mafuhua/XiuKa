package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZhuBoXinXiActivity extends BaseActivity implements View.OnClickListener {
    private List settingString2 = new ArrayList(Arrays.asList("昵称", "秀咖号", "性别", "个性签名", "年龄", "星座", "标签", "所在地区", "职业"));
    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private ImageView iv_user_icon;
    private TextView tv_user_name;
    private RelativeLayout rl_user_icon;
    private ListView lv_ziliao;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_bo_xin_xi);
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
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_name.setOnClickListener(this);
        rl_user_icon = (RelativeLayout) findViewById(R.id.rl_user_icon);
        rl_user_icon.setOnClickListener(this);
        lv_ziliao = (ListView) findViewById(R.id.lv_ziliao);
        myAdapter = new MyAdapter(settingString2);
        lv_ziliao.setAdapter(myAdapter);
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
            case R.id.btn_jia:

                break;
            case R.id.btn_tijiao:

                break;
        }
    }

    class MyAdapter extends DefaultAdapter {
        public MyAdapter(List datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }
    }

    public class ViewHolder extends BaseHolder<String> {
        public TextView tvshopmanagerleft;
        public TextView tvshopmanagerright;
        public ImageView ivShopItemImage;

        @Override
        public View initView() {
            View root = View.inflate(context, R.layout.layout_zilia_xinxi_item, null);
            tvshopmanagerleft = (TextView) root.findViewById(R.id.tv_shop_manager_left);
            ivShopItemImage = (ImageView) root.findViewById(R.id.iv_shop_item_image);
            tvshopmanagerright = (TextView) root.findViewById(R.id.tv_shop_manager_right);
            return root;
        }

        @Override
        public void refreshView(String data, int position) {
            tvshopmanagerleft.setText(data);
            tvshopmanagerright.setText(data);
        }
    }
}
