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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.FaBuActivity;
import com.yuen.xiuka.activity.GuanZhuListActivity;
import com.yuen.xiuka.activity.PingLunActivity;
import com.yuen.xiuka.activity.ZhuBoXiangXiActivity;
import com.yuen.xiuka.beans.BaseBean;
import com.yuen.xiuka.beans.XIUQUANBean;
import com.yuen.xiuka.beans.XiuQuanDataBean;
import com.yuen.xiuka.utils.MyEvent;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;

public class MyXiuQuanActivity extends com.yuen.xiuka.activity.BaseActivity implements View.OnClickListener {


    public ListView mixlist;
    public boolean isRefresh = false;//是否刷新
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
    public  List<XiuQuanDataBean> xiuquanBeanData;
    private boolean refresh = false;
    private SwipeRefreshLayout swiperefresh;
    private int page = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    isRefresh = false;
                    swiperefresh.setRefreshing(false);
                    //adapter.notifyDataSetChanged();
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                case 2:
                    Toast.makeText(context, "正在刷新", Toast.LENGTH_SHORT).show();
                  /*  mixlist.requestLayout();
                    myAdapter.notifyDataSetChanged();*/
                    Log.d("mafuhua", "刷新");
                    xiuquan();
                    break;
                default:
                    break;
            }
        }
    };
    public static List<XiuQuanDataBean> xiuquanListData = new ArrayList<>();
    private DbManager db;
    private TextView tv_jiaguanzhu;
    private LinearLayout ll_jiasixin;
    private LinearLayout ll_jiaguanzhu;
    private ImageView iv_jiaguanzhu;
    private LinearLayout ll_bottom;
    private ImageView iv_huangwei;
    private ImageView iv_lanwei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_xiu_quan);
        Intent intent = getIntent();
        // xiuquandata = (XIUQUANBean.XiuQuanDataBean) intent.getSerializableExtra("data");
        xiuquandataId = intent.getStringExtra("id");
        xiuquandataName = intent.getStringExtra("name");

        DbManager.DaoConfig daoConfig = XUtils.getDaoConfig();
        db = x.getDb(daoConfig);
      /*  xiuquandataId = xiuquandata.getId();
        xiuquandataName = xiuquandata.getName();*/
        // Toast.makeText(this, xiuquandataId + xiuquandataName, Toast.LENGTH_SHORT).show();
        initView();
        xiuquan();
        SysExitUtil.activityList.add(this);
    }

    @Override
    public void initView() {
        context = this;
        mixlist = (ListView) findViewById(R.id.mixlist);
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_jia = (Button) findViewById(R.id.btn_jia);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_jiaguanzhu = (TextView) findViewById(R.id.tv_jiaguanzhu);
        iv_jiaguanzhu = (ImageView) findViewById(R.id.iv_jiaguanzhu);
        ll_jiaguanzhu = (LinearLayout) findViewById(R.id.ll_jiaguanzhu);
        ll_jiasixin = (LinearLayout) findViewById(R.id.ll_jiasixin);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        btn_fanhui.setVisibility(View.VISIBLE);
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        tv_titlecontent.setText(xiuquandataName + "");
        header = (RelativeLayout) View.inflate(this, R.layout.layout_xiuquan_header, null);
        tv_fensi = (TextView) header.findViewById(R.id.tv_fensi);
        tv_guanzhu = (TextView) header.findViewById(R.id.tv_guanzhu);
        tv_renzheng = (TextView) header.findViewById(R.id.tv_renzheng);
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
        btn_fanhui.setOnClickListener(this);
        ll_jiaguanzhu.setOnClickListener(this);
        ll_jiasixin.setOnClickListener(this);


        myAdapter = new XiuQuanAdapter(context, xiuquanListData, false);
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
                            isRefresh = true;
                            page = 0;
                           // xiuquanListData.clear();
                            mHandler.sendEmptyMessage(2);
                        }

                    }
                }).start();

            }
        });
        mixlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, PingLunActivity.class);
                intent.putExtra("data", xiuquanListData.get(position-1));
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

    @Override
    public void loadData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered) {
            EventBus.getDefault().register(this);
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

    public void xiuquan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", xiuquandataId);
        map.put("my_uid", SPUtil.getInt("uid")+"");
        map.put("page", page + "");
        XUtils.xUtilsPost(URLProvider.LOOK_MY_CIRCLE, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                isRefresh = false;
                   // Toast.makeText(context,result, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
              /*  if (!result.contains("data")){
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
                if (xiuquanBeanDatas.getUid().equals(SPUtil.getInt("uid") + "")) {
                    ll_bottom.setVisibility(View.GONE);
                } else {
                    ll_bottom.setVisibility(View.VISIBLE);
                }

                myAdapter.notifyDataSetChanged();
                mHandler.sendEmptyMessage(1);
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

    }

    public void onEventMainThread(MyEvent event) {
        MyEvent.Event eventEvent = event.getEvent();
        switch (eventEvent) {
            case REFRESH_XIUQUAN:
                page = 0;
                xiuquanListData.clear();
                Log.d("mafuhua", "刷新");
                xiuquan();
                // Toast.makeText(MyXiuQuanActivity.this, "onEventMainThread消息", Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void initheader(final XIUQUANBean.DatasBean xiuquanBeanDatas) {
        tv_fensi.setText("粉丝" + xiuquanBeanDatas.getFensi());

        tv_guanzhu.setText("关注" + xiuquanBeanDatas.getGuanzhu());
        tv_name.setText(xiuquanBeanDatas.getName());
        if (xiuquanBeanDatas.getPlatform() != null && xiuquanBeanDatas.getPlatform().length() > 0) {
            tv_renzheng.setText("认证平台" + xiuquanBeanDatas.getPlatform());
        }
        x.image().bind(iv_user_icon, URLProvider.BaseImgUrl + xiuquanBeanDatas.getImage(), MyApplication.options);
        x.image().bind(iv_bj, URLProvider.BaseImgUrl + xiuquanBeanDatas.getBj_image(), MyApplication.optionsxq);

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
        if (xiuquanBeanDatas.getShifou()==1) {
            tv_jiaguanzhu.setText("取消关注");
            iv_jiaguanzhu.setBackgroundResource(R.drawable.yiguanzhu1);
        } else if (xiuquanBeanDatas.getShifou()==0) {
            tv_jiaguanzhu.setText("加关注");
            iv_jiaguanzhu.setBackgroundResource(R.drawable.jiaguanzhu);
        }

        //Glide.with(context).load(URLProvider.BaseImgUrl + SPUtil.getString("icon")).centerCrop().error(R.drawable.cuowu).crossFade().into(iv_user_icon);

      /*  if (SPUtil.getInt("uid") == Integer.parseInt(xiuquanBeanDatas.getUid())) {

        } else {
            tv_guanzhu.setText("我要关注");

        }*/
    }

    private void addordelguanzhu(String url, String uid, final int type) {
        //  Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("g_uid", uid);
        //   Toast.makeText(context, "uid" + SPUtil.getInt("uid") + "g_uid" + uid, Toast.LENGTH_SHORT).show();
        XUtils.xUtilsPost(url, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                BaseBean baseBean = gson.fromJson(result, BaseBean.class);
                Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                xiuquanBeanDatas.setShifou(type);
                if (xiuquanBeanDatas.getShifou()==1) {
                    tv_jiaguanzhu.setText("取消关注");
                    iv_jiaguanzhu.setBackgroundResource(R.drawable.yiguanzhu1);
                } else if (xiuquanBeanDatas.getShifou()==0) {
                    tv_jiaguanzhu.setText("加关注");
                    iv_jiaguanzhu.setBackgroundResource(R.drawable.jiaguanzhu);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fensi:
                if (xiuquanBeanDatas == null) return;
                startActivity(GuanZhuListActivity.class, "fensi", xiuquanBeanDatas.getUid());
               /* if (xiuquandataId.equals(SPUtil.getInt("uid") + "")) {
                }*/
                break;
            case R.id.tv_guanzhu:
                if (xiuquanBeanDatas == null) return;
                startActivity(GuanZhuListActivity.class, "guanzhu", xiuquanBeanDatas.getUid());

                break;
            case R.id.tv_renzheng:
                break;
            case R.id.iv_bj:
                break;
            case R.id.iv_user_icon:
                if (xiuquanBeanDatas == null) {
                    return;
                }
                if (xiuquanBeanDatas.getUid().equals(SPUtil.getInt("uid") + "")) {
                    return;
                }
                Intent intent = new Intent(this, ZhuBoXiangXiActivity.class);
                intent.putExtra("uid", xiuquanBeanDatas.getUid());
                startActivity(intent);
                break;

            case R.id.btn_jia:
                startActivity(FaBuActivity.class);
                break;
            case R.id.btn_fanhui:
                finish();
                break;
            case R.id.ll_jiaguanzhu:
                if (xiuquanBeanDatas == null) {
                    return;
                }
                if (xiuquanBeanDatas.getShifou()==0) {
                    addordelguanzhu(URLProvider.ADD_GUANZHU, xiuquanBeanDatas.getUid(), 1);

                    PersonTable person = new PersonTable();
                    person.setId(Integer.parseInt(xiuquanBeanDatas.getUid()));
                    person.setName(xiuquanBeanDatas.getName());
                    person.setImg(URLProvider.BaseImgUrl + xiuquanBeanDatas.getImage());
                    try {
                        db.saveOrUpdate(person);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                } else if (xiuquanBeanDatas.getShifou()==1) {
                    addordelguanzhu(URLProvider.DEL_GUANZHU, xiuquanBeanDatas.getUid(), 0);
                    try {
                        db.deleteById(PersonTable.class, xiuquanBeanDatas.getUid());
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.ll_jiasixin:
                if (xiuquanBeanDatas == null) {
                    return;
                }
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(MyXiuQuanActivity.this, xiuquanBeanDatas.getUid(), xiuquanBeanDatas.getName());
                    // RongIM.getInstance().startCustomerServiceChat(ZhuBoXiangXiActivity.this, myBeanData.getUid(), myBeanData.getName());
                }
                break;
        }
    }


}
