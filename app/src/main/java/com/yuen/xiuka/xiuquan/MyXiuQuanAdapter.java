package com.yuen.xiuka.xiuquan;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuen.baselib.utils.AppUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.PingLunActivity;
import com.yuen.xiuka.beans.MYXIUQUANBean;
import com.yuen.xiuka.utils.URLProvider;

import org.xutils.x;

import java.io.Serializable;
import java.util.List;

class MyXiuQuanAdapter extends BaseAdapter {
    private final int windowwidth;
    private List<MYXIUQUANBean.DataBean.ImageBean> imageBeanList;
    private Context context;
    private List<MYXIUQUANBean.DataBean> xiuquanBeanData;

    public MyXiuQuanAdapter(Context context, List<MYXIUQUANBean.DataBean> xiuquanBeanData) {
        this.context = context;
        this.xiuquanBeanData = xiuquanBeanData;
        windowwidth = AppUtil.getWidth(context);
        // windowwidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return xiuquanBeanData == null ? 0 : xiuquanBeanData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        XiuQuanViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.mix_view, null);
            viewHolder = new XiuQuanViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (XiuQuanViewHolder) convertView.getTag();
        }
        viewHolder.add.setText(xiuquanBeanData.get(position).getAdd());
        viewHolder.time.setText(xiuquanBeanData.get(position).getTime());
        viewHolder.username.setText(xiuquanBeanData.get(position).getName());
        viewHolder.usercontent.setText(xiuquanBeanData.get(position).getContent());
        viewHolder.tv_dianzan.setText(xiuquanBeanData.get(position).getZan());
        viewHolder.tv_pinglun.setText(xiuquanBeanData.get(position).getComments());
        viewHolder.tv_zhuanfa.setText(xiuquanBeanData.get(position).getShare());

        x.image().bind(viewHolder.listuserimg, URLProvider.BaseImgUrl + xiuquanBeanData.get(position).getImg(), MyApplication.options);
        //    Glide.with(context).load(URLProvider.BaseImgUrl + xiuquanBeanData.get(position).getImg()).centerCrop().error(R.drawable.cuowu).crossFade().into(viewHolder.listuserimg);
        imageBeanList = xiuquanBeanData.get(position).getImage();
          /*  if (position>0){
                imagepagerList = xiuquanBeanData.get(position - 1).getImage();
            }else {

            }*/

        if (imageBeanList.size() == 1) {
            viewHolder.showimage.setVisibility(View.VISIBLE);
            viewHolder.gridview.setVisibility(View.GONE);
            viewHolder.showimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                    List<MYXIUQUANBean.DataBean.ImageBean> image = xiuquanBeanData.get(position).getImage();
                    Intent intent = new Intent(context, PagersImgActivity.class);
                    intent.putExtra("data", (Serializable) image);
                    intent.putExtra("index", "0");
                    context.startActivity(intent);
                }
            });
            // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + imageBeanList.get(0).getImg(), viewHolder.showimage);
            x.image().bind(viewHolder.showimage, URLProvider.BaseImgUrl + imageBeanList.get(0).getImg(), MyApplication.optionsxq);
            // Glide.with(context).load(URLProvider.BaseImgUrl + imageBeanList.get(0).getImg()).centerCrop().error(R.drawable.cuowu).crossFade().into(viewHolder.showimage);
        } else {
            viewHolder.showimage.setVisibility(View.GONE);
            viewHolder.gridview.setVisibility(View.VISIBLE);
            int a = imageBeanList.size() / 3;
            int b = imageBeanList.size() % 3;
            if (b > 0) {
                a++;
            }
            float width = (windowwidth - AppUtil.dp2Px(context, 40)) / 3;
            viewHolder.gridview.getLayoutParams().height = (int) (a * width);

            for (int i = 0; i < 9; i++) {
                viewHolder.imgview[i].setVisibility(View.GONE);
            }

            for (int i = 0; i < imageBeanList.size(); i++) {
                MYXIUQUANBean.DataBean.ImageBean imageBean = imageBeanList.get(i);
                viewHolder.imgview[i].setVisibility(View.VISIBLE);
                viewHolder.imgview[i].getLayoutParams().width = (int) width;
                viewHolder.imgview[i].getLayoutParams().height = (int) width;
                // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + imageBean.getImg(), viewHolder.imgview[i]);
                x.image().bind(viewHolder.imgview[i], URLProvider.BaseImgUrl + imageBean.getImg(), MyApplication.optionsxq);
                //  Glide.with(context).load(URLProvider.BaseImgUrl + imageBean.getImg()).centerCrop().error(R.drawable.cuowu).crossFade().into(viewHolder.imgview[i]);
                viewHolder.imgview[i].setOnClickListener(new GridOnclick(position, viewHolder.imgview[i], imageBeanList, i, viewHolder.gridview));
            }
        }
        viewHolder.listuserimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(context, MyXiuQuanActivity.class);
                intent.putExtra("data", xiuquanBeanData.get(position));
                context.startActivity(intent);*/
                Toast.makeText(context, "list_img" + position, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.iv_zhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "tv_zhuanfa" + position, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.iv_pinlun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PingLunActivity.class);
                intent.putExtra("data", xiuquanBeanData.get(position));
                context.startActivity(intent);
                Toast.makeText(context, "tv_pinglun" + position, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.iv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "tv_dianzan" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    class GridOnclick implements View.OnClickListener {

        private List<MYXIUQUANBean.DataBean.ImageBean> imageBeanList;
        private int index;
        private int position;
        private ImageView imageView;
        private GridLayout gridLayout;

        public GridOnclick(int position, ImageView imageView, List<MYXIUQUANBean.DataBean.ImageBean> imageBeanList, int index, GridLayout gridLayout) {
            this.imageBeanList = imageBeanList;
            this.index = index;
            this.position = position;
            this.imageView = imageView;
            this.gridLayout = gridLayout;
        }

        @Override
        public void onClick(View v) {
            //    Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, PagersImgActivity.class);
            intent.putExtra("data", (Serializable) imageBeanList);
            intent.putExtra("index", index);
            context.startActivity(intent);
        }
    }
}
