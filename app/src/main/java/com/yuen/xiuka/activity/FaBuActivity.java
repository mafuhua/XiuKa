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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.ToastUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.ImgBean;
import com.yuen.xiuka.fragment.FragmentFractory;
import com.yuen.xiuka.utils.MyUtils;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;
import com.yuen.xiuka.xiuquan.XiuQuanFragment2;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class FaBuActivity extends BaseActivity implements View.OnClickListener {

    //声明AMapLocationClient类对象
    public static AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public static AMapLocationClientOption mLocationOption = null;
    public static String province;
    public static String city;
    public static String district;
    public static String street;
    private static TextView tv_add;
    //声明定位回调监听器
    public static AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    aMapLocation.getLatitude();//获取纬度
                    aMapLocation.getLongitude();//获取经度
                    aMapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(aMapLocation.getTime());
                    df.format(date);//定位时间
                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    aMapLocation.getCountry();//国家信息
                    province = aMapLocation.getProvince();
                    city = aMapLocation.getCity();
                    district = aMapLocation.getDistrict();
                    street = aMapLocation.getStreet();
                    aMapLocation.getStreetNum();//街道门牌号信息
                    aMapLocation.getCityCode();//城市编码
                    aMapLocation.getAdCode();//地区编码
                    Log.d("mafuhua", province + city + district + street);
                    tv_add.setText(city);
                    //     Toast.makeText(MyApplication.context, province + city + district + street, Toast.LENGTH_SHORT).show();
                    //  aMapLocation.getAOIName();//获取当前定位点的AOI信息
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("mafuhua", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    private final int REQUEST_CODE_GALLERY = 1001;
    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private EditText et_content;
    private GridView gv_pic;
    private ImageView iv_add;
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

    public static void getLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        //  mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

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
        tv_titlecontent.setText("发布");
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        et_content = (EditText) findViewById(R.id.et_content);
        et_content.setOnClickListener(this);
        gv_pic = (GridView) findViewById(R.id.gv_pic);
        mPhotoList.add("");
        myAdapter = new MYAdapter();
        gv_pic.setAdapter(myAdapter);
        gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mPhotoList.size() - 1) {
                    if (myAdapter.getCount() >= 9) {
                        ToastUtil.toastShortShow(FaBuActivity.this, "最多选择九张图片");
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
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        btn_fabu = (Button) findViewById(R.id.btn_fabu);
        btn_fabu.setOnClickListener(this);
        getLoc();
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

    private void submit() {
        // validate
        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "说点什么吧!", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("content", content);
        map.put("add", city+"");

        fabu(map);

    }

    private void fabu(HashMap<String, String> map) {
        addrenzhengimg();
        XUtils.xUtilsPost(URLProvider.CIRCLE, map, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                ImgBean imgBean = gson.fromJson(result, ImgBean.class);
                if (ImageList.size()==0){
                    Toast.makeText(context, imgBean.getMsg(), Toast.LENGTH_SHORT).show();
                    if (mypDialog.isShowing()) {
                        mypDialog.dismiss();
                    }
                }   else {
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

    public void sendComPic() {
        for (int i = 0; i < ImageList.size(); i++) {
            sendimg(ImageList.get(i));
        }
        if (mypDialog.isShowing()) {
            mypDialog.dismiss();
        }
        XiuQuanFragment2 xiuQuanFragment2 = (XiuQuanFragment2) FragmentFractory.getInstance().createFragment(2);
       // xiuQuanFragment2.xiuquan();
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

    class MYAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mPhotoList.size();
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
                convertView = View.inflate(context, R.layout.layout_selector_gridview, null);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
