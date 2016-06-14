package com.yuen.xiuka.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.RenZhengActivity;
import com.yuen.xiuka.activity.SettingActivity;
import com.yuen.xiuka.activity.ZhuBoXinXiActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class WoDeFragment extends BaseFragment {
    private List<String> wodeItemDec = new ArrayList<String>(Arrays.asList("主播认证", "传媒公司/工会认证",
            "主播信息修改", "消息提醒", "设置"));
    private TextView tvGuanzhu;
    private TextView tvFensi;
    private ListView lvWode;

    private void assignViews(View view) {
        tvGuanzhu = (TextView) view.findViewById(R.id.tv_guanzhu);
        tvFensi = (TextView) view.findViewById(R.id.tv_fensi);
        lvWode = (ListView) view.findViewById(R.id.lv_wode);
        lvWode.setAdapter(new MyAdapter(wodeItemDec));
        lvWode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        startActivity(RenZhengActivity.class);
                        break;
                    case 1:
                        break;
                    case 2:
                        startActivity(ZhuBoXinXiActivity.class);
                        break;

                    case 3:

                        break;
                    case 4:
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
        private int[] wodeItemImg = new int[]{R.drawable.renzheng, R.drawable.renzheng, R.drawable.xinxi,
                R.drawable.huihua, R.drawable.shezhi};

        @Override
        public View initView() {
            View root = View.inflate(getActivity(), R.layout.layout_wode_list_item, null);
            ivwodeitemicon = (ImageView) root.findViewById(R.id.iv_wode_item_icon);
            tvwodeitemdec = (TextView) root.findViewById(R.id.tv_wode_item_dec);
            return root;
        }

        @Override
        public void refreshView(String data, int position) {
            tvwodeitemdec.setText(data);
            ivwodeitemicon.setBackgroundResource(wodeItemImg[position]);
        }
    }
}
