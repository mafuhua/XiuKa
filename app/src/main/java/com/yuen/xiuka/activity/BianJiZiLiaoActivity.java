package com.yuen.xiuka.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.BaseBean;
import com.yuen.xiuka.beans.MYBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BianJiZiLiaoActivity extends BaseActivity implements View.OnClickListener {
    int infowhich = -1;
    String xingzuoitems[] = {"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};
    String biaoqianitems[] = {"篮球", "足球", "排球", "人气", "推荐", "热门", "哈哈"};
    private List settingString2 = new ArrayList(Arrays.asList("头像", "昵称", "秀咖号", "性别", "个性签名", "年龄", "星座", "标签", "所在地区", "职业"));
    private List settingkey = new ArrayList(Arrays.asList("Uid", "name", "秀咖号", "sex", "qianming", "age", "constellation", "label", "add", "zhiye"));
    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private ImageView iv_user_icon;
    private TextView tv_user_name;
    private RelativeLayout rl_user_icon;
    private ListView lv_ziliao;
    private MyAdapter myAdapter;
    private ArrayList<String> mydatastrings;
    private MYBean.DataBean mydata;
    private File destDir;
    private Bitmap iconphoto;
    private String biaoqian;
    private File iconfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bian_ji_zi_liao);
        mydata = (MYBean.DataBean) getIntent().getSerializableExtra("user");
        // MYBean.DataBean mydata = (MYBean.DataBean)getIntent().getBundleExtra();
        mydatastrings = new ArrayList<>();
        datasetting();
        initView();
    }

    private void datasetting() {
        mydatastrings.clear();
        mydatastrings.add(0, "");
        mydatastrings.add(1, mydata.getName());
        mydatastrings.add(2, mydata.getUid());
        if (mydata.getSex().equals("1")) {
            mydatastrings.add(3, "男");
        } else {
            mydatastrings.add(3, "女");
        }
        mydatastrings.add(4, mydata.getQianming());
        mydatastrings.add(5, mydata.getAge());
        mydatastrings.add(6, mydata.getConstellation());
        mydatastrings.add(7, mydata.getLabel());
        mydatastrings.add(8, mydata.getAdd());
        mydatastrings.add(9, mydata.getZhiye());
    }

    @Override
    public void initView() {

        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("编辑资料");
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        lv_ziliao = (ListView) findViewById(R.id.lv_ziliao);
        myAdapter = new MyAdapter(mydatastrings);
        lv_ziliao.setAdapter(myAdapter);
        lv_ziliao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ShowPickDialog();
                } else if (position == 2) {

                } else if (position == 3) {
                    dialogsex();

                } else if (position == 6) {
                    dialogxingzuo();
                } else if (position == 7) {
                    dialogbiaoqian();
                } else {
                    Intent intent = new Intent(BianJiZiLiaoActivity.this, EditUserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra("data", mydata);
                    intent.putExtra("position", position);
                    intent.putExtra("key", (String) settingkey.get(position));
                    startActivityForResult(intent, position);
                }
            }
        });
    }

    /**
     * 选择提示对话框
     */
    private void ShowPickDialog() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像...")
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
                        /**
                         * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
                         * 文档，you_sdk_path/docs/guide/topics/media/camera.html
                         * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
                         * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
                         */
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        //下面这句指定调用相机拍照后的照片存储的路径
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory()+"/imagcacahe/",
                                        "/icon.jpg")));
                        startActivityForResult(intent, 2);
                    }
                }).show();
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        /*
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
		 * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
		 * 制做的了...吼吼
		 */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void dialogsex() {
        final String items[] = {"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("选择性别"); //设置标题
        // builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                infowhich = which+1;
                saveInfo("sex", infowhich + "", 10);

                Toast.makeText(BianJiZiLiaoActivity.this, items[which] + infowhich, Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
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
        iconfile = new File(destDir + "/icon.jpg");
        if (iconfile.exists()){
            iconfile.delete();
            Toast.makeText(context, "删除文件", Toast.LENGTH_SHORT).show();
            iconfile = new File(destDir + "/icon.jpg");
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
         sendimg(iconfile.getPath());
    }

    private void sendimg(String path) {

        AsyncHttpClient client = new AsyncHttpClient();
        com.loopj.android.http.RequestParams rp = new com.loopj.android.http.RequestParams();

        File file = new File(path);
        //  Log.d("mafuhua", path + "**************");
        try {
            rp.add("uid",SPUtil.getInt("uid")+"");
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
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }


        });
    }

    private void dialogxingzuo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("选择星座"); //设置标题
        // builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setSingleChoiceItems(xingzuoitems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
                infowhich = which;
                Toast.makeText(BianJiZiLiaoActivity.this, xingzuoitems[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                saveInfo("constellation", xingzuoitems[infowhich], 11);
                Toast.makeText(context, xingzuoitems[infowhich], Toast.LENGTH_SHORT).show();

            }
        });
        builder.create().show();
    }

    private void dialogbiaoqian() {
        final boolean selected[] = {false, false, false, false, false, false, false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("选择标签"); //设置标题
        //  builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setMultiChoiceItems(biaoqianitems, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // dialog.dismiss();
                //   Toast.makeText(BianJiZiLiaoActivity.this, items[which] + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //android会自动根据你选择的改变selected数组的值。
               biaoqian = "";
                for (int i = 0; i < selected.length; i++) {
                    Log.e("mafuhua", "" + biaoqianitems[i]);
                    if (selected[i]) {
                        biaoqian += biaoqianitems[i];
                    }
                }
                Toast.makeText(context, biaoqian, Toast.LENGTH_SHORT).show();
                saveInfo("label", biaoqian, 12);

            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String extras="";
        switch (resultCode) {
            case 1:
                extras = data.getStringExtra("key");
                mydata.setName(extras);
                datasetting();
                myAdapter.notifyDataSetChanged();
                break;
            case 4:
                extras = data.getStringExtra("key");
                mydata.setQianming(extras);
                datasetting();
                myAdapter.notifyDataSetChanged();
                break;
            case 5:
                extras = data.getStringExtra("key");
                mydata.setAge(extras);
                datasetting();
                myAdapter.notifyDataSetChanged();
                break;
            case 7:
                extras = data.getStringExtra("key");
                mydata.setLabel(extras);
                datasetting();
                myAdapter.notifyDataSetChanged();
                break;
            case 8:
                extras = data.getStringExtra("key");
                mydata.setAdd(extras);
                datasetting();
                myAdapter.notifyDataSetChanged();
                break;
            case 9:
                extras = data.getStringExtra("key");
                mydata.setZhiye(extras);
                datasetting();
                myAdapter.notifyDataSetChanged();
                break;


        }
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                startPhotoZoom(data.getData());
                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory()
                       + "/imagcacahe/",
                        "/icon.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            // 取得裁剪后的图片
            case 3:
                /**
                 * 非空判断大家一定要验证，如果不验证的话，
                 * 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException，小马只
                 * 在这个地方加下，大家可以根据不同情况在合适的
                 * 地方做判断处理类似情况
                 *
                 */
                if (data != null) {
                    saveBitmapFile(data);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveInfo(String key, String info, final int flag) {
        HashMap<String, String> map = new HashMap<>();
        map.put(key, info);
        map.put("uid", SPUtil.getInt("uid") + "");
        XUtils.xUtilsPost(URLProvider.SAVE_DATA, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", "----SAVE_DATA-----" + result);
                Gson gson = new Gson();
                BaseBean baseBean = gson.fromJson(result, BaseBean.class);
                Toast.makeText(BianJiZiLiaoActivity.this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (flag == 10) {
                    mydata.setSex(infowhich + "");
                    datasetting();
                    myAdapter.notifyDataSetChanged();
                } else if (flag == 11) {
                    mydata.setConstellation(xingzuoitems[infowhich]);
                    datasetting();
                    myAdapter.notifyDataSetChanged();
                } else if (flag == 12) {
                    mydata.setLabel(biaoqian);
                    datasetting();
                    myAdapter.notifyDataSetChanged();
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
        }
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

    public class ViewHolder extends BaseHolder<String> {
        public TextView tvshopmanagerleft;
        public TextView tvshopmanagerright;
        public ImageView ivShopItemImage;
        public ImageView iv_user_icon;
        public View line;

        @Override
        public View initView() {
            View root = View.inflate(context, R.layout.layout_zilia_xinxi_item, null);
            tvshopmanagerleft = (TextView) root.findViewById(R.id.tv_shop_manager_left);
            line = root.findViewById(R.id.line);
            ivShopItemImage = (ImageView) root.findViewById(R.id.iv_shop_item_image);
            iv_user_icon = (ImageView) root.findViewById(R.id.iv_user_icon);
            tvshopmanagerright = (TextView) root.findViewById(R.id.tv_shop_manager_right);
            return root;
        }

        @Override
        public void refreshView(String data, int position) {
            if (position == 0 && iconphoto != null) {
                //iv_user_icon.setImageBitmap(iconphoto);
                x.image().bind(iv_user_icon,iconfile.getAbsolutePath(), MyApplication.options);
            } else {
                tvshopmanagerright.setText(mydatastrings.get(position));
            }
            if (position == 6 || position == 1) {
                line.setVisibility(View.VISIBLE);
            } else {
                line.setVisibility(View.GONE);
            }
            tvshopmanagerleft.setText((String) settingString2.get(position));

        }
    }
}
