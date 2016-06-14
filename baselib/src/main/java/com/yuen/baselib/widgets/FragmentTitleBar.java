/**
 * Project Name:TX
 * Package Name:com.threeti.tx.utils.widget
 * File Name:FragmentTitleBar.java
 * Function: TODO 〈一句话功能简述〉. <br/>
 * Description: TODO 〈功能详细描述〉. <br/>
 * Date:2014年10月11日下午2:28:36
 * Copyright:Copyright (c) 2014, 翔傲科技（上海）有限公司 All Rights Reserved.
 */
package com.yuen.baselib.widgets;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuen.baselib.R;


/**
 * ClassName:FragmentTitleBar
 * Description:标题栏. <br/>
 * Date:2014年10月11日
 */
public class FragmentTitleBar {
    private Activity mActivity;
    private ImageView im_left;
    private ImageView im_right;
    private ImageView im_logo;
    private TextView tv_title;
    private TextView tv_right;
    private RelativeLayout rl_title;

    public FragmentTitleBar(Activity activity) {
        this.mActivity = activity;
        initViews();
        initSize();
        initEvents();
    }

    private void initViews() {
        im_left = (ImageView) mActivity.findViewById(R.id.im_left);
        im_right = (ImageView) mActivity.findViewById(R.id.im_right);
        tv_title = (TextView) mActivity.findViewById(R.id.tv_title);
        im_logo = (ImageView) mActivity.findViewById(R.id.im_logo);
        rl_title = (RelativeLayout) mActivity.findViewById(R.id.rl_title);
        tv_right = (TextView) mActivity.findViewById(R.id.tv_right);
        hideLeft();
    }

    public void initSize() {

    }

    public void setLeftIcon(int resid, OnClickListener listener) {
        im_left.setVisibility(View.VISIBLE);
        im_left.setImageResource(resid);
        if (listener != null)
            im_left.setOnClickListener(listener);
    }

    public void setRightIcon(int resid, OnClickListener listener) {
        im_right.setVisibility(View.VISIBLE);
        im_right.setImageResource(resid);
        im_right.setOnClickListener(listener);
    }

    public void setBackground(int resid) {
        rl_title.setBackgroundResource(resid);
    }

    public void setBackgroundColor(String color) {
        rl_title.setBackgroundColor(Color.parseColor(color));
    }

    private void initEvents() {
        im_left.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    public void setTitle(int resid) {
        tv_title.setText(resid);
    }

    public void setTitle(String str) {
        tv_title.setText(str);
    }

    public TextView getCenterText() {
        return this.tv_title;
    }

    public void showLeft() {
        im_left.setVisibility(View.VISIBLE);
    }

    public void hideLeft() {
        im_left.setVisibility(View.GONE);
    }

    public ImageView getRight() {
        im_right.setVisibility(View.VISIBLE);
        return this.im_right;
    }


    public void hideRight() {
        im_right.setVisibility(View.INVISIBLE);
    }

    public void setVisibility(int visibility) {
        rl_title.setVisibility(visibility);
    }
    public void setGone(int gone) {
        rl_title.setVisibility(gone);
    }

    public int getLeftId() {
        return im_left.getId();
    }

    public int getRightId() {
        return im_right.getId();
    }

    public int getRightTextId() {
        return tv_right.getId();
    }

    public TextView getRightText() {
        return tv_right;
    }


    public void setRightText(int resid) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(resid);
    }

    public void setRightText(String str) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(str);
    }
}
