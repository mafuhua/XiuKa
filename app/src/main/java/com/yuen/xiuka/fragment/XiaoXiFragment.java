package com.yuen.xiuka.fragment;

import android.view.View;
import android.widget.Button;

import com.yuen.baselib.activity.BaseFragment;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/6/13.
 */
public class XiaoXiFragment extends BaseFragment{
    @Override
    public View initView() {
        Button textView = new Button(getActivity());
        textView.setText("XiaoXiFragment");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(getActivity(), "456", "");
                }
            }
        });
        return textView;
    }

    @Override
    public void initData() {

    }
}
