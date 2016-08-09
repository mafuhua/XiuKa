package com.yuen.xiuka.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.BianQianBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class BiaoQianActivity extends AppCompatActivity {

    private TagContainerLayout mTagContainerLayout1;
    private BianQianBean bianQianBean;
    private List<BianQianBean.DataBean> bianQianBeanData;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biao_qian);
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("修改标签");
        mTagContainerLayout1 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout1);

        getData();


        mTagContainerLayout1.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Toast.makeText(BiaoQianActivity.this, "click-position:" + position + ", text:" + text,
                        Toast.LENGTH_SHORT).show();
                mTagContainerLayout1.setTagTextColor(Color.BLUE);

            }

            @Override
            public void onTagLongClick(final int position, String text) {

            }
        });

    }

    private void getData() {
        final List<String> list1 = new ArrayList<String>();

        XUtils.xUtilsGet(URLProvider.BIAOQIAN, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                bianQianBean = gson.fromJson(result, BianQianBean.class);
                bianQianBeanData = bianQianBean.getData();

                for (BianQianBean.DataBean dataBean : bianQianBeanData) {
                    String type = dataBean.getName();
                    list1.add(type);
                }
                mTagContainerLayout1.setTags(list1);

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
}
