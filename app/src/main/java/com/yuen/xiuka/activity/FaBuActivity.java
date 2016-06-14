package com.yuen.xiuka.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class FaBuActivity extends BaseActivity implements View.OnClickListener {

    private final int REQUEST_CODE_GALLERY = 1001;
    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private EditText et_content;
    private GridView gv_pic;
    private ImageView iv_add;
    private TextView tv_add;
    private Button btn_fabu;
    private List<String> mPhotoList = new ArrayList<>();
    private List<String> ImageList = new ArrayList<>();
    private File destDir;
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            mPhotoList.clear();
            if (resultList != null) {
                for (int i = 0; i < resultList.size(); i++) {
                    PhotoInfo photoInfo = resultList.get(i);
                    String photoPath = photoInfo.getPhotoPath();
                    mPhotoList.add(photoPath);
                    Log.d("mafuhua", "mPhotoList:" + photoPath);
                }

                for (int i = 0; i < mPhotoList.size(); i++) {
                    ImageList.add(destDir.toString() + "/" + i + ".jpg");
                    Log.d("mafuhua", destDir.toString() + "/" + i + ".jpg");
                    Compresspic(ImageList.get(i), mPhotoList.get(i));
                }
                // mChoosePhotoListAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu);
        initView();
    }

    @Override
    public void initView() {
        /**
         * 创建文件夹存放压缩文件
         */
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        destDir = new File(externalStorageDirectory + "/imagcacahe/");
        Log.d("mafuhua", "externalStorageDirectory:" + destDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setOnClickListener(this);
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        et_content = (EditText) findViewById(R.id.et_content);
        et_content.setOnClickListener(this);
        gv_pic = (GridView) findViewById(R.id.gv_pic);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        btn_fabu = (Button) findViewById(R.id.btn_fabu);
        btn_fabu.setOnClickListener(this);
    }

    @Override
    public void loadData() {

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
            case R.id.btn_fabu:
                //带配置
                FunctionConfig config = new FunctionConfig.Builder()
                        .setMutiSelectMaxSize(6)
                        .build();
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, mOnHanlderResultCallback);

                break;
        }
    }

    private void submit() {
        // validate
        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "说点什么吧!", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    class MyAdapter extends DefaultAdapter {
        public MyAdapter(List datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }
    }

    public class ViewHolder extends BaseHolder {
        public TextView tvname;
        public TextView tvadd;

        @Override
        public View initView() {
            View root = View.inflate(context, R.layout.layout_grid_item, null);
            tvname = (TextView) root.findViewById(R.id.tv_name);
            tvadd = (TextView) root.findViewById(R.id.tv_add);
            return root;
        }

        @Override
        public void refreshView(Object data, int position) {

        }
    }
}
