package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.BaseBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;

import java.util.HashMap;

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
            case R.id.btn_setting_opnion_tijiao:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String content = et_setting_opnion_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("content", content);
        map.put("name", SPUtil.getString("name"));

        fankui(map);


    }
    private void fankui(HashMap<String, String> map) {
        XUtils.xUtilsPost(URLProvider.MESSAGE, map, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                BaseBean baseBean = gson.fromJson(result, BaseBean.class);
                Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
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
}
