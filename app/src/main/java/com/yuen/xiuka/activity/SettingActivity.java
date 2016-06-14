package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private ListView lv_setting;
    private Button btn_logout;
    private List settingString2 = new ArrayList(Arrays.asList("账号安全", "版权说明", "用户反馈"));
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        lv_setting = (ListView) findViewById(R.id.lv_setting);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        myAdapter = new MyAdapter(settingString2);
        lv_setting.setAdapter(myAdapter);
        lv_setting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        startActivity(AnQuanActivity.class);
                        break;
                    case 1:
                        startActivity(BanQuanActivity.class);
                        break;
                    case 2:
                        startActivity(SettingOption.class);
                        break;


                }
            }
        });
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
            case R.id.btn_logout:

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
        }
    }
}
