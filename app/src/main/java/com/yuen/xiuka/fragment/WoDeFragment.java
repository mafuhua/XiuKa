package com.yuen.xiuka.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.BianJiZiLiaoActivity;
import com.yuen.xiuka.activity.GongHuiRenZhengActivity;
import com.yuen.xiuka.activity.GuanZhuListActivity;
import com.yuen.xiuka.activity.RenZhengActivity;
import com.yuen.xiuka.activity.SettingActivity;
import com.yuen.xiuka.beans.MYBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class WoDeFragment extends BaseFragment implements View.OnClickListener {
    private List<String> wodeItemDec = new ArrayList<String>(Arrays.asList("主播认证", "传媒公司/工会认证",
           "消息提醒", "设置"));
    private TextView tvGuanzhu;
    private TextView tvFensi;
    private ListView lvWode;
    private LinearLayout ll_guanzhu;
    private LinearLayout ll_fensi;
    private LinearLayout layout_title_usericon;
    private Context context;
    private MYBean.DataBean myBeanData;

    private void assignViews(View view) {
        context = getActivity();
        tvGuanzhu = (TextView) view.findViewById(R.id.tv_guanzhu);
        tvFensi = (TextView) view.findViewById(R.id.tv_fensi);
        lvWode = (ListView) view.findViewById(R.id.lv_wode);
        ll_guanzhu = (LinearLayout) view.findViewById(R.id.ll_guanzhu);
        layout_title_usericon = (LinearLayout) view.findViewById(R.id.layout_title_usericon);
        ll_fensi = (LinearLayout) view.findViewById(R.id.ll_fensi);
        ll_guanzhu.setOnClickListener(this);
        layout_title_usericon.setOnClickListener(this);
        ll_fensi.setOnClickListener(this);
        lvWode.setAdapter(new MyAdapter(wodeItemDec));
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
                    case 2:
                      //  startActivity(ZhuBoXinXiActivity.class);
                        break;

                    case 3:
                        startActivity(SettingActivity.class);
                        break;

                }
            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.layout_wodefragment, null);
        assignViews(view);
        return view;
    }

    @Override
    public void initData() {

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
                startActivity(GuanZhuListActivity.class,"fensi");
                break;
            case R.id.ll_guanzhu:
                startActivity(GuanZhuListActivity.class,"guanzhu");
                break;
            case R.id.layout_title_usericon:
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
        map.put("uid", SPUtil.getInt("uid")+"");
        XUtils.xUtilsPost(URLProvider.MY, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", "----MY------" + result);
                System.out.print(result);
               // Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                MYBean myBean = gson.fromJson(result, MYBean.class);
                myBeanData = myBean.getData();



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

            return root;
        }

        @Override
        public void refreshView(String data, int position) {
            if (position == 2 || position == 3 || position == 4) {
                line.setVisibility(View.VISIBLE);
            } else {
                line.setVisibility(View.GONE);
            }
            if (position==2){
                sw_tixing.setVisibility(View.VISIBLE);
            }else {
                sw_tixing.setVisibility(View.GONE);
            }
            tvwodeitemdec.setText(data);
            ivwodeitemicon.setBackgroundResource(wodeItemImg[position]);
        }
    }
}
