package com.yuen.xiuka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.BaseBean;
import com.yuen.xiuka.beans.MYBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private List settingString2 = new ArrayList(Arrays.asList("头像", "昵称", "秀咖号", "性别", "个性签名", "年龄", "星座", "标签", "所在地区", "职业","直播平台"));
    private String key;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        SysExitUtil.activityList.add(this);
        initView();
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mydata = (MYBean.DataBean) intent.getSerializableExtra("data");
        position = intent.getIntExtra("position", -1);
        key = intent.getStringExtra("key");
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("修改" + settingString2.get(position));
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
                finish();
                break;
            case R.id.btn_edit_info_ok:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        final String info = et_input_info.getText().toString().trim();
        if (TextUtils.isEmpty(info)) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        HashMap<String, String> map = new HashMap<>();
        map.put(key, info);
        map.put("uid", SPUtil.getInt("uid")+"");

      //  Toast.makeText(EditUserActivity.this,key+ info+SPUtil.getInt("uid") + "", Toast.LENGTH_LONG).show();
        XUtils.xUtilsPost(URLProvider.SAVE_DATA, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", "----SAVE_DATA-----" + result);
                Gson gson = new Gson();
                BaseBean baseBean = gson.fromJson(result, BaseBean.class);
              //  Toast.makeText(EditUserActivity.this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("key",info);
                setResult(position,intent);
                finish();
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
