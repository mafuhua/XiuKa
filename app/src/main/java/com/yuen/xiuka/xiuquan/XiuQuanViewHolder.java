package com.yuen.xiuka.xiuquan;

import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuen.xiuka.R;

public class XiuQuanViewHolder {
    public ImageView iv_dianzan;
    public ImageView listuserimg;
    public TextView username;
    public TextView usercontent;
    public TextView add;
    public TextView time;
    public ImageView showimage;
    public GridLayout gridview;
    public TextView tv_zhuanfa;
    public TextView tv_pinglun;
    public TextView tv_dianzan;
    public ImageView imgview[] = new ImageView[9];
    public ImageView iv_zhuanfa;
    public ImageView iv_pinlun;
    public int ImagaId[] = {R.id.img_0, R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7, R.id.img_8};

    public XiuQuanViewHolder(View rootView) {
        listuserimg = (ImageView) rootView.findViewById(R.id.listuserimg);
        username = (TextView) rootView.findViewById(R.id.username);
        usercontent = (TextView) rootView.findViewById(R.id.usercontent);
        add = (TextView) rootView.findViewById(R.id.add);
        time = (TextView) rootView.findViewById(R.id.time);
        showimage = (ImageView) rootView.findViewById(R.id.showimage);
        for (int i = 0; i < 9; i++) {
            imgview[i] = (ImageView) rootView.findViewById(ImagaId[i]);
        }
        gridview = (GridLayout) rootView.findViewById(R.id.gridview);
        tv_zhuanfa = (TextView) rootView.findViewById(R.id.tv_zhuanfa);
        iv_zhuanfa = (ImageView) rootView.findViewById(R.id.iv_zhuanfa);
        iv_pinlun = (ImageView) rootView.findViewById(R.id.iv_pinlun);
        iv_dianzan = (ImageView) rootView.findViewById(R.id.iv_dianzan);
        tv_pinglun = (TextView) rootView.findViewById(R.id.tv_pinglun);
        tv_dianzan = (TextView) rootView.findViewById(R.id.tv_dianzan);
    }

}
