package com.yuen.baselib.adapter;

import android.view.View;

/**
 * holder基类
 * @author wangdh
 * @param <T>
 *
 */
public abstract class BaseHolder<T> {

    /**
     * holder对应的视图（listview的子布局对应的视图）
     */
    private View contentView;
   
    public View getContentView(){
        return contentView;
    }
    
    public BaseHolder(){
        //listview子布局的内容视图
        contentView = initView();
        
        contentView.setTag(this);
    }
    /**
     * 初始化当前holder对应的布局
     * @return
     */
    public abstract View initView() ;

    /**
     * 传递数据
     * @param data:泛型
     */
    public void setDatas(T data, int position){
        refreshView(data,position);
    }
    /**
     * 刷新listview子布局内部的控件
     */
    public abstract void refreshView(T data, int position);
    
}