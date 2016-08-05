package com.yuen.xiuka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.GongGaoBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;

import java.util.List;

public class GongGaoActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button btnFanhui;
    protected TextView tvTitlecontent;
    protected ListView gongaolist;
    private MyAdapter myAdapter;
    private List<GongGaoBean.DataBean> gongGaoBeanData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_gong_gao);
        initView();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_fanhui) {
            finish();
        }
    }

    private void initView() {
        btnFanhui = (Button) findViewById(R.id.btn_fanhui);
        btnFanhui.setOnClickListener(GongGaoActivity.this);
        tvTitlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        gongaolist = (ListView) findViewById(R.id.gongaolist);
        tvTitlecontent.setText("秀咖");

        initdata();
    }

    private void initdata() {
        XUtils.xUtilsGet(URLProvider.ANNOUNCEMENT, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GongGaoBean gongGaoBean = gson.fromJson(result, GongGaoBean.class);
                gongGaoBeanData = gongGaoBean.getData();
                myAdapter = new MyAdapter(gongGaoBeanData);
                gongaolist.setAdapter(myAdapter);
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
            return new ViewHolder();
        }
    }

    public class ViewHolder extends BaseHolder<GongGaoBean.DataBean> {
        public TextView content;
        public TextView shijian;

        @Override
        public View initView() {
            View root = View.inflate(GongGaoActivity.this, R.layout.item_gonggao, null);
            content = (TextView) root.findViewById(R.id.content);
            shijian = (TextView) root.findViewById(R.id.shijian);
            return root;
        }

        @Override
        public void refreshView(GongGaoBean.DataBean data, int position) {
            content.setText(data.getContent());
            shijian.setText(data.getData_time());
        }
    }
}
