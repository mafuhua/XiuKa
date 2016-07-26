package com.yuen.xiuka.xiuquan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.FaBuActivity;
import com.yuen.xiuka.activity.GuanZhuListActivity;
import com.yuen.xiuka.activity.PingLunActivity;
import com.yuen.xiuka.beans.XIUQUANBean;
import com.yuen.xiuka.beans.XiuQuanDataBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyXiuQuanActivity extends com.yuen.xiuka.activity.BaseActivity implements View.OnClickListener {


    public ListView mixlist;
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
    private XiuQuanAdapter myAdapter;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private XIUQUANBean.DatasBean xiuquanBeanDatas;
    private List<XiuQuanDataBean> xiuquanBeanData;

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
    private boolean refresh = false;
    private SwipeRefreshLayout swiperefresh;
    private int page = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    swiperefresh.setRefreshing(false);
                    //adapter.notifyDataSetChanged();
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void initView() {
        context = this;
        mixlist = (ListView) findViewById(R.id.mixlist);
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_jia = (Button) findViewById(R.id.btn_jia);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        btn_fanhui.setVisibility(View.GONE);
        btn_jia.setVisibility(View.VISIBLE);
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
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

        myAdapter = new XiuQuanAdapter(context, xiuquanListData);
        mixlist.setAdapter(myAdapter);

        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(context, "正在刷新", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override

                    public void run() {
                        if (!isRefresh) {
                            page = 0;
                            xiuquanListData.clear();
                            Log.d("mafuhua", "刷新");
                            xiuquan();
                            mHandler.sendEmptyMessage(1);
                        }

                    }
                }).start();

            }
        });
        mixlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, PingLunActivity.class);
                intent.putExtra("data", xiuquanBeanData.get(position - 1));
                context.startActivity(intent);
            }
        });
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


    }
    private List<XiuQuanDataBean> xiuquanListData = new ArrayList<>();
    @Override
    public void loadData() {

    }
    public boolean isRefresh = false;//是否刷新
    public void xiuquan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", xiuquandataId);
        map.put("page", 0 + "");
        XUtils.xUtilsPost(URLProvider.LOOK_MY_CIRCLE, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                System.out.println(result);
                XIUQUANBean xiuquanBean = gson.fromJson(result, XIUQUANBean.class);
                xiuquanBeanData = xiuquanBean.getData();
                xiuquanBeanDatas = xiuquanBean.getDatas();
                xiuquanListData.addAll(xiuquanBeanData);
                if (page == 0){
                    initheader(xiuquanBeanDatas);
                }
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
    public void onResume() {
        super.onResume();
        xiuquan();

    }

    public void initheader(XIUQUANBean.DatasBean xiuquanBeanDatas) {
        tv_fensi.setText("粉丝" + xiuquanBeanDatas.getFensi());
        tv_guanzhu.setText("关注" + xiuquanBeanDatas.getGuanzhu());
        tv_name.setText(xiuquanBeanDatas.getName());
        tv_renzheng.setText("认证平台" + xiuquanBeanDatas.getPlatform());

        x.image().bind(iv_user_icon, URLProvider.BaseImgUrl + xiuquanBeanDatas.getImage(), MyApplication.options);
       // x.image().bind(iv_bj, URLProvider.BaseImgUrl + xiuquanBeanDatas.getBj_image(), MyApplication.optionsxq);
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
