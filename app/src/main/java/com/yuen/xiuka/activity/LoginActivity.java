package com.yuen.xiuka.activity;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.ToastUtil;
import com.yuen.baselib.utils.VerifyUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.DUANXINBean;
import com.yuen.xiuka.beans.LOGINBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private UMShareAPI mShareAPI;
    private EditText et_tel;
    private EditText et_yzm;
    private Button btn_get_yzm;
    private Button btn_login;
    private Button btn_qq;
    private Button btn_weixin;
    private Button btn_weobo;
    private Context context;
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    private int duanxinBeanYan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mShareAPI = UMShareAPI.get(this);
        initView();
    }

    @Override
    public void initView() {
        if (!SPUtil.getString("tel").isEmpty()){
            startActivity(MainActivity.class);
            finish();
        }
        context = this;
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
        SHARE_MEDIA platform;
        switch (v.getId()) {
            case R.id.btn_get_yzm:
                submit();

                break;
            case R.id.btn_login:
              login();

                break;
            case R.id.btn_qq:
                platform = SHARE_MEDIA.QQ;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.btn_weixin:
                platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.btn_weobo:
                platform = SHARE_MEDIA.SINA;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);


                break;
        }
    }

    public void getYzm(String tel) {
        HashMap<String, String> map = new HashMap<>();
        map.put("tel", tel);
        XUtils.xUtilsPost(URLProvider.DUANXIN, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", "----DUANXIN------" + result);
                Gson gson = new Gson();
                DUANXINBean duanxinBean = gson.fromJson(result, DUANXINBean.class);
                ToastUtil.toastShortShow(context, "获得验证码" + duanxinBean.getMsg());
                duanxinBeanYan = duanxinBean.getYan();
                Toast.makeText(context, "Yan:" + duanxinBeanYan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public void postlogin(String tel) {
        HashMap<String, String> map = new HashMap<>();
        map.put("tel", tel);
        XUtils.xUtilsPost(URLProvider.LOGIN, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", "----LOGIN------" + result);

                Gson gson = new Gson();
                LOGINBean loginBean = gson.fromJson(result, LOGINBean.class);
                ToastUtil.toastShortShow(context, "登录" + loginBean.getMsg());
                if (loginBean.getCode().equals("0")){
                    startActivity(MainActivity.class);
                    SPUtil.saveInt("uid",loginBean.getUid());
                    SPUtil.saveString("tel",loginBean.getTel());
                    finish();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

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
        getYzm(tel);

        // TODO validate success, do something


    }

    private void login() {
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
        if (yzm.equals(duanxinBeanYan + "")) {
            postlogin(tel);
        } else {
            ToastUtil.toastShortShow(context, "验证码错误");
        }
        // TODO validate success, do something


    }
}
