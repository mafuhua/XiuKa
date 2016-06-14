package com.yuen.baselib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public abstract class DefaultAdapter<T> extends BaseAdapter {
    //适配器的数据源
    private List<T> datas;

    public DefaultAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return (datas == null) ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            holder = getHolder();//调用initView方法，初始化contentView
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        // 获取position对应的数据
        T data = datas.get(position);
        // 将数据传递到holder内
      //  Log.d("mafuhua", "将数据传递到holder内"+position);
        holder.setDatas(data, position);
        return holder.getContentView();
    }
    /**
     * 获取当前对应的holder
     *
     * @return
     */
    public abstract BaseHolder getHolder();
}
