package com.yuen.xiuka.beans;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuen.xiuka.R;

public class ConverTListViewHolder {
        public View rootView;
        public ImageView icon;
        public TextView name;
        public TextView time;
        public View pinglundian;
        public TextView content;
        public TextView count;

        public ConverTListViewHolder(View rootView) {
            this.rootView = rootView;
            this.icon = (ImageView) rootView.findViewById(R.id.icon);
            this.pinglundian = rootView.findViewById(R.id.pinglundian);
            this.name = (TextView) rootView.findViewById(R.id.name);
            this.time = (TextView) rootView.findViewById(R.id.time);
            this.content = (TextView) rootView.findViewById(R.id.content);
            this.count = (TextView) rootView.findViewById(R.id.count);
        }

    }