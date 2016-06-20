package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZhuBoXiangXiActivity extends BaseActivity implements View.OnClickListener {
    private List settingString2 = new ArrayList(Arrays.asList("头像", "昵称", "性别", "个性签名", "标签", "年龄", "所在地区", "职业", "直播平台", "直播预告"));
    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private ListView lv_xiangxi;
    private Button btn_sendmsg;
    private ImageView usericon;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_bo_xiang_xi);
        initView();
    }

    @Override
    public void initView() {

        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("详细资料");
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        lv_xiangxi = (ListView) findViewById(R.id.lv_xiangxi);
        LinearLayout header = (LinearLayout) View.inflate(ZhuBoXiangXiActivity.this, R.layout.layout_xiangxi_header, null);
        lv_xiangxi.setAdapter(new MyAdapter(settingString2));
        usericon = (ImageView)header.findViewById(R.id.iv_user_icon);
        username = (TextView)header.findViewById(R.id.tv_user_name);
        username.setText("宝贝");
        lv_xiangxi.addHeaderView(header);
        setListViewHeightBasedOnChildren(lv_xiangxi);
        btn_sendmsg = (Button) findViewById(R.id.btn_sendmsg);
        btn_sendmsg.setOnClickListener(this);
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len-1; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;//+ (listView.getHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fanhui:

                break;
            case R.id.btn_sousuo:

                break;
            case R.id.btn_jia:

                break;
            case R.id.btn_tijiao:

                break;
            case R.id.btn_sendmsg:

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
        public View line;

        @Override
        public View initView() {
            View root = View.inflate(context, R.layout.layout_xiangxi_item, null);
            tvshopmanagerleft = (TextView) root.findViewById(R.id.tv_shop_manager_left);
            line = root.findViewById(R.id.line);
            tvshopmanagerright = (TextView) root.findViewById(R.id.tv_shop_manager_right);
            return root;
        }

        @Override
        public void refreshView(String data, int position) {
            if (position ==0||position==1||position==6){
                line.setVisibility(View.VISIBLE);
            }else {
                line.setVisibility(View.GONE);
            }
            tvshopmanagerleft.setText(data);
            tvshopmanagerright.setText(data);
        }
    }
}
