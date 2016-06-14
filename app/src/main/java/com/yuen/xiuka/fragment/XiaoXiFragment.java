package com.yuen.xiuka.fragment;

import android.view.View;
import android.widget.TextView;

import com.yuen.baselib.activity.BaseFragment;

/**
 * Created by Administrator on 2016/6/13.
 */
public class XiaoXiFragment extends BaseFragment{
    @Override
    public View initView() {
        TextView textView = new TextView(getActivity());
        textView.setText("XiaoXiFragment");
        return textView;
    }

    @Override
    public void initData() {

    }
}
