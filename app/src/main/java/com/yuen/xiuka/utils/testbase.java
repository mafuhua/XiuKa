package com.yuen.xiuka.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class testbase extends AppCompatActivity {
    class base extends DefaultAdapter {
        public base(List datas) {
            super(datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = View.inflate(getApplicationContext(), R.layout.layout_guanzhu_item, null);
            }
            return super.getView(position, convertView, parent);
        }

        @Override
        public BaseHolder getHolder() {
            return null;
        }


        public class ViewHolder {
            public final ImageView ivusericon;
            public final TextView tvusername;
            public final TextView tvusercontent;
            public final CheckBox cbguanzhu;
            public final View root;

            public ViewHolder(View root) {
                ivusericon = (ImageView) root.findViewById(R.id.iv_user_icon);
                tvusername = (TextView) root.findViewById(R.id.tv_user_name);
                tvusercontent = (TextView) root.findViewById(R.id.tv_user_content);
                cbguanzhu = (CheckBox) root.findViewById(R.id.cb_guanzhu);
                this.root = root;
            }
        }
    }

}
