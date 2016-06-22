package com.yuen.xiuka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.MYBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditUserActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private EditText et_input_info;
    private Button btn_edit_info_ok;
    private MYBean.DataBean mydata;

    private List settingString2 = new ArrayList(Arrays.asList("头像", "昵称", "秀咖号", "性别", "个性签名", "年龄", "星座", "标签", "所在地区", "职业"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        initView();
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mydata = (MYBean.DataBean)intent.getSerializableExtra("data");
        int position = intent.getIntExtra("position",-1);
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("修改"+settingString2.get(position));
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        et_input_info = (EditText) findViewById(R.id.et_input_info);
        et_input_info.setOnClickListener(this);
        btn_edit_info_ok = (Button) findViewById(R.id.btn_edit_info_ok);
        btn_edit_info_ok.setOnClickListener(this);
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
            case R.id.btn_edit_info_ok:

                break;
        }
    }

    private void submit() {
        // validate
        String info = et_input_info.getText().toString().trim();
        if (TextUtils.isEmpty(info)) {
            Toast.makeText(this, "info不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
