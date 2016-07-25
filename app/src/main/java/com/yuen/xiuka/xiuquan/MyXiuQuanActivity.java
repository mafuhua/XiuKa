package com.yuen.xiuka.xiuquan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.FaBuActivity;
import com.yuen.xiuka.activity.GuanZhuListActivity;
import com.yuen.xiuka.activity.PingLunActivity;
import com.yuen.xiuka.beans.MYXIUQUANBean;
import com.yuen.xiuka.beans.XIUQUANBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

public class MyXiuQuanActivity extends com.yuen.xiuka.activity.BaseActivity implements View.OnClickListener {


    public ListView mixlist;
    private XIUQUANBean.XiuQuanDataBean xiuquandata;
    private String xiuquandataName;
    private String xiuquandataId;
    private Context context;
    private TextView tv_fensi;
    private TextView tv_guanzhu;
    private TextView tv_renzheng;
    private TextView tv_name;
    private ImageView iv_user_icon;
    private RelativeLayout header;
    private ImageView iv_bj;
    private MyXiuQuanAdapter myAdapter;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private MYXIUQUANBean.DatasBean xiuquanBeanDatas;
    private List<MYXIUQUANBean.DataBean> xiuquanBeanData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_xiu_quan);
        Intent intent = getIntent();
       // xiuquandata = (XIUQUANBean.XiuQuanDataBean) intent.getSerializableExtra("data");
       xiuquandataId = intent.getStringExtra("id");
        xiuquandataName = intent.getStringExtra("name");
      /*  xiuquandataId = xiuquandata.getId();
        xiuquandataName = xiuquandata.getName();*/
        Toast.makeText(this, xiuquandataId + xiuquandataName, Toast.LENGTH_SHORT).show();
        initView();
    }

    @Override
    public void initView() {
        context = this;
        mixlist = (ListView) findViewById(R.id.mixlist);
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_jia = (Button) findViewById(R.id.btn_jia);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        btn_fanhui.setVisibility(View.GONE);
        btn_jia.setVisibility(View.VISIBLE);
        tv_titlecontent.setText(xiuquandataName+"");
        header = (RelativeLayout) View.inflate(this, R.layout.layout_xiuquan_header, null);
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


        mixlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, PingLunActivity.class);
                intent.putExtra("data", xiuquanBeanData.get(position - 1));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void loadData() {

    }

    public void xiuquan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", xiuquandataId);
        map.put("page", 0 + "");
        XUtils.xUtilsPost(URLProvider.LOOK_MY_CIRCLE, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                System.out.println(result);
                MYXIUQUANBean xiuquanBean = gson.fromJson(result, MYXIUQUANBean.class);
                xiuquanBeanData = xiuquanBean.getData();
                xiuquanBeanDatas = xiuquanBean.getDatas();
                initheader(xiuquanBeanDatas);
                myAdapter = new MyXiuQuanAdapter(context, xiuquanBeanData);
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
    public void onResume() {
        super.onResume();
        xiuquan();

    }

    public void initheader(MYXIUQUANBean.DatasBean xiuquanBeanDatas) {
        tv_fensi.setText("粉丝" + xiuquanBeanDatas.getFensi());
        tv_guanzhu.setText("关注" + xiuquanBeanDatas.getGuanzhu());
        tv_name.setText(xiuquanBeanDatas.getName());
        tv_renzheng.setText("认证平台" + xiuquanBeanDatas.getPlatform());

        x.image().bind(iv_user_icon, URLProvider.BaseImgUrl + xiuquanBeanDatas.getImage(), MyApplication.options);
        x.image().bind(iv_bj, URLProvider.BaseImgUrl + xiuquanBeanDatas.getBj_image(), MyApplication.optionsxq);
        //Glide.with(context).load(URLProvider.BaseImgUrl + SPUtil.getString("icon")).centerCrop().error(R.drawable.cuowu).crossFade().into(iv_user_icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fensi:

                if (xiuquandataId.equals( SPUtil.getInt("uid") + "")){
                    startActivity(GuanZhuListActivity.class, "fensi");
                }
                break;
            case R.id.tv_guanzhu:
                if (xiuquandataId.equals( SPUtil.getInt("uid") + "")){
                    startActivity(GuanZhuListActivity.class, "guanzhu");
                }

                break;
            case R.id.tv_renzheng:
                Toast.makeText(context, "tv_renzheng", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_bj:
                Toast.makeText(context, "iv_bj", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_user_icon:
                Toast.makeText(context, "iv_user_icon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_jia:
                startActivity(FaBuActivity.class);
                break;
        }
    }
}
