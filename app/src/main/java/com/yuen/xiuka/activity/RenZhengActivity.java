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

public class RenZhengActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private EditText et_name;
    private EditText et_idcard;
    private ImageView btn_idcardz;
    private ImageView btn_idcardb;
    private ImageView btn_idcardsc;
    private EditText et_zhibo;
    private EditText et_zhiboroom;
    private EditText et_zhiboid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng);
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
        btn_idcardz = (ImageView) findViewById(R.id.btn_idcardz);
        btn_idcardz.setOnClickListener(this);
        btn_idcardb = (ImageView) findViewById(R.id.btn_idcardb);
        btn_idcardb.setOnClickListener(this);
        btn_idcardsc = (ImageView) findViewById(R.id.btn_idcardsc);
        btn_idcardsc.setOnClickListener(this);
        et_zhibo = (EditText) findViewById(R.id.et_zhibo);
        et_zhibo.setOnClickListener(this);
        et_zhiboroom = (EditText) findViewById(R.id.et_zhiboroom);
        et_zhiboroom.setOnClickListener(this);
        et_zhiboid = (EditText) findViewById(R.id.et_zhiboid);
        et_zhiboid.setOnClickListener(this);
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
            case R.id.btn_idcardz:
                takephoto("idcardz",0);
                break;
            case R.id.btn_idcardb:
                takephoto("idcardb",1);
                break;
            case R.id.btn_idcardsc:
                takephoto("idcardsc",2);
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
                        + "/idcardz.jpg");
                x.image().bind(btn_idcardz,temp.getAbsolutePath());
                break;
            case 1:
               temp = new File(Environment.getExternalStorageDirectory()
                        + "/idcardb.jpg");
                x.image().bind(btn_idcardb,temp.getAbsolutePath());
                break;
            // 取得裁剪后的图片
            case 2:
                temp = new File(Environment.getExternalStorageDirectory()
                        + "/idcardsc.jpg");
                x.image().bind(btn_idcardsc,temp.getAbsolutePath());
                break;
            default:
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

        String zhibo = et_zhibo.getText().toString().trim();
        if (TextUtils.isEmpty(zhibo)) {
            Toast.makeText(this, "zhibo不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String zhiboroom = et_zhiboroom.getText().toString().trim();
        if (TextUtils.isEmpty(zhiboroom)) {
            Toast.makeText(this, "zhiboroom不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String zhiboid = et_zhiboid.getText().toString().trim();
        if (TextUtils.isEmpty(zhiboid)) {
            Toast.makeText(this, "zhiboid不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
