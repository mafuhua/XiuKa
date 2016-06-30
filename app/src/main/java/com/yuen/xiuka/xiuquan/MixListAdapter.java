package com.yuen.xiuka.xiuquan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by DavidWang on 15/10/8.
 */
public class MixListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Mixinfo> data;
    private ImageBDInfo bdInfo;
    private MainActivity activity;
    private int ImagaId[] = {R.id.img_0, R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7, R.id.img_8};
    private XiuQuanFragment xiuquanFragment;

    public MixListAdapter(Context context, ArrayList<Mixinfo> data) {
        this.context = context;
        this.data = data;
        bdInfo = new ImageBDInfo();
        activity = (MainActivity) context;
        xiuquanFragment = (XiuQuanFragment) activity.getSupportFragmentManager().findFragmentByTag("xiuquanFragment");
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        Mixinfo info = data.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.mix_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoaders.setsendimg(info.userimg, holder.list_img);
        holder.username.setText(info.username);
        holder.usercontent.setText(info.content);
        holder.showimage.setVisibility(View.GONE);
        holder.gridview.setVisibility(View.GONE);
        if (info.data.size() == 1) {
            holder.showimage.setVisibility(View.VISIBLE);
            holder.gridview.setVisibility(View.GONE);

            ImageInfo imageInfo = info.data.get(0);
            float w = imageInfo.width;
            float h = imageInfo.height;
            float width = 0.0f;
            float height = 0.0f;
            if (w > h) {
                width = activity.getWindowManager().getDefaultDisplay().getWidth() - dip2px(120);
            } else if (w < h) {
                width = activity.getWindowManager().getDefaultDisplay().getWidth() / 2;
            } else if (w == h) {
                width = (float) (activity.getWindowManager().getDefaultDisplay().getWidth());
            }
            height = width * h / w;
            ImageLoaders.setsendimg(imageInfo.url, holder.showimage);
            holder.showimage.getLayoutParams().width = (int) width;
            holder.showimage.getLayoutParams().height = (int) height;
            holder.showimage.setOnClickListener(new SingleOnclick(position, holder.showimage));
        } else if (info.data.size() > 1) {
            holder.showimage.setVisibility(View.GONE);
            holder.gridview.setVisibility(View.VISIBLE);
            int a = info.data.size() / 3;
            int b = info.data.size() % 3;
            if (b > 0) {
                a++;
            }
            float width = (activity.getWindowManager().getDefaultDisplay().getWidth() - dip2px(40)) / 3;
            holder.gridview.getLayoutParams().height = (int) (a * width);

            for (int i = 0; i < 9; i++) {
                holder.imgview[i].setVisibility(View.GONE);
            }

            for (int i = 0; i < info.data.size(); i++) {
                ImageInfo imageInfo = info.data.get(i);
                holder.imgview[i].setVisibility(View.VISIBLE);
                holder.imgview[i].getLayoutParams().width = (int) width;
                holder.imgview[i].getLayoutParams().height = (int) width;
                ImageLoaders.setsendimg(imageInfo.url, holder.imgview[i]);
                holder.imgview[i].setOnClickListener(new GridOnclick(position, holder.imgview[i], i, holder.gridview));
            }
        }
        holder.list_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "list_img" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_zhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "tv_zhuanfa" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "tv_pinglun" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "tv_dianzan" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public class ViewHolder {
        ImageView list_img;
        TextView username;
        TextView tv_dianzan;
        TextView tv_pinglun;
        TextView add;
        TextView time;
        TextView tv_zhuanfa;
        TextView usercontent;
        GridLayout gridview;
        ImageView showimage;
        ImageView imgview[] = new ImageView[9];

        public ViewHolder(View rootView) {
            list_img = (ImageView) rootView.findViewById(R.id.listuserimg);
            username = (TextView) rootView.findViewById(R.id.username);
            usercontent = (TextView) rootView.findViewById(R.id.usercontent);
            add = (TextView) rootView.findViewById(R.id.add);
            time = (TextView) rootView.findViewById(R.id.time);
            showimage = (ImageView) rootView.findViewById(R.id.showimage);
            gridview = (GridLayout) rootView.findViewById(R.id.gridview);
            tv_zhuanfa = (TextView) rootView.findViewById(R.id.tv_zhuanfa);
            tv_pinglun = (TextView) rootView.findViewById(R.id.tv_pinglun);
            tv_dianzan = (TextView) rootView.findViewById(R.id.tv_dianzan);
            for (int i = 0; i < 9; i++) {
                imgview[i] = (ImageView) rootView.findViewById(ImagaId[i]);
            }

        }

    }

    class SingleOnclick implements View.OnClickListener {

        private int index;
        private ImageView imageView;

        public SingleOnclick(int index, ImageView imageView) {
            this.index = index;
            this.imageView = imageView;
        }

        @Override
        public void onClick(View v) {

            View c = xiuquanFragment.mixlist.getChildAt(0);
            int top = c.getTop();
            int firstVisiblePosition = xiuquanFragment.mixlist.getFirstVisiblePosition();

            float height = 0.0f;
            for (int i = 0; i < ((index + 1) - firstVisiblePosition); i++) {
                View view = xiuquanFragment.mixlist.getChildAt(i);
                height += view.getHeight();
            }
            bdInfo.x = imageView.getLeft();
            bdInfo.y = imageView.getTop() + height + top + xiuquanFragment.mixlist.getTop();
            bdInfo.width = imageView.getLayoutParams().width;
            bdInfo.height = imageView.getLayoutParams().height;
            Intent intent = new Intent(context, PreviewImage.class);
            ArrayList<ImageInfo> info = data.get(index).data;
            Log.e("1", info.toString());
            intent.putExtra("data", (Serializable) info);
            intent.putExtra("bdinfo", bdInfo);
            intent.putExtra("index", 0);
            intent.putExtra("type", 0);
            context.startActivity(intent);
        }
    }

    class GridOnclick implements View.OnClickListener {

        private int index;
        private int row;
        private ImageView imageView;
        private GridLayout gridLayout;

        public GridOnclick(int index, ImageView imageView, int row, GridLayout gridLayout) {
            this.index = index;
            this.imageView = imageView;
            this.gridLayout = gridLayout;
            this.row = row;
        }

        @Override
        public void onClick(View v) {
            View c = xiuquanFragment.mixlist.getChildAt(0);
            int top = c.getTop();
            int firstVisiblePosition = xiuquanFragment.mixlist.getFirstVisiblePosition();
            float height = 0.0f;
            for (int i = 0; i < ((index + 1) - firstVisiblePosition); i++) {
                View view = xiuquanFragment.mixlist.getChildAt(i);
                height += view.getHeight();
            }
            bdInfo.x = imageView.getLeft() + gridLayout.getLeft();
            bdInfo.y = gridLayout.getTop() + imageView.getTop() + height + top + xiuquanFragment.mixlist.getTop();
            bdInfo.width = imageView.getLayoutParams().width;
            bdInfo.height = imageView.getLayoutParams().height;
            Intent intent = new Intent(context, PreviewImage.class);
            ArrayList<ImageInfo> info = data.get(index).data;
            Log.e("1", info.toString());
            intent.putExtra("data", (Serializable) info);
            intent.putExtra("bdinfo", bdInfo);
            intent.putExtra("index", row);
            intent.putExtra("type", 3);
            context.startActivity(intent);
        }
    }


}
