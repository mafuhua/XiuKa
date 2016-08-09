package com.yuen.xiuka.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.ImgBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GongHuiRenZhengActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiaoo;
    private EditText et_name;
    private EditText et_idcard;
    private ImageView btn_zhengmian;
    private ImageView btn_fanmian;
    private long currentTimeMillis;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_hui_ren_zheng);
        SysExitUtil.activityList.add(this);
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
        btn_tijiaoo = (Button) findViewById(R.id.btn_tijiaoo);
        btn_tijiaoo.setOnClickListener(this);
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
        currentTimeMillis = System.currentTimeMillis();
        switch (v.getId()) {
            case R.id.btn_fanhui:
                finish();
                break;
            case R.id.btn_sousuo:

                break;
            case R.id.btn_jia:

                break;
            case R.id.btn_tijiaoo:
                submit();
                break;
            case R.id.btn_zhengmian:
                takephoto(currentTimeMillis+"",0);
                break;
            case R.id.btn_fanmian:
                takephoto(currentTimeMillis+"",1);
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
                        + "/"+currentTimeMillis+".jpg");
                takephotos.put(0, temp.getAbsolutePath());
                Compresspic(Environment.getExternalStorageDirectory()
                        + "/"+currentTimeMillis+"core.jpg", temp.getAbsolutePath());
                renzhengimgs.add(Environment.getExternalStorageDirectory()
                        + "/"+currentTimeMillis+"core.jpg");
                x.image().bind(btn_zhengmian,temp.getAbsolutePath());
                break;
            case 1:
                temp = new File(Environment.getExternalStorageDirectory()
                        + "/"+currentTimeMillis+".jpg");
                takephotos.put(0, temp.getAbsolutePath());
                Compresspic(Environment.getExternalStorageDirectory()
                        + "/"+currentTimeMillis+"core.jpg", temp.getAbsolutePath());
                renzhengimgs.add(Environment.getExternalStorageDirectory()
                        + "/"+currentTimeMillis+"core.jpg");
                x.image().bind(btn_fanmian,temp.getAbsolutePath());
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "传媒公司名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String idcard = et_idcard.getText().toString().trim();
        if (TextUtils.isEmpty(idcard)) {
            Toast.makeText(this, "组织机构代码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (renzhengimgs.size()<2)return;
        addrenzheng(name, idcard);
        // TODO validate success, do something


    }
    public void sendComPic() {
        for (int i = 0; i < renzhengimgs.size(); i++) {
            sendimg(renzhengimgs.get(i));
        }

    }
    public void Compresspic(final String path, final String old) {
        File file = new File(old);
        if (!file.exists())return;
        new Thread(new Runnable() {//开启多线程进行压缩处理
            private int options;

            @Override
            public void run() {
                // TODO Auto-generated method stub
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(old, opts);
                Log.d("mafuhua", "bitmap.getByteCount():" + bitmap.getByteCount() / 1024);
                options = 80;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                //质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
                while (baos.toByteArray().length / 1024 > 200) {//循环判断如果压缩后图片是否大于60kb,大于继续压缩
                    baos.reset();//重置baos即让下一次的写入覆盖之前的内容
                    options -= 10;//图片质量每次减少10
                    if (options < 0) options = 0;//如果图片质量小于10，则将图片的质量压缩到最小值
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
                    if (options == 0) break;//如果图片的质量已降到最低则，不再进行压缩
                }
                try {
                    FileOutputStream fos = new FileOutputStream(new File(path));//将压缩后的图片保存的本地上指定路径中
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();

                    Log.e("图爱散股", path);
                    File file = new File(path);// path为压缩后的图片路径，将这个新生成的file申明为成员变量，后续会把这个file对象上传服务端，后端自动识别
                    Log.d("mafuhua", "file.length()/1024:" + (file.length() / 1024));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendimg(String path) {

        AsyncHttpClient client = new AsyncHttpClient();
        com.loopj.android.http.RequestParams rp = new com.loopj.android.http.RequestParams();

        File file = new File(path);
        //  Log.d("mafuhua", path + "**************");
        try {
            rp.add("id", resultid);
            rp.put("img", file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        client.post(URLProvider.ADD_JG_RENZHEN_IMG, rp, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                flag += 1;
                if (flag == renzhengimgs.size()) {
                    if (mypDialog.isShowing()) {
                        mypDialog.dismiss();
                    }
                    Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                if (mypDialog.isShowing()) {
                    mypDialog.dismiss();
                }
            }


        });
    }
    private void addrenzheng(String name, String idcard) {
        addrenzhengimg();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("name", name);
        map.put("daima", idcard);
        XUtils.xUtilsPost(URLProvider.ADD_JG_RENZHEN, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhuax", "result-----" + result);
                Gson gson = new Gson();
                ImgBean imgBean = gson.fromJson(result, ImgBean.class);

                if (imgBean.getCode().equals("0")) {
                    resultid = imgBean.getId() + "";
                    sendComPic();
                } else {
                    Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                    if (mypDialog.isShowing()) {
                        mypDialog.dismiss();
                    }
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
    private HashMap<Integer, String> takephotos = new HashMap<>();
    private ProgressDialog mypDialog;
    private String resultid;
    private List<String> renzhengimgs = new ArrayList<>();
    private void addrenzhengimg() {
        mypDialog = new ProgressDialog(context);
        //实例化
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置进度条风格，风格为圆形，旋转的
        //设置ProgressDialog 标题
        mypDialog.setMessage("正在提交");
        //设置ProgressDialog 提示信息
        mypDialog.setIndeterminate(false);
        //设置ProgressDialog 的进度条是否不明确
        mypDialog.setCancelable(false);
        //设置ProgressDialog 是否可以按退回按键取消
        mypDialog.setCanceledOnTouchOutside(true);
        mypDialog.show();
        //让ProgressDialog显示

    }
}
