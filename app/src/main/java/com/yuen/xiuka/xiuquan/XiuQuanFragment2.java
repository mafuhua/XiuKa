package com.yuen.xiuka.xiuquan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.FaBuActivity;
import com.yuen.xiuka.activity.GuanZhuListActivity;
import com.yuen.xiuka.activity.PingLunActivity;
import com.yuen.xiuka.beans.XIUQUANBean;
import com.yuen.xiuka.beans.XiuQuanDataBean;
import com.yuen.xiuka.utils.MyEvent;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by Administrator on 2016/6/13.
 */
public class XiuQuanFragment2 extends BaseFragment implements View.OnClickListener {
    public ListView mixlist;
    public boolean isRefresh = false;//是否刷新
    private Context context;
    private TextView tv_fensi;
    private TextView tv_guanzhu;
    private TextView tv_renzheng;
    private TextView tv_name;
    private ImageView iv_user_icon;
    private RelativeLayout header;
    private ImageView iv_bj;
    private List<XiuQuanDataBean> xiuquanBeanData;
    private List<XiuQuanDataBean> xiuquanListData = new ArrayList<>();


    private XiuQuanAdapter myAdapter;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private boolean refresh = false;
    private SwipeRefreshLayout swiperefresh;
    private int page = 0;
    private File iconfile;
    private boolean icon = false;
    private File destDir;
    private Bitmap iconphoto;
    private XIUQUANBean.DatasBean xiuquanBeanDatas;
    private ImageView iv_huangwei;
    private ImageView iv_lanwei;
    private boolean head = true;
    private TextView tv_fangjian;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    isRefresh = false;
                    swiperefresh.setRefreshing(false);
                    //adapter.notifyDataSetChanged();
                    //  swiperefresh.setEnabled(false);
                    break;
                case 2:
                    Toast.makeText(context, "正在刷新", Toast.LENGTH_SHORT).show();
                 /*   mixlist.requestLayout();
                    myAdapter.notifyDataSetChanged();*/
                    Log.d("mafuhua", "刷新");
                    xiuquan();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View initView() {
        context = getActivity();

        View view = View.inflate(getActivity(), R.layout.layout_xiuquanfragment, null);
        mixlist = (ListView) view.findViewById(R.id.mixlist);
        btn_fanhui = (Button) view.findViewById(R.id.btn_fanhui);
        btn_jia = (Button) view.findViewById(R.id.btn_jia);
        tv_titlecontent = (TextView) view.findViewById(R.id.tv_titlecontent);
        swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        btn_fanhui.setVisibility(View.GONE);
        tv_titlecontent.setText("秀圈");
        header = (RelativeLayout) View.inflate(getActivity(), R.layout.layout_xiuquan_header, null);
        tv_fensi = (TextView) header.findViewById(R.id.tv_fensi);
        tv_guanzhu = (TextView) header.findViewById(R.id.tv_guanzhu);
        tv_renzheng = (TextView) header.findViewById(R.id.tv_renzheng);
        tv_fangjian = (TextView) header.findViewById(R.id.tv_fangjian);
        tv_name = (TextView) header.findViewById(R.id.tv_user_name);
        iv_user_icon = (ImageView) header.findViewById(R.id.iv_user_icon);
        iv_huangwei = (ImageView) header.findViewById(R.id.iv_huangwei);
        iv_lanwei = (ImageView) header.findViewById(R.id.iv_lanwei);
        iv_bj = (ImageView) header.findViewById(R.id.iv_bj);
        mixlist.addHeaderView(header);
        tv_fensi.setOnClickListener(this);
        tv_guanzhu.setOnClickListener(this);
        tv_renzheng.setOnClickListener(this);
        iv_user_icon.setOnClickListener(this);
        iv_bj.setOnClickListener(this);
        btn_jia.setOnClickListener(this);

        myAdapter = new XiuQuanAdapter(context, xiuquanListData, true);
        mixlist.setAdapter(myAdapter);

        mixlist.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 滚动状态
                // Log.d("mafuhua", "scrollState:" + scrollState);
                int lastVisiblePosition = mixlist.getLastVisiblePosition();
                Log.d("mafuhua", "lastVisiblePosition:" + lastVisiblePosition);
                Log.d("mafuhua", "mixlist.getCount():" + mixlist.getCount());
                // 如果处于空闲状态
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //最后一个条目
                    if (lastVisiblePosition == mixlist.getCount() - 1) {

                        Log.d("mafuhua", "加载下一页");
                        //请求下一页数据：handler模拟加载下一页数据
//                        handler.sendEmptyMessageDelayed(REFRESHFINISH, 2000);
                        page += 1;
                        xiuquan();
                    }

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

        });

        mixlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, PingLunActivity.class);
                intent.putExtra("data", xiuquanListData.get(position - 1));
                context.startActivity(intent);
            }
        });



      /*  mixlist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRefresh) {
                    return true;
                } else {
                    return false;
                }

            }
        });*/
        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isRefresh) {
                            isRefresh = true;
                            page = 0;
                            mHandler.sendEmptyMessage(2);
                        }
                    }
                }).start();
            }
        });
        return view;
    }

    public void xiuquan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("page", page + "");
        XUtils.xUtilsPost(URLProvider.LOOK_CIRCLE, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("2222222222222222" + result);
                mHandler.sendEmptyMessage(1);
                Gson gson = new Gson();
              /*  if (!result.contains("data")){
                    Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                XIUQUANBean xiuquanBean = gson.fromJson(result, XIUQUANBean.class);
                xiuquanBeanDatas = xiuquanBean.getDatas();
                xiuquanBeanData = xiuquanBean.getData();
                if (page == 0) {
                    xiuquanListData.clear();
                    initheader(xiuquanBeanDatas);
                }
                xiuquanListData.addAll(xiuquanBeanData);
                myAdapter.notifyDataSetChanged();

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

    @Override
    public void initData() {
        xiuquan();

    }

    @Override
    public void onPause() {
        super.onPause();
        head = true;
    }

    @Override
    public void onResume() {
        super.onResume();
      /*  page = 0;
        xiuquanListData.clear();
        Toast.makeText(context, "刷新", Toast.LENGTH_SHORT).show();
        Log.d("mafuhua", "刷新");
        xiuquan();*/
    }

    public void initheader(XIUQUANBean.DatasBean xiuquanDatas) {
        head = false;
        tv_fensi.setText("粉丝" + xiuquanDatas.getFensi());
        tv_guanzhu.setText("关注" + xiuquanDatas.getGuanzhu());
        tv_name.setText(xiuquanDatas.getName());
        if (xiuquanBeanDatas.getPlatform() != null && xiuquanBeanDatas.getPlatform().length() > 0) {
            tv_renzheng.setText("认证平台" + xiuquanBeanDatas.getPlatform());
        }
        x.image().bind(iv_user_icon, URLProvider.BaseImgUrl + xiuquanDatas.getImage(), MyApplication.options);
        x.image().bind(iv_bj, URLProvider.BaseImgUrl + xiuquanDatas.getBj_image(), MyApplication.optionsxq);
        if (xiuquanDatas.getPlatformname().length() > 0&&xiuquanDatas.getPlatformid().length()>0) {
            tv_fangjian.setText("房间号：" + xiuquanDatas.getPlatformname()+" 房间ID："+xiuquanDatas.getPlatformid());
        }else if (xiuquanDatas.getPlatformname().length() > 0){
            tv_fangjian.setText("房间号：" + xiuquanDatas.getPlatformname());
        }else if(xiuquanDatas.getPlatformid().length() > 0){
            tv_fangjian.setText("房间ID："+xiuquanDatas.getPlatformid());
        }
        if (xiuquanBeanDatas.getShifou_ren() == 0) {
            iv_lanwei.setVisibility(View.GONE);
        } else if (xiuquanBeanDatas.getShifou_ren() == 1) {
            iv_lanwei.setVisibility(View.VISIBLE);
        }
        if (xiuquanBeanDatas.getType().equals("0")) {
            iv_huangwei.setVisibility(View.GONE);
        } else if (xiuquanBeanDatas.getType().equals("1")) {
            iv_huangwei.setVisibility(View.VISIBLE);
        }
        //Glide.with(context).load(URLProvider.BaseImgUrl + SPUtil.getString("icon")).centerCrop().error(R.drawable.cuowu).crossFade().into(iv_user_icon);


        //  Toast.makeText(context, "initheader"+SPUtil.getString("icon"), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fensi:
                if (xiuquanBeanDatas == null) return;
                startActivity(GuanZhuListActivity.class, "fensi", xiuquanBeanDatas.getUid());
                break;
            case R.id.tv_guanzhu:
                if (xiuquanBeanDatas == null) return;
                startActivity(GuanZhuListActivity.class, "guanzhu", xiuquanBeanDatas.getUid());
                break;
            case R.id.iv_bj:
                ShowPickDialog();
                //   Toast.makeText(context, "iv_bj", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_user_icon:
                Intent intent = new Intent(getActivity(), MyXiuQuanActivity.class);
                intent.putExtra("id", SPUtil.getInt("uid") + "");
                intent.putExtra("name", SPUtil.getString("name"));
                startActivity(intent);
                // Toast.makeText(context, "iv_user_icon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_jia:
                refresh = true;
                startActivity(FaBuActivity.class);
                break;
        }
    }

    public void onEventMainThread(MyEvent event) {
        MyEvent.Event eventEvent = event.getEvent();
        switch (eventEvent) {
            case REFRESH_XIUQUAN:
                page = 0;
                xiuquanListData.clear();
                Log.d("mafuhua", "刷新");
                xiuquan();
                //   Toast.makeText(getActivity(), "onEventMainThread收到了消息", Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (registered) {
            EventBus.getDefault().unregister(this);//反注册EventBus
        }

    }

    /**
     * 选择提示对话框
     */
    private void ShowPickDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("设置背景...")
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 1);

                    }
                })
                .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();

                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        //下面这句指定调用相机拍照后的照片存储的路径
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory() + "/imagcacahe/",
                                        "/iconbg.jpg")));
                        startActivityForResult(intent, 2);
                    }
                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if (data== null) return;
        String extras = "";
        File temp;
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                if (data == null) return;
                Uri data1 = data.getData();
                getPhoneImage(data1);
                break;
            // 如果是调用相机拍照时
            case 2:
                temp = new File(Environment.getExternalStorageDirectory() + "/imagcacahe/"
                        + "/iconbg.jpg");
                Compresspic(Environment.getExternalStorageDirectory() + "/imagcacahe/"
                        + "/iconbg01.jpg", temp.getAbsolutePath());

                //     x.image().bind(iv_bj, temp.getAbsolutePath());
                sendimg(temp.getAbsolutePath());
                break;

            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取系统缺省路径选择的图片
     *
     * @param
     * @return
     */
    private void getPhoneImage(Uri uriString) {
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uriString,
                filePathColumns, null, null, null);//从系统表中查询指定Uri对应的照片
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
        String picturePath = cursor.getString(columnIndex);  //获取照片路径
        cursor.close();
        sendimg(picturePath);
        //   x.image().bind(iv_bj, picturePath);
        // ((ImageView) findViewById(R.id.iv_temp)).setImageBitmap(BitmapFactory.decodeFile(picturePath));

    }

    public void saveBitmapFile(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            iconphoto = extras.getParcelable("data");
        }
        /**
         * 创建文件夹存放压缩文件
         */
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        destDir = new File(externalStorageDirectory + "/imagcacahe/");
        // Log.d("mafuhua", "externalStorageDirectory:" + destDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        //将要保存图片的路径
        iconfile = new File(destDir + "/iconbg.jpg");
        if (iconfile.exists()) {
            iconfile.delete();
            Toast.makeText(context, "删除文件", Toast.LENGTH_SHORT).show();
            iconfile = new File(destDir + "/iconbg.jpg");
        }
        try {

            Toast.makeText(context, "创建文件" + iconfile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(iconfile));
            iconphoto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        sendimg(iconfile.getAbsolutePath());
    }

    private void sendimg(final String path) {

        AsyncHttpClient client = new AsyncHttpClient();
        com.loopj.android.http.RequestParams rp = new com.loopj.android.http.RequestParams();

        File file = new File(path);
        Log.d("mafuhua", path + "**************");
        try {
            rp.add("uid", SPUtil.getInt("uid") + "");
            rp.put("img", file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        client.post(URLProvider.ADD_BJ_IMAGE, rp, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.d("mafuhua", "responseBody" + response);
                Gson gson = new Gson();
             /*   IconResultBean iconResultBean = gson.fromJson(response, IconResultBean.class);
                if (iconResultBean.getStatus().equals("0")) {
                    Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                    getUserIcon(ContactURL.SHOP_STORE_TOU + MainActivity.userid);
                } else {
                    Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                }*/
                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                x.image().bind(iv_bj, path);
                //icon = true;
                //  myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

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

}
