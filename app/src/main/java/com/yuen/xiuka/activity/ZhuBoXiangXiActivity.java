package com.yuen.xiuka.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.MYBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.rong.imkit.RongIM;

public class ZhuBoXiangXiActivity extends BaseActivity implements View.OnClickListener {
    private List settingString2 = new ArrayList(Arrays.asList("设置备注", "秀咖号", "性别", "个性签名", "年龄", "星座", "标签", "所在地区", "职业", "职业"));
    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private ListView lv_xiangxi;
    private Button btn_sendmsg;
    private ImageView usericon;
    private TextView username;
    private MYBean.DataBean myBeanData;
    private MyAdapter myAdapter;
    private String uid;
    private HashMap<Integer, String> mydatastrings = new HashMap<>();
    private Button btn_lookcircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_bo_xiang_xi);
        initView();
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
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
        myAdapter = new MyAdapter();
        lv_xiangxi.setAdapter(myAdapter);

        usericon = (ImageView) header.findViewById(R.id.iv_user_icon);
        username = (TextView) header.findViewById(R.id.tv_user_name);
        // username.setText("宝贝");
        lv_xiangxi.addHeaderView(header);
        setListViewHeightBasedOnChildren(lv_xiangxi);
        btn_sendmsg = (Button) findViewById(R.id.btn_sendmsg);
        btn_sendmsg.setOnClickListener(this);
        lv_xiangxi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                if (position == 1) {
                    settingbeizhu(position, "设置备注", "");
                }
            }
        });
        other();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len - 1; i++) {
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
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(ZhuBoXiangXiActivity.this, myBeanData.getUid(), myBeanData.getName());
                   // RongIM.getInstance().startCustomerServiceChat(ZhuBoXiangXiActivity.this, myBeanData.getUid(), myBeanData.getName());
                }
                break;

        }
    }

    public void settingbeizhu(final int position, String str, final String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请输入" + str);
        final EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        // editText.setHint(hint);
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String weight = editText.getText().toString().trim();
                Toast.makeText(context, weight, Toast.LENGTH_SHORT).show();
            /*    settingMap.put(position, weight + hint);
                myAdapter.notifyDataSetChanged();*/
                HashMap<String, String> map = new HashMap<>();
                map.put("uid", SPUtil.getInt("uid") + "");
                map.put("g_uid", myBeanData.getUid());
                map.put("name", weight);
                XUtils.xUtilsPost(URLProvider.ADD_NOTE, map, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println(result);
                        mydatastrings.put(2, weight);
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
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void other() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("g_uid", SPUtil.getInt("uid") + "");
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
                datasetting();

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

    private void datasetting() {
        mydatastrings.clear();
        mydatastrings.put(0, myBeanData.getImage());
        mydatastrings.put(1, myBeanData.getName());
        mydatastrings.put(2, myBeanData.getNote_name());
        mydatastrings.put(3, myBeanData.getUid());
        if (myBeanData.getSex().equals("1")) {
            mydatastrings.put(4, "男");
        } else {
            mydatastrings.put(4, "女");
        }
        mydatastrings.put(5, myBeanData.getQianming());
        mydatastrings.put(6, myBeanData.getAge());
        mydatastrings.put(7, myBeanData.getConstellation());
        mydatastrings.put(8, myBeanData.getLabel());
        mydatastrings.put(9, myBeanData.getAdd());
        mydatastrings.put(10, myBeanData.getZhiye());

        x.image().bind(usericon, URLProvider.BaseImgUrl + mydatastrings.get(0), MyApplication.options);
        username.setText(mydatastrings.get(1));
        myAdapter.notifyDataSetChanged();


    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return settingString2 == null ? 0 : settingString2.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.layout_xiangxi_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (position == 0 || position == 1 || position == 6) {
                viewHolder.line.setVisibility(View.VISIBLE);
            } else {
                viewHolder.line.setVisibility(View.GONE);
            }
            viewHolder.tv_shop_manager_left.setText(settingString2.get(position).toString());
            if (mydatastrings.size() > 1) {
                viewHolder.tv_shop_manager_right.setText(mydatastrings.get(position + 2));
            } else {

            }

            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public View line;
            public TextView tv_shop_manager_left;
            public TextView tv_shop_manager_right;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.line = (View) rootView.findViewById(R.id.line);
                this.tv_shop_manager_left = (TextView) rootView.findViewById(R.id.tv_shop_manager_left);
                this.tv_shop_manager_right = (TextView) rootView.findViewById(R.id.tv_shop_manager_right);
            }

        }
    }

}
