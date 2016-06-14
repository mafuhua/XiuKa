package com.yuen.xiuka;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yuen.baselib.utils.VerifyUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_tel;
    private EditText et_yzm;
    private Button btn_get_yzm;
    private Button btn_login;
    private Button btn_qq;
    private Button btn_weixin;
    private Button btn_weobo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    public void initView() {

        et_tel = (EditText) findViewById(R.id.et_tel);
        et_tel.setOnClickListener(this);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        et_yzm.setOnClickListener(this);
        btn_get_yzm = (Button) findViewById(R.id.btn_get_yzm);
        btn_get_yzm.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_qq = (Button) findViewById(R.id.btn_qq);
        btn_qq.setOnClickListener(this);
        btn_weixin = (Button) findViewById(R.id.btn_weixin);
        btn_weixin.setOnClickListener(this);
        btn_weobo = (Button) findViewById(R.id.btn_weobo);
        btn_weobo.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_yzm:

                break;
            case R.id.btn_login:
                startActivity(MainActivity.class);
                break;
            case R.id.btn_qq:

                break;
            case R.id.btn_weixin:

                break;
            case R.id.btn_weobo:

                break;
        }
    }

    private void submit() {
        // validate
        String tel = et_tel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!VerifyUtil.isMobileNO(tel)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        String yzm = et_yzm.getText().toString().trim();
        if (TextUtils.isEmpty(yzm)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
