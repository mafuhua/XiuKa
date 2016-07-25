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
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class XiuQuanFragment2 extends BaseFragment implements View.OnClickListener {
    public ListView mixlist;
    private Context context;
    private TextView tv_fensi;
    private TextView tv_guanzhu;
    private TextView tv_renzheng;
    private TextView tv_name;
    private ImageView iv_user_icon;
    private RelativeLayout header;
    private ImageView iv_bj;
    private List<XIUQUANBean.XiuQuanDataBean> xiuquanBeanData;
    private XiuQuanAdapter myAdapter;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private Button btn_jia;

    @Override
    public View initView() {
        context = getActivity();

        View view = View.inflate(getActivity(), R.layout.layout_xiuquanfragment, null);
        mixlist = (ListView) view.findViewById(R.id.mixlist);
        btn_fanhui = (Button) view.findViewById(R.id.btn_fanhui);
        btn_jia = (Button) view.findViewById(R.id.btn_jia);
        tv_titlecontent = (TextView) view.findViewById(R.id.tv_titlecontent);
        btn_fanhui.setVisibility(View.GONE);
        btn_jia.setVisibility(View.VISIBLE);
        tv_titlecontent.setText("秀圈");
        header = (RelativeLayout) View.inflate(getActivity(), R.layout.layout_xiuquan_header, null);
        tv_fensi = (TextView) header.findViewById(R.id.tv_fensi);
        tv_guanzhu = (TextView) header.findViewById(R.id.tv_guanzhu);
        tv_renzheng = (TextView) header.findViewById(R.id.tv_renzheng);
        tv_name = (TextView) header.findViewById(R.id.tv_user_name);
        iv_user_icon = (ImageView) header.findViewById(R.id.iv_user_icon);
        iv_bj = (ImageView) header.findViewById(R.id.iv_bj);
        mixlist.addHeaderView(header);
        tv_fensi.setOnClickListener(this);
        tv_guanzhu.setOnClickListener(this);
        tv_renzheng.setOnClickListener(this);
        iv_user_icon.setOnClickListener(this);
        iv_bj.setOnClickListener(this);
        btn_jia.setOnClickListener(this);

       // mixlist.setOnScrollListener(new PauseOnScrollListener());

        mixlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, PingLunActivity.class);
                intent.putExtra("data", xiuquanBeanData.get(position - 1));
                context.startActivity(intent);
            }
        });
        return view;
    }

    public void xiuquan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("page", 0 + "");
        XUtils.xUtilsPost(URLProvider.LOOK_CIRCLE, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("2222222222222222"+getActivity());
                Gson gson = new Gson();
                XIUQUANBean xiuquanBean = gson.fromJson(result, XIUQUANBean.class);
                String bj_image = xiuquanBean.getBj_image();
                xiuquanBeanData = xiuquanBean.getData();
                 Toast.makeText(getActivity(), URLProvider.BaseImgUrl + bj_image, Toast.LENGTH_SHORT).show();
                System.out.println(result);
                System.out.println(getActivity());
                x.image().bind(iv_bj, URLProvider.BaseImgUrl + bj_image, MyApplication.options);
                myAdapter = new XiuQuanAdapter(context,xiuquanBeanData);
                mixlist.setAdapter(myAdapter);

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


    }

    @Override
    public void onResume() {
        super.onResume();
        initheader();
        xiuquan();

    }

    public void initheader() {
        tv_fensi.setText("粉丝" + SPUtil.getString("fensi"));
        tv_guanzhu.setText("关注" + SPUtil.getString("guanzhu"));
        tv_name.setText(SPUtil.getString("name"));
        tv_renzheng.setText("认证平台" + SPUtil.getString("platform"));
        x.image().bind(iv_user_icon, URLProvider.BaseImgUrl + SPUtil.getString("icon"), MyApplication.options);
        //Glide.with(context).load(URLProvider.BaseImgUrl + SPUtil.getString("icon")).centerCrop().error(R.drawable.cuowu).crossFade().into(iv_user_icon);


      //  Toast.makeText(context, "initheader"+SPUtil.getString("icon"), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fensi:
                startActivity(GuanZhuListActivity.class, "fensi");
                break;
            case R.id.tv_guanzhu:
                startActivity(GuanZhuListActivity.class, "guanzhu");
                break;
            case R.id.iv_bj:
                ShowPickDialog();
                Toast.makeText(context, "iv_bj", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_user_icon:
              /*  Intent intent = new Intent(getActivity(), MyXiuQuanActivity.class);
                intent.putExtra("id", SPUtil.getInt("uid")+"");
                intent.putExtra("name",SPUtil.getString("name"));
                startActivity(intent);
                Toast.makeText(context, "iv_user_icon", Toast.LENGTH_SHORT).show();*/
                break;
            case R.id.btn_jia:
                startActivity(FaBuActivity.class);
                break;
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
                        /**
                         * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
                         * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
                         */
                        Intent intent = new Intent(Intent.ACTION_PICK, null);

                        /**
                         * 下面这句话，与其它方式写是一样的效果，如果：
                         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                         * intent.setType(""image/*");设置数据类型
                         * 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                         * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
                         */
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
                Uri data1 = data.getData();
                getPhoneImage(data1);
                break;
            // 如果是调用相机拍照时
            case 2:
                temp = new File(Environment.getExternalStorageDirectory()+ "/imagcacahe/"
                        + "/iconbg.jpg");
                Compresspic(Environment.getExternalStorageDirectory()+ "/imagcacahe/"
                        + "/iconbg01.jpg", temp.getAbsolutePath());

                x.image().bind(iv_bj, temp.getAbsolutePath());
                break;

            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 获取系统缺省路径选择的图片
     * @param
     * @return
     */
    private void getPhoneImage(Uri uriString)
    {
        String[] filePathColumns = { MediaStore.Images.Media.DATA };
        Cursor cursor =getActivity().getContentResolver().query(uriString,
                filePathColumns, null, null, null);//从系统表中查询指定Uri对应的照片
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
        String picturePath = cursor.getString(columnIndex);  //获取照片路径
        cursor.close();
        x.image().bind(iv_bj, picturePath);
        // ((ImageView) findViewById(R.id.iv_temp)).setImageBitmap(BitmapFactory.decodeFile(picturePath));

    }


    private File iconfile;
    private boolean icon = false;
    private File destDir;
    private Bitmap iconphoto;
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
        x.image().bind(iv_bj, iconfile.getAbsolutePath());
       // sendimg(iconfile.getPath());
    }

    private void sendimg(String path) {

        AsyncHttpClient client = new AsyncHttpClient();
        com.loopj.android.http.RequestParams rp = new com.loopj.android.http.RequestParams();

        File file = new File(path);
        //  Log.d("mafuhua", path + "**************");
        try {
            rp.add("uid", SPUtil.getInt("uid") + "");
            rp.put("img", file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        client.post(URLProvider.ADD_IMAGE, rp, new AsyncHttpResponseHandler() {

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
                icon = true;
                myAdapter.notifyDataSetChanged();
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
