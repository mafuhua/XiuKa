package com.yuen.xiuka.fragment;

import android.view.View;
import android.widget.TextView;

import com.yuen.baselib.activity.BaseFragment;

import io.rong.imkit.RongIM;

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
        if (RongIM.getInstance() != null)
            RongIM.getInstance().startConversationList(getActivity());
    }
}
