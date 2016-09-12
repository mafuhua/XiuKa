package com.yuen.xiuka.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.BianJiZiLiaoActivity;
import com.yuen.xiuka.activity.GongHuiRenZhengActivity;
import com.yuen.xiuka.activity.GuanZhuListActivity;
import com.yuen.xiuka.activity.LianxiwomenActivity;
import com.yuen.xiuka.activity.RenZhengActivity;
import com.yuen.xiuka.activity.SettingActivity;
import com.yuen.xiuka.beans.MYBean;
import com.yuen.xiuka.beans.PushBean;
import com.yuen.xiuka.utils.MyEvent;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2016/6/13.
 */
public class WoDeFragment extends BaseFragment implements View.OnClickListener {
    private List<String> wodeItemDec = new ArrayList<String>(Arrays.asList("主播认证", "传媒公司/工会认证",
            "消息提醒", "联系我们", "设置"));
    private TextView tvGuanzhu;
    private TextView tvFensi;
    private ListView lvWode;
    private LinearLayout ll_guanzhu;
    private LinearLayout ll_fensi;
    private LinearLayout layout_title_usericon;
    private Context context;
    private MYBean.DataBean myBeanData;
    private ImageView iv_user_icon;
    private TextView tv_user_tel;
    private TextView tv_user_name;
    private boolean switchc = true;
    private MyAdapter myAdapter;
    private View fensi;

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

    public void onEventMainThread(MyEvent event) {
        MyEvent.Event eventEvent = event.getEvent();
        switch (eventEvent) {
            case REFRESH_FENSI:
                String getmPush = event.getmPush();
                Gson gson = new Gson();
                PushBean pushBean = gson.fromJson(getmPush, PushBean.class);
                if (pushBean.getTxt().getType().equals("4")) {
                    fensi.setVisibility(View.VISIBLE);
                }
                SPUtil.saveInt("fensij",1);
                //    initNotify(pushBean);
                break;
            case REFRESH_FENSIH:
                fensi.setVisibility(View.GONE);
                SPUtil.saveInt("fensij",0);
                break;
        }
        my();

    }


