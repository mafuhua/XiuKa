package com.yuen.baselib;

import android.widget.ListView;

import com.yuen.baselib.activity.BaseActivity;

public class MainActivity extends BaseActivity {


    ListView listView;

    /**
     * @param layoutResID 界面资源文件id
     */
    public MainActivity() {
        super(R.layout.activity_main);

    }

    @Override
    public void findIds() {
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void initView() {

    }

    @Override
    public void loadData() {

    }


}
