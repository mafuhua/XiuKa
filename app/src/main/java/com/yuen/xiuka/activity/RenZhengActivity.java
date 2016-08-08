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
import com.yuen.baselib.utils.VerifyUtil;
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

public class RenZhengActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiaoo;
    private EditText et_name;
    private EditText et_idcard;
    private ImageView btn_idcardz;
    private ImageView btn_idcardb;
    private ImageView btn_idcardsc;
    private EditText et_zhibo;
    private EditText et_zhiboroom;
    private EditText et_zhiboid;
    private HashMap<Integer, String> takephotos = new HashMap<>();
    private ProgressDialog mypDialog;
    private String resultid;
    private List<String> renzhengimgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng);
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
        btn_tijiaoo = (Button) findViewById(R.id.btn_tijiaoo);
        btn_tijiaoo.setOnClickListener(this);
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
            case R.id.btn_tijiaoo:
                submit();
                break;
            case R.id.btn_idcardz:

                takephoto("idcardz", 0);
                break;
            case R.id.btn_idcardb:
                takephoto("idcardb", 1);
                break;
            case R.id.btn_idcardsc:
                takephoto("idcardsc", 2);
                break;
        }
    }

    private void takephoto(String name, int code) {
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        //下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                .fromFile(new File(Environment
                        .getExternalStorageDirectory(),
                        name + ".jpg")));
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File temp;
        switch (requestCode) {
            case 0:
                temp = new File(Environment.getExternalStorageDirectory()
                        + "/idcardz.jpg");
                takephotos.put(0, temp.getAbsolutePath());
                Compresspic(Environment.getExternalStorageDirectory()
                        + "/idcardz01.jpg", temp.getAbsolutePath());
                renzhengimgs.add(Environment.getExternalStorageDirectory()
                        + "/idcardz01.jpg");
                x.image().bind(btn_idcardz, temp.getAbsolutePath());
                break;
            case 1:
                temp = new File(Environment.getExternalStorageDirectory()
                        + "/idcardb.jpg");
                takephotos.put(1, temp.getAbsolutePath());
                Compresspic(Environment.getExternalStorageDirectory()
                        + "/idcardb01.jpg", temp.getAbsolutePath());
                renzhengimgs.add(Environment.getExternalStorageDirectory()
                        + "/idcardb01.jpg");
                x.image().bind(btn_idcardb, temp.getAbsolutePath());
                break;
            // 取得裁剪后的图片
            case 2:
                temp = new File(Environment.getExternalStorageDirectory()
                        + "/idcardsc.jpg");
                takephotos.put(2, temp.getAbsolutePath());
                Compresspic(Environment.getExternalStorageDirectory()
                        + "/idcardsc01.jpg", temp.getAbsolutePath());
                renzhengimgs.add(Environment.getExternalStorageDirectory()
                        + "/idcardsc01.jpg");
                x.image().bind(btn_idcardsc, temp.getAbsolutePath());
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String idcard = et_idcard.getText().toString().trim();
        if (TextUtils.isEmpty(idcard)) {
            Toast.makeText(this, "身份证号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!VerifyUtil.isIDCard(idcard)) {
            Toast.makeText(this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
            return;
        }

        String zhibo = et_zhibo.getText().toString().trim();
        if (TextUtils.isEmpty(zhibo)) {
            Toast.makeText(this, "直播平台不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String zhiboroom = et_zhiboroom.getText().toString().trim();
        if (TextUtils.isEmpty(zhiboroom)) {
            Toast.makeText(this, "直播房间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String zhiboid = et_zhiboid.getText().toString().trim();
        if (TextUtils.isEmpty(zhiboid)) {
            Toast.makeText(this, "直播ID不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        addrenzheng(name, idcard, zhibo, zhiboroom, zhiboid);
        // TODO validate success, do something


    }

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
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
        //让ProgressDialog显示

    }

    public void sendComPic() {
        for (int i = 0; i < renzhengimgs.size(); i++) {
            sendimg(renzhengimgs.get(i));
        }
        if (mypDialog.isShowing()) {
            mypDialog.dismiss();
        }
    }

    private void addrenzheng(String name, String idcard, String zhibo, String zhiboroom, String zhiboid) {
        addrenzhengimg();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("name", name);
        map.put("card_id", idcard);
        map.put("zhibo", zhibo);
        map.put("zhibo_id", zhiboid);
        map.put("zhibo_fang", zhiboroom);
        XUtils.xUtilsPost(URLProvider.ADD_RENZHEN, map, new Callback.CommonCallback<String>() {
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

    public void Compresspic(final String path, final String old) {
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


        client.post(URLProvider.ADD_RENZHEN_IMG, rp, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                // Log.d("mafuhua", "responseBody" + response);
                Gson gson = new Gson();
             /*   IconResultBean iconResultBean = gson.fromJson(response, IconResultBean.class);
                if (iconResultBean.getStatus().equals("0")) {
                    Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                    getUserIcon(ContactURL.SHOP_STORE_TOU + MainActivity.userid);
                } else {
                    Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                }*/
                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                if (mypDialog.isShowing()) {
                    mypDialog.dismiss();
                }
            }


        });
    }
}
