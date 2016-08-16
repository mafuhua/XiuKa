package com.yuen.xiuka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.BaseBean;
import com.yuen.xiuka.beans.FENSIBean;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;
import com.yuen.xiuka.xiuquan.MyXiuQuanActivity;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

public class GuanZhuListActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private Button btn_sousuo;
    private TextView tv_titlecontent;
    private Button btn_jia;
    private Button btn_tijiao;
    private ListView lv_guanzhu;
    private List<FENSIBean.DataBean> fensiBeanData;
    private MyAdapter myAdapter;
    private String stringExtra;
    private DbManager db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu_list);
        SysExitUtil.activityList.add(this);
        stringExtra = getIntent().getStringExtra("data");
        uid = getIntent().getStringExtra("uid");
        DbManager.DaoConfig daoConfig = XUtils.getDaoConfig();
        db = x.getDb(daoConfig);
        initView();
        if (stringExtra.equals("fensi")){
            getList(URLProvider.FANS, uid);
            tv_titlecontent.setText("粉丝");
        }else if (stringExtra.equals("guanzhu")){
            getList(URLProvider.GUANZHU, uid);
            tv_titlecontent.setText("关注");
        }

    }

    @Override
    public void initView() {

        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_sousuo = (Button) findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        btn_jia = (Button) findViewById(R.id.btn_jia);
        btn_jia.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        lv_guanzhu = (ListView) findViewById(R.id.lv_guanzhu);

    }

    @Override
    public void loadData() {

    }

    private void getList(String url, String uid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", uid);
        XUtils.xUtilsPost(url, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", result);
                Gson gson = new Gson();
                FENSIBean fensiBean = gson.fromJson(result, FENSIBean.class);
                fensiBeanData = fensiBean.getData();
             //   Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                myAdapter = new MyAdapter(fensiBeanData);
                lv_guanzhu.setAdapter(myAdapter);
                if (stringExtra.equals("guanzhu")){
                    for (int i = 0; i < fensiBeanData.size(); i++) {
                        PersonTable person = new PersonTable();
                        person.setId(Integer.parseInt(fensiBeanData.get(i).getUid()));
                        person.setName(fensiBeanData.get(i).getName());
                        person.setImg(URLProvider.BaseImgUrl+fensiBeanData.get(i).getImage());
                        try {
                            db.saveOrUpdate(person);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    }
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
            case R.id.btn_fanhui:
                finish();
                break;

        }
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

    class WoDeHolder extends BaseHolder<FENSIBean.DataBean> {
        public ImageView ivusericon;
        public TextView tvusername;
        public TextView tvusercontent;
        public CheckBox cbguanzhu;

        @Override
        public View initView() {
            View root = View.inflate(GuanZhuListActivity.this, R.layout.layout_guanzhu_item, null);
            ivusericon = (ImageView) root.findViewById(R.id.iv_user_icon);
            tvusername = (TextView) root.findViewById(R.id.tv_user_name);
            tvusercontent = (TextView) root.findViewById(R.id.tv_user_content);
            cbguanzhu = (CheckBox) root.findViewById(R.id.cb_guanzhu);
            return root;
        }

        @Override
        public void refreshView(final FENSIBean.DataBean data, int position) {
            tvusername.setText(data.getName());
            tvusercontent.setText(data.getQianming());
            x.image().bind(ivusericon, URLProvider.BaseImgUrl + data.getImage(), MyApplication.optionscache);
            ivusericon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stringExtra.equals("fensi")){
                        Intent intent = new Intent(context, MyXiuQuanActivity.class);
                        intent.putExtra("id", data.getUid());
                        intent.putExtra("name", data.getName());
                        startActivity(intent);
                    }else if (stringExtra.equals("guanzhu")){
                        Intent intent = new Intent(context, MyXiuQuanActivity.class);
                        intent.putExtra("id", data.getG_uid());
                        intent.putExtra("name", data.getName());
                        startActivity(intent);
                    }
                }
            });
            cbguanzhu.setChecked(data.getXianghu() == 1 ? true : false);
            cbguanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (cbguanzhu.isChecked()) {
                        //添加对粉丝的关注
                        if (stringExtra.equals("fensi")){
                            addordelguanzhu(URLProvider.ADD_GUANZHU,data.getUid());
                            try {
                                PersonTable person = new PersonTable();
                                person.setId(Integer.parseInt(data.getUid()));
                                person.setName(data.getName());
                                person.setImg(URLProvider.BaseImgUrl+data.getImage());
                                db.saveOrUpdate(person);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }else if (stringExtra.equals("guanzhu")){
                            addordelguanzhu(URLProvider.ADD_GUANZHU,data.getG_uid());
                            try {
                                PersonTable person = new PersonTable();
                                person.setId(Integer.parseInt(data.getG_uid()));
                                person.setName(data.getName());
                                person.setImg(URLProvider.BaseImgUrl+data.getImage());
                                db.saveOrUpdate(person);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }


                    } else {
                        if (stringExtra.equals("fensi")){
                            //删除粉丝的关注
                            addordelguanzhu(URLProvider.DEL_GUANZHU,data.getUid());
                            try {
                                db.deleteById(PersonTable.class, data.getUid());

                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }else if (stringExtra.equals("guanzhu")){
                            //删除粉丝的关注
                            addordelguanzhu(URLProvider.DEL_GUANZHU,data.getG_uid());
                            try {
                                db.deleteById(PersonTable.class, data.getG_uid());

                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }

                private void addordelguanzhu(String url, String uid) {
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
        }
    }
}
