package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuanZhuListActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private ListView lv_guanzhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu_list);
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
        lv_guanzhu = (ListView) findViewById(R.id.lv_guanzhu);
        lv_guanzhu.setAdapter(new MyAdapter(wodeItemDec));
    }

    @Override
    public void loadData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fanhui:

                break;
            case R.id.btn_sousuo:

                break;
            case R.id.btn_jia:

                break;
            case R.id.btn_tijiao:

                break;
        }
    }
    private List<String> wodeItemDec = new ArrayList<String>(Arrays.asList("主播认证", "传媒公司/工会认证",
            "主播信息修改", "消息提醒", "设置", "消息提醒", "设置", "消息提醒", "设置", "消息提醒", "设置"));
    class MyAdapter extends DefaultAdapter {
        public MyAdapter(List datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new WoDeHolder();
        }
    }

    class WoDeHolder extends BaseHolder<String> {
        public ImageView ivusericon;
        public TextView tvusername;
        public TextView tvusercontent;
        public CheckBox cbguanzhu;

        @Override
        public View initView() {
            View root = View.inflate(GuanZhuListActivity.this, R.layout.layout_guanzhu_item, null);
            ivusericon = (ImageView) root.findViewById(R.id.iv_user_icon);
            tvusername = (TextView) root.findViewById(R.id.tv_user_name);
            tvusercontent = (TextView) root.findViewById(R.id.tv_user_content);
            cbguanzhu = (CheckBox) root.findViewById(R.id.cb_guanzhu);
            return root;
        }

        @Override
        public void refreshView(String data, int position) {

        }
    }
}
