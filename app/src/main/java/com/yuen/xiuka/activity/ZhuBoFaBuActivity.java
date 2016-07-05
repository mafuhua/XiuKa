package com.yuen.xiuka.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.ToastUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.ImgBean;
import com.yuen.xiuka.utils.MyUtils;
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

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class ZhuBoFaBuActivity extends BaseActivity implements View.OnClickListener {
    private final int REQUEST_CODE_GALLERY = 1001;
    public String[] mMonth = new String[]{"月", "8", "9", "10", "11", "12"};
    public String[] mDay = new String[]{
            "日", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31"};
    public String[] mhour = new String[]{
            "时", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24"};
    public String zhibo_time = "2016-月-日 时";
    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private EditText et_platname;
    private Spinner spinner0;
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText et_content;
    private GridView gv_pic;
    private Button btn_fabu;
    private List<String> mPhotoList = new ArrayList<>();
    private List<String> ImageList = new ArrayList<>();
    private File destDir;
    private MYAdapter myAdapter;
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            mPhotoList.clear();
            ImageList.clear();
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
                mPhotoList.add("");
                myAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
    private String resultid;
    private ProgressDialog mypDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_bo_fa_bu);
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
        tv_titlecontent.setText("发布");
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        et_platname = (EditText) findViewById(R.id.et_platname);
        et_platname.setOnClickListener(this);
        spinner0 = (Spinner) findViewById(R.id.spinner0);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        et_content = (EditText) findViewById(R.id.et_content);
        gv_pic = (GridView) findViewById(R.id.gv_pic);
        btn_fabu = (Button) findViewById(R.id.btn_fabu);
        btn_fabu.setOnClickListener(this);
        mPhotoList.add("");
        myAdapter = new MYAdapter();
        gv_pic.setAdapter(myAdapter);
        gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mPhotoList.size() - 1) {
                    if (myAdapter.getCount() >= 9) {
                        ToastUtil.toastShortShow(ZhuBoFaBuActivity.this, "最多选择九张图片");
                    } else {
                        //带配置
                        FunctionConfig config = new FunctionConfig.Builder()
                                .setMutiSelectMaxSize(9)
                                .build();
                        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, mOnHanlderResultCallback);
                    }
                }

            }

        });
        setSpinnerContent(spinner0, mMonth);
        setSpinnerContent(spinner1, mDay);
        setSpinnerContent(spinner2, mhour);

        spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) return;
                zhibo_time.replace("月", mMonth[pos]);
                Toast.makeText(context, zhibo_time, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) return;
                zhibo_time.replace("日", mDay[pos]);
                Toast.makeText(context, zhibo_time, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) return;
                zhibo_time.replace("时", mhour[pos]);
                Toast.makeText(context, zhibo_time, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            case R.id.btn_tijiao:

                break;
            case R.id.btn_fabu:
                submit();
                break;
        }
    }

    private void setSpinnerContent(final Spinner spinners, final String[] mCountries) {
        if (mCountries == null) {
            return;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, mCountries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners.setAdapter(adapter);

    }

    public void sendComPic() {
        for (int i = 0; i < ImageList.size(); i++) {
            sendimg(ImageList.get(i));
        }
        if (mypDialog.isShowing()) {
            mypDialog.dismiss();
        }
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


        client.post(URLProvider.ADD_CIRCLE_IMG, rp, new AsyncHttpResponseHandler() {

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

    private void submit() {
        // validate
        String platname = et_platname.getText().toString().trim();
        if (TextUtils.isEmpty(platname)) {
            Toast.makeText(this, "直播平台不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "说点什么吧!", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("content", content);
        map.put("platform", platname);
        map.put("zhibo_time", platname);
        // TODO validate success, do something
        // fabu(map);


    }

    private void fabu(HashMap<String, String> map) {
        addrenzhengimg();
        XUtils.xUtilsPost(URLProvider.CIRCLE, map, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                ImgBean imgBean = gson.fromJson(result, ImgBean.class);
                if (ImageList.size() == 0) {
                    Toast.makeText(context, imgBean.getMsg(), Toast.LENGTH_SHORT).show();
                    if (mypDialog.isShowing()) {
                        mypDialog.dismiss();
                    }
                } else {
                    if (imgBean.getCode().equals("0")) {
                        resultid = imgBean.getId() + "";
                        Toast.makeText(context, imgBean.getMsg(), Toast.LENGTH_SHORT).show();
                        sendComPic();
                    } else {
                        Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                        if (mypDialog.isShowing()) {
                            mypDialog.dismiss();
                        }
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
                //  Log.d("mafuhua", "bitmap.getByteCount():" + bitmap.getByteCount() / 1024);
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

    class MYAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mPhotoList == null ? 0 : mPhotoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.layout_selector_gridview2, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            x.image().bind(viewHolder.ivgriditemimage, mPhotoList.get(position), MyUtils.options);
            return convertView;
        }

        public class ViewHolder {
            public final ImageView ivgriditemimage;
            public final View root;

            public ViewHolder(View root) {
                ivgriditemimage = (ImageView) root.findViewById(R.id.iv_grid_item_image);
                this.root = root;
            }
        }
    }
}
