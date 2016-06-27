package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.BaseBean;
import com.yuen.xiuka.beans.FENSIBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
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
        initView();
    }

    @Override
    public void initView() {


        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        tv_sousuo = (EditText) findViewById(R.id.tv_sousuo);
        tv_sousuo.setOnClickListener(this);
        tv_quxiao = (TextView) findViewById(R.id.tv_quxiao);
        tv_quxiao.setOnClickListener(this);
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

    private void submit() {
        // validate
        String sousuo = tv_sousuo.getText().toString().trim();
        if (TextUtils.isEmpty(sousuo)) {
            Toast.makeText(this, "秀咖/昵称/标签不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("title", sousuo);

        search(map);

    }

    private void search(HashMap<String, String> map) {
        XUtils.xUtilsPost(URLProvider.SEARCH, map, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                FENSIBean fensiBean = gson.fromJson(result, FENSIBean.class);
                fensiBeanData = fensiBean.getData();
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                myAdapter = new MyAdapter(fensiBeanData);
                lv_guanzhu.setAdapter(myAdapter);
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
                    if (cbguanzhu.isChecked()) {
                        addordelguanzhu(URLProvider.ADD_GUANZHU, data.getUid());
                    } else {
                        addordelguanzhu(URLProvider.DEL_GUANZHU, data.getUid());

                    }
                }

                private void addordelguanzhu(String url, String uid) {
                    Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
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
