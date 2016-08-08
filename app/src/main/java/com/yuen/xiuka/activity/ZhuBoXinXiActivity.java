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
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZhuBoXinXiActivity extends BaseActivity implements View.OnClickListener{

    private List settingString2 = new ArrayList(Arrays.asList("头像", "昵称", "性别", "个性签名", "标签", "年龄", "所在地区", "职业", "直播平台", "直播预告"));
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
        SysExitUtil.activityList.add(this);
    }

    @Override
    public void initView() {

        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("信息修改");
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
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
        public ImageView iv_user_icon;
        public View line;

        @Override
        public View initView() {
            View root = View.inflate(context, R.layout.layout_zilia_xinxi_item, null);
            tvshopmanagerleft = (TextView) root.findViewById(R.id.tv_shop_manager_left);
            line = root.findViewById(R.id.line);
            ivShopItemImage = (ImageView) root.findViewById(R.id.iv_shop_item_image);
            iv_user_icon = (ImageView) root.findViewById(R.id.iv_user_icon);
            tvshopmanagerright = (TextView) root.findViewById(R.id.tv_shop_manager_right);
            return root;
        }

        @Override
        public void refreshView(String data, int position) {
            if (position == 0) {
                iv_user_icon.setBackgroundResource(R.drawable.touxiang);
            } else {
                tvshopmanagerright.setText(data);
            }
            if (position == 8 || position == 1|| position == 0|| position == 5) {
                line.setVisibility(View.VISIBLE);
            } else {
                line.setVisibility(View.GONE);
            }
            tvshopmanagerleft.setText(data);

        }
    }
}