    private void assignViews(View view) {
        context = getActivity();
        tvGuanzhu = (TextView) view.findViewById(R.id.tv_guanzhu);
        tvFensi = (TextView) view.findViewById(R.id.tv_fensi);
        lvWode = (ListView) view.findViewById(R.id.lv_wode);
        fensi = view.findViewById(R.id.fensi);
        ll_guanzhu = (LinearLayout) view.findViewById(R.id.ll_guanzhu);
        layout_title_usericon = (LinearLayout) view.findViewById(R.id.layout_title_usericon);
        iv_user_icon = (ImageView) view.findViewById(R.id.iv_user_icon);
        tv_user_tel = (TextView) view.findViewById(R.id.tv_user_tel);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        ll_fensi = (LinearLayout) view.findViewById(R.id.ll_fensi);
        ll_guanzhu.setOnClickListener(this);
        layout_title_usericon.setOnClickListener(this);
        ll_fensi.setOnClickListener(this);

        tvFensi.setText(SPUtil.getString("fensi"));
        tvGuanzhu.setText(SPUtil.getString("guanzhu"));
        tv_user_name.setText(SPUtil.getString("name"));
        tv_user_tel.setText("ID:" + SPUtil.getInt("uid"));
        myAdapter = new MyAdapter(wodeItemDec);
        lvWode.setAdapter(myAdapter);
        lvWode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        startActivity(RenZhengActivity.class);
                        break;
                    case 1:
                        startActivity(GongHuiRenZhengActivity.class);
                        break;
                    case 3:
                        startActivity(LianxiwomenActivity.class);
                        break;

                    case 4:
                        startActivity(SettingActivity.class);
                        break;

                }
            }
        });
        if (SPUtil.getInt("fensij")==1){
            fensi.setVisibility(View.VISIBLE);
        }else {
            fensi.setVisibility(View.GONE);
        }

    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.layout_wodefragment, null);
        assignViews(view);
        return view;
    }

    @Override
    public void initData() {
        getNotificationStatus();
    }

    /**
     * 得到当前消息免打扰状态
     */
    private void getNotificationStatus() {

        if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {

            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                RongIM.getInstance().getRongIMClient().getNotificationQuietHours(new RongIMClient.GetNotificationQuietHoursCallback() {
                    @Override
                    public void onSuccess(String startTime, int spanMins) {
                        Log.d("WoDeFragment", "消息通知");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        switchc = false;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        my();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fensi:
                if (myBeanData == null) return;
                startActivity(GuanZhuListActivity.class, "fensi", myBeanData.getUid());
                break;
            case R.id.ll_guanzhu:
                if (myBeanData == null) return;
                startActivity(GuanZhuListActivity.class, "guanzhu", myBeanData.getUid());
                break;
            case R.id.layout_title_usericon:
                if (myBeanData == null) return;
                Intent intent = new Intent(getActivity(), BianJiZiLiaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", myBeanData);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;

        }
    }

    public void my() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        XUtils.xUtilsPost(URLProvider.MY, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", "----MY------" + result);
                System.out.print(result);
                //  Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                MYBean myBean = gson.fromJson(result, MYBean.class);
                // Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                myBeanData = myBean.getData();
                x.image().bind(iv_user_icon, URLProvider.BaseImgUrl + myBeanData.getImage(), MyApplication.options);
                tvFensi.setText(myBeanData.getFensi() + "");
                tvGuanzhu.setText(myBeanData.getGuanzhu() + "");
                tv_user_name.setText(myBeanData.getName());
                tv_user_tel.setText("ID:" + myBeanData.getUid());
                SPUtil.saveString("name", myBeanData.getName());
                SPUtil.saveString("icon", myBeanData.getImage());
                SPUtil.saveString("guanzhu", myBeanData.getGuanzhu() + "");
                SPUtil.saveString("fensi", myBeanData.getFensi() + "");
                SPUtil.saveString("platform", myBeanData.getPlatform());
                SPUtil.saveString("token", myBeanData.getToken());
                SPUtil.saveString("type", myBeanData.getType());
                RongIM.getInstance().setCurrentUserInfo(new UserInfo(SPUtil.getInt("uid") + "", SPUtil.getString("name"), Uri.parse(URLProvider.BaseImgUrl + SPUtil.getString("icon"))));
                //      Toast.makeText(context,"icon"+myBeanData.getImage(), Toast.LENGTH_LONG).show();

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

    class MyAdapter extends DefaultAdapter {
        public MyAdapter(List datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new WoDeHolder();
        }
    }

    class WoDeHolder extends BaseHolder<String> {
        public ImageView ivwodeitemicon;
        public TextView tvwodeitemdec;
        public Switch sw_tixing;
        public View line;
        private int[] wodeItemImg = new int[]{R.drawable.renzheng, R.drawable.renzheng, R.drawable.xinxi,
                R.drawable.huihua, R.drawable.shezhi};


        @Override
        public View initView() {
            View root = View.inflate(getActivity(), R.layout.layout_wode_list_item, null);
            ivwodeitemicon = (ImageView) root.findViewById(R.id.iv_wode_item_icon);
            sw_tixing = (Switch) root.findViewById(R.id.sw_tixing);
            line = root.findViewById(R.id.line);
            tvwodeitemdec = (TextView) root.findViewById(R.id.tv_wode_item_dec);
           /* sw_tixing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (sw_tixing.isChecked()) {
                                RongIM.getInstance().getRongIMClient().removeNotificationQuietHours(new RongIMClient.OperationCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.d("WoDeHolder", "移除消息通知");
                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {
                                    }
                                });
                            } else {
                                RongIM.getInstance().getRongIMClient().setNotificationQuietHours("00:00:00", 1339, new RongIMClient.OperationCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.d("WoDeHolder", "添加消息通知");
                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {

                                    }
                                });
                            }


                        }
                    });

                }
            });*/
            return root;
        }

        @Override
        public void refreshView(String data, int position) {
          /*  if (position == 2 || position == 3 || position == 4||position==5) {
                line.setVisibility(View.VISIBLE);
            } else {
                line.setVisibility(View.GONE);
            }*/
            if (position == 2) {
                sw_tixing.setVisibility(View.VISIBLE);
                sw_tixing.setChecked(switchc);
            } else {
                sw_tixing.setVisibility(View.GONE);
            }
            tvwodeitemdec.setText(data);
            ivwodeitemicon.setBackgroundResource(wodeItemImg[position]);
        }
    }
}
