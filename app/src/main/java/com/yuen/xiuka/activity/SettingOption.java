package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuen.xiuka.R;

public class SettingOption extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private EditText et_setting_opnion_content;
    private Button btn_setting_opnion_tijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_opinion_edit);
        initView();
    }

    public void initView() {
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        et_setting_opnion_content = (EditText) findViewById(R.id.et_setting_opnion_content);
        btn_setting_opnion_tijiao = (Button) findViewById(R.id.btn_setting_opnion_tijiao);
        tv_titlecontent.setText("用户反馈");
        btn_fanhui.setOnClickListener(this);
        btn_sousuo.setOnClickListener(this);
        btn_jia.setOnClickListener(this);
        btn_tijiao.setOnClickListener(this);
        btn_setting_opnion_tijiao.setOnClickListener(this);
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
            case R.id.btn_setting_opnion_tijiao:

                break;
        }
    }

    private void submit() {
        // validate
        String content = et_setting_opnion_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "content不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
