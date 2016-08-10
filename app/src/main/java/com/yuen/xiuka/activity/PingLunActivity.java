package com.yuen.xiuka.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.PINGLUNBean;
import com.yuen.xiuka.beans.XiuQuanDataBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;
import com.yuen.xiuka.xiuquan.PagersImgActivity;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PingLunActivity extends BaseActivity implements View.OnClickListener {

    public ImageView imgview[] = new ImageView[9];
    private ImageView listuserimg;
    private TextView username;
    private TextView usercontent;
    private TextView add;
    private TextView time;
    private ImageView showimage;
    private GridLayout gridview;
    private RelativeLayout rl_content;
    private ListView lv_pinglun;
    private EditText et_pinglun;
    private Button btn_pinglun;
    private int ImagaId[] = {R.id.img_0, R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7, R.id.img_8};
    private XiuQuanDataBean xiuquanBeanData;
    private List<XiuQuanDataBean.ImageBean> imageBeanList;
    private int windowwidth;
    private Context context;
    private RelativeLayout heaser;
    private List settingString2 = new ArrayList(Arrays.asList("设置备注", "秀咖号", "性别", "个性签名", "年龄", "星座", "标签", "所在地区", "职业", "职业"));
    private List<PINGLUNBean.DataBean.CommentsBean> pinglunBeanDataComments;
    private MyAdapter myAdapter;
    private TextView pingluncount;


    private String xiuquanid;
    private Button btn_fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_lun);
        SysExitUtil.activityList.add(this);
        Intent intent = getIntent();
        xiuquanBeanData = (XiuQuanDataBean) intent.getSerializableExtra("data");
        xiuquanid = xiuquanBeanData.getId();
        initView();
     //   Toast.makeText(context, xiuquanid, Toast.LENGTH_SHORT).show();
        xiuquan(xiuquanid);
    }

    @Override
    public void initView() {
        context = this;
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();

        heaser = (RelativeLayout) View.inflate(context, R.layout.layout_pinglun_header, null);

        for (int i = 0; i < 9; i++) {
            imgview[i] = (ImageView) heaser.findViewById(ImagaId[i]);
        }
        listuserimg = (ImageView) heaser.findViewById(R.id.listuserimg);
        listuserimg.setOnClickListener(this);
        username = (TextView) heaser.findViewById(R.id.username);
        pingluncount = (TextView) heaser.findViewById(R.id.pingluncount);
        username.setOnClickListener(this);
        usercontent = (TextView) heaser.findViewById(R.id.usercontent);
        usercontent.setOnClickListener(this);
        add = (TextView) heaser.findViewById(R.id.add);
        add.setOnClickListener(this);
        time = (TextView) heaser.findViewById(R.id.time);
        time.setOnClickListener(this);
        showimage = (ImageView) heaser.findViewById(R.id.showimage);
        gridview = (GridLayout) heaser.findViewById(R.id.gridview);
        lv_pinglun = (ListView) findViewById(R.id.lv_pinglun);
        et_pinglun = (EditText) findViewById(R.id.et_pinglun);
        btn_pinglun = (Button) findViewById(R.id.btn_pinglun);
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_pinglun.setOnClickListener(this);
        btn_fanhui.setOnClickListener(this);
        add.setText(xiuquanBeanData.getAdd());
        time.setText(xiuquanBeanData.getTime());
        username.setText(xiuquanBeanData.getName());
        usercontent.setText(xiuquanBeanData.getContent());


        x.image().bind(listuserimg, URLProvider.BaseImgUrl + xiuquanBeanData.getImg(), MyApplication.options);

        imageBeanList = xiuquanBeanData.getImage();

        if (imageBeanList.size() == 1) {
            showimage.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.GONE);
            showimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Toast.makeText(context, "position:", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PagersImgActivity.class);
                    intent.putExtra("data", (Serializable) imageBeanList);
                    intent.putExtra("index", "0");
                    context.startActivity(intent);
                }
            });
            // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + imageBeanList.get(0).getImg(), viewHolder.showimage);
            x.image().bind(showimage, URLProvider.BaseImgUrl + imageBeanList.get(0).getImg(), MyApplication.optionsxq);
        } else {
            showimage.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            int a = imageBeanList.size() / 3;
            int b = imageBeanList.size() % 3;
            if (b > 0) {
                a++;
            }
            float width = (windowwidth - dip2px(40)) / 3;
            gridview.getLayoutParams().height = (int) (a * width);

            for (int i = 0; i < 9; i++) {
                imgview[i].setVisibility(View.GONE);
            }

            for (int i = 0; i < imageBeanList.size(); i++) {
                XiuQuanDataBean.ImageBean imageBean = imageBeanList.get(i);
                imgview[i].setVisibility(View.VISIBLE);
                imgview[i].getLayoutParams().width = (int) width;
                imgview[i].getLayoutParams().height = (int) width;
                // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + imageBean.getImg(), viewHolder.imgview[i]);
                x.image().bind(imgview[i], URLProvider.BaseImgUrl + imageBean.getImg(), MyApplication.optionsxq);
                imgview[i].setOnClickListener(new GridOnclick(0, imgview[i], imageBeanList, i, gridview));
            }
        }
        lv_pinglun.addHeaderView(heaser);

    }


    @Override
    public void loadData() {

    }

    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void xiuquan(String xiukaid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", xiukaid);
        XUtils.xUtilsPost(URLProvider.COMMENTS, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                // Toast.makeText(PingLunActivity.this, result, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                PINGLUNBean pinglunBean = gson.fromJson(result, PINGLUNBean.class);
                PINGLUNBean.DataBean pinglunBeanData = pinglunBean.getData();
                pinglunBeanDataComments = pinglunBeanData.getComments();

                myAdapter = new MyAdapter(pinglunBeanDataComments);
                lv_pinglun.setAdapter(myAdapter);
                pingluncount.setText("评论" + pinglunBeanDataComments.size());

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pinglun:
                submit();
                break;
            case R.id.btn_fanhui:
                finish();
                break;
        }
    }

    private void submit() {
        // validate
        String pinglun = et_pinglun.getText().toString().trim();
        if (TextUtils.isEmpty(pinglun)) {
            Toast.makeText(this, "发评论", Toast.LENGTH_SHORT).show();
            return;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_pinglun.getWindowToken(), 0);
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("circle_id", xiuquanid);
        map.put("content", pinglun);
        XUtils.xUtilsPost(URLProvider.ADD_COMMENTS, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //   Toast.makeText(context, result, Toast.LENGTH_LONG).show();

                xiuquan(xiuquanid);
                et_pinglun.clearFocus();
                et_pinglun.setText("");
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

    class GridOnclick implements View.OnClickListener {

        private List<XiuQuanDataBean.ImageBean> imageBeanList;
        private int index;
        private int position;
        private ImageView imageView;
        private GridLayout gridLayout;

        public GridOnclick(int position, ImageView imageView, List<XiuQuanDataBean.ImageBean> imageBeanList, int index, GridLayout gridLayout) {
            this.imageBeanList = imageBeanList;
            this.index = index;
            this.position = position;
            this.imageView = imageView;
            this.gridLayout = gridLayout;
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, PagersImgActivity.class);
            intent.putExtra("data", (Serializable) imageBeanList);
            intent.putExtra("index", index);
            context.startActivity(intent);
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

    public class ViewHolder extends BaseHolder<PINGLUNBean.DataBean.CommentsBean> {
        public ImageView rootlistuserimg;
        public TextView rootusername;
        public TextView rootusercontent;
        public TextView roottime;

        @Override
        public View initView() {
            View rootView = View.inflate(context, R.layout.layout_pinglun_item, null);
            rootlistuserimg = (ImageView) rootView.findViewById(R.id.listuserimg);
            rootusername = (TextView) rootView.findViewById(R.id.username);
            rootusercontent = (TextView) rootView.findViewById(R.id.pinluncontent);
            roottime = (TextView) rootView.findViewById(R.id.time);
            return rootView;
        }

        @Override
        public void refreshView(PINGLUNBean.DataBean.CommentsBean data, int position) {
            rootusername.setText(data.getName());
            rootusercontent.setText(data.getContent());
            roottime.setText(data.getTime());
            System.out.println(URLProvider.BaseImgUrl + data.getImg() + "haha");

            x.image().bind(rootlistuserimg, URLProvider.BaseImgUrl + data.getImg(), MyApplication.options);
        }
    }
}