package com.yuen.xiuka.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuen.xiuka.R;

import org.xutils.x;

import java.io.File;

public class GongHuiRenZhengActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private EditText et_name;
    private EditText et_idcard;
    private ImageView btn_zhengmian;
    private ImageView btn_fanmian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_hui_ren_zheng);
        initView();
    }

    @Override
    public void initView() {

        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("认证");
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setOnClickListener(this);
        et_idcard = (EditText) findViewById(R.id.et_idcard);
        et_idcard.setOnClickListener(this);
        btn_zhengmian = (ImageView) findViewById(R.id.btn_zhengmian);
        btn_zhengmian.setOnClickListener(this);
        btn_fanmian = (ImageView) findViewById(R.id.btn_fanmian);
        btn_fanmian.setOnClickListener(this);
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
            case R.id.btn_zhengmian:
                takephoto("zhengmian",0);
                break;
            case R.id.btn_fanmian:
                takephoto("fanmian",1);
                break;
        }
    }
    private void takephoto(String name,int code) {
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        //下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                .fromFile(new File(Environment
                        .getExternalStorageDirectory(),
                        name +".jpg")));
        startActivityForResult(intent, code);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File temp;
        switch (requestCode) {
            case 0:
                temp = new File(Environment.getExternalStorageDirectory()
                        + "/zhengmian.jpg");
                x.image().bind(btn_zhengmian,temp.getAbsolutePath());
                break;
            case 1:
                temp = new File(Environment.getExternalStorageDirectory()
                        + "/fanmian.jpg");

                x.image().bind(btn_fanmian,temp.getAbsolutePath());
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String idcard = et_idcard.getText().toString().trim();
        if (TextUtils.isEmpty(idcard)) {
            Toast.makeText(this, "idcard不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
