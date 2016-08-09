package com.yuen.xiuka.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

public class SouSuoActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_fanhui;
    private EditText tv_sousuo;
    private TextView tv_quxiao;
    private Button btn_search;
    private ListView lv_guanzhu;
    private MyAdapter myAdapter;
    private List<FENSIBean.DataBean> fensiBeanData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        SysExitUtil.activityList.add(this);
        initView();
    }

    @Override
    public void initView() {


        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        tv_sousuo = (EditText) findViewById(R.id.tv_sousuo);
        tv_sousuo.setOnClickListener(this);
        tv_quxiao = (TextView) findViewById(R.id.tv_quxiao);
        tv_quxiao.setVisibility(View.GONE);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        lv_guanzhu = (ListView) findViewById(R.id.lv_guanzhu);
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
            case R.id.btn_search:
                submit();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }

    private void submit() {
        // validate
        String sousuo = tv_sousuo.getText().toString().trim();
        if (TextUtils.isEmpty(sousuo)) {
            Toast.makeText(this, "秀咖号/昵称/直播平台标签不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        tv_sousuo.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv_sousuo.getWindowToken(), 0) ;
        // TODO validate success, do something
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("title", sousuo);
        Toast.makeText(context, "正在搜索", Toast.LENGTH_SHORT).show();
        search(map);

    }

    private void search(HashMap<String, String> map) {
        XUtils.xUtilsPost(URLProvider.SEARCH, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                FENSIBean fensiBean = gson.fromJson(result, FENSIBean.class);
                if (fensiBeanData == null) {
                    Toast.makeText(context, "没有此用户", Toast.LENGTH_SHORT).show();
                }else {
                    fensiBeanData = fensiBean.getData();
                    myAdapter = new MyAdapter(fensiBeanData);
                    lv_guanzhu.setAdapter(myAdapter);
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

    class MyAdapter extends DefaultAdapter {
        public MyAdapter(List datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new WoDeHolder();
        }
    }
    private DbManager db;
    class WoDeHolder extends BaseHolder<FENSIBean.DataBean> {
        public ImageView ivusericon;
        public TextView tvusername;
        public TextView tvusercontent;
        public CheckBox cbguanzhu;

        @Override
        public View initView() {
            View root = View.inflate(SouSuoActivity.this, R.layout.layout_guanzhu_item, null);
            ivusericon = (ImageView) root.findViewById(R.id.iv_user_icon);
            tvusername = (TextView) root.findViewById(R.id.tv_user_name);
            tvusercontent = (TextView) root.findViewById(R.id.tv_user_content);
            cbguanzhu = (CheckBox) root.findViewById(R.id.cb_guanzhu);
            return root;
        }

        @Override
        public void refreshView(final FENSIBean.DataBean data, int position) {
            tvusername.setText(data.getName());
            //    Toast.makeText(context, data.getName(), Toast.LENGTH_SHORT).show();
            tvusercontent.setText(data.getQianming());
            x.image().bind(ivusericon, URLProvider.BaseImgUrl + data.getImage(), MyApplication.options);
            cbguanzhu.setChecked(data.getGuanzhu() == 1 ? true : false);
            cbguanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbManager.DaoConfig daoConfig = XUtils.getDaoConfig();
                    db = x.getDb(daoConfig);
                    if (cbguanzhu.isChecked()) {
                        addordelguanzhu(URLProvider.ADD_GUANZHU, data.getUid());

                        PersonTable person = new PersonTable();
                        person.setId(Integer.parseInt(data.getUid()));
                        person.setName(data.getName());
                        person.setImg(URLProvider.BaseImgUrl+data.getImage());
                        try {
                            db.saveOrUpdate(person);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {
                        addordelguanzhu(URLProvider.DEL_GUANZHU, data.getUid());
                        try {
                            db.deleteById(PersonTable.class, data.getUid());
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }

                private void addordelguanzhu(String url, String uid) {
                  //  Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("uid", SPUtil.getInt("uid") + "");
                    map.put("g_uid", uid);
                    Toast.makeText(context, "uid" + SPUtil.getInt("uid") + "g_uid" + uid, Toast.LENGTH_SHORT).show();
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
