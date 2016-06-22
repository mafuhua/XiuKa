package com.yuen.xiuka.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.MYBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BianJiZiLiaoActivity extends BaseActivity implements View.OnClickListener {
    private List settingString2 = new ArrayList(Arrays.asList("头像", "昵称", "秀咖号", "性别", "个性签名", "年龄", "星座", "标签", "所在地区", "职业"));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bian_ji_zi_liao);
        mydata = (MYBean.DataBean) getIntent().getSerializableExtra("user");
        // MYBean.DataBean mydata = (MYBean.DataBean)getIntent().getBundleExtra();
        mydatastrings = new ArrayList<>();
        mydatastrings.add(0, mydata.getName());
        mydatastrings.add(1, mydata.getName());
        mydatastrings.add(2, mydata.getUid());
        mydatastrings.add(3, mydata.getSex());
        mydatastrings.add(4, mydata.getQianming());
        mydatastrings.add(5, mydata.getAge());
        mydatastrings.add(6, mydata.getConstellation());
        mydatastrings.add(7, mydata.getLabel());
        mydatastrings.add(8, mydata.getAdd());
        mydatastrings.add(9, mydata.getZhiye());
        initView();
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
                    startActivityForResult(intent, position);
                }
            }
        });
    }

    private void dialogsex() {
        final String items[] = {"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("选择性别"); //设置标题
        // builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
                Toast.makeText(BianJiZiLiaoActivity.this, items[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(BianJiZiLiaoActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void dialogxingzuo() {
        final String items[] = {"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("选择星座"); //设置标题
        // builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
                Toast.makeText(BianJiZiLiaoActivity.this, items[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(BianJiZiLiaoActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void dialogbiaoqian() {
        final String items[] = {"篮球", "足球", "排球", "人气", "推荐", "热门", "哈哈"};
        final boolean selected[] = {false, false, false, false, false, false, false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("选择标签"); //设置标题
        //  builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // dialog.dismiss();
                Toast.makeText(BianJiZiLiaoActivity.this, items[which] + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(BianJiZiLiaoActivity.this, "确定", Toast.LENGTH_SHORT).show();
                //android会自动根据你选择的改变selected数组的值。
                for (int i = 0; i < selected.length; i++) {
                    Log.e("mafuhua", "" + selected[i]);
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            if (position == 0) {
                iv_user_icon.setBackgroundResource(R.drawable.touxiang);
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
