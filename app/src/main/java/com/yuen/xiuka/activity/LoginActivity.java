package com.yuen.xiuka.activity;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.yuen.xiuka.beans.FENSIBean;
import com.yuen.xiuka.beans.LOGINBean;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private List<FENSIBean.DataBean> fensiBeanData;
    private DbManager db;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mShareAPI = UMShareAPI.get(this);
        DbManager.DaoConfig daoConfig = XUtils.getDaoConfig();
        db = x.getDb(daoConfig);
        initView();
    }

    @Override
    public void initView() {
        if (!SPUtil.getString("tel").isEmpty()) {


            if (time()) {
                startActivity(Close.class);
                finish();
            } else if (!SPUtil.getString("close").isEmpty()) {
                startActivity(Close.class);
                finish();
            } else {
                startActivity(MainActivity.class);
                finish();
            }

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

    public static Long dateToLong(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates.getTime();
    }
    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        SHARE_MEDIA platform;
        switch (v.getId()) {
            case R.id.btn_get_yzm:
                time = new TimeCount(60000, 1000);

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

                if (loginBean.getCode().equals("0")) {
                    SPUtil.saveInt("uid", loginBean.getUid());
                    SPUtil.saveString("tel", loginBean.getTel());
                    SPUtil.saveString("token", loginBean.getToken());
                    getList(URLProvider.GUANZHU);

                } else {
                    ToastUtil.toastShortShow(context, "登录失败");
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

    private void getList(String url) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        XUtils.xUtilsPost(url, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", result);
                Gson gson = new Gson();
                FENSIBean fensiBean = gson.fromJson(result, FENSIBean.class);
                fensiBeanData = fensiBean.getData();
                ToastUtil.toastShortShow(context, "登录成功");
                if (fensiBeanData == null) {
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    for (int i = 0; i < fensiBeanData.size(); i++) {
                        PersonTable person = new PersonTable();
                        person.setId(Integer.parseInt(fensiBeanData.get(i).getG_uid()));
                        person.setName(fensiBeanData.get(i).getName());
                        person.setImg(URLProvider.BaseImgUrl + fensiBeanData.get(i).getImage());
                        try {
                            db.saveOrUpdate(person);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    }
                    startActivity(MainActivity.class);
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
        et_tel.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_tel.getWindowToken(), 0);
        time.start();// 开始计时
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
            Toast.makeText(context, "正在登陆", Toast.LENGTH_SHORT).show();
            postlogin(tel);
        } else {
            ToastUtil.toastShortShow(context, "验证码错误");
        }
        // TODO validate success, do something


    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btn_get_yzm.setText("获取验证码");
            btn_get_yzm.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btn_get_yzm.setClickable(false);//防止重复点击
            btn_get_yzm.setText(millisUntilFinished / 1000 + "秒");
        }
    }
    public boolean time(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);

        Long aLong = dateToLong("2016-09-20");
        Long aLong2 = dateToLong(dateString);
        if (aLong2>aLong) {
            return true;
        }else {
            return false;
        }

    }
}
