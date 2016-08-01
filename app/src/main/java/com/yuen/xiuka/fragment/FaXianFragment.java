package com.yuen.xiuka.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.SouSuoActivity;
import com.yuen.xiuka.activity.ZhuBoListActivity;
import com.yuen.xiuka.activity.ZhuBoXiangXiActivity;
import com.yuen.xiuka.utils.MyUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class FaXianFragment extends BaseFragment implements View.OnClickListener {
    public static int mPosition;
    public static int mRCPosition = 0;
    private static String[] settingString = new String[]{"意见", "更新", "缓存", "中心", "我们", "退出", "意见", "更新", "缓存", "中心", "我们", "退出", "意见", "更新", "缓存", "中心", "我们", "退出"};
    private RecyclerView mRcHomeHorizontal;
    private MyRCAdapter myRCAdapter;
    private List settingString2 = new ArrayList(Arrays.asList("意见", "更新", "缓存", "中心", "我们", "退出"));
    private Context context;
    private GridView gv_renqi;

    private ImageView iv_baoming;
    private TextView tv_gengduo;
    private TextView tv_gengduo1;
    private GridView gv_tuijian;
    private TextView tv_gengduo2;
    private GridView gv_xinren;
    private MyAdapter myAdapter;
    private TextView tv_sousuo;

    @Override
    public View initView() {
        context = getActivity();
        View view = View.inflate(getActivity(), R.layout.layout_homefragment, null);
        mRcHomeHorizontal = (RecyclerView) view.findViewById(R.id.rc_home_horizontal);
        tv_sousuo = (TextView) view.findViewById(R.id.tv_sousuo);
        tv_sousuo.setOnClickListener(this);
        gv_renqi = (GridView) view.findViewById(R.id.gv_renqi);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRcHomeHorizontal.setLayoutManager(linearLayoutManager);
        //设置适配器
        myRCAdapter = new MyRCAdapter(context);
        myRCAdapter.setOnItemClickLitener(new MyRCAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mRCPosition = position;
                // Log.d("mafuhua", "mrcPosition------:" + position);
                MyUtils.toastShow(context, settingString[position], Toast.LENGTH_SHORT);
                myRCAdapter.notifyDataSetChanged();
                startActivity(ZhuBoListActivity.class);

            }
        });
        mRcHomeHorizontal.setAdapter(myRCAdapter);
        myAdapter = new MyAdapter(settingString2);
        gv_renqi.setAdapter(myAdapter);

        gv_renqi.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return MotionEvent.ACTION_MOVE == event.getAction() ? true
                        : false;
            }
        });

        iv_baoming = (ImageView) view.findViewById(R.id.iv_baoming);
        x.image().bind(iv_baoming,"aa.jpg");
        iv_baoming.setOnClickListener(this);
        tv_gengduo = (TextView) view.findViewById(R.id.tv_gengduo);
        tv_gengduo.setOnClickListener(this);
        tv_gengduo1 = (TextView) view.findViewById(R.id.tv_gengduo1);
        tv_gengduo1.setOnClickListener(this);
        gv_tuijian = (GridView) view.findViewById(R.id.gv_tuijian);
        tv_gengduo2 = (TextView) view.findViewById(R.id.tv_gengduo2);
        tv_gengduo2.setOnClickListener(this);
        gv_xinren = (GridView) view.findViewById(R.id.gv_xinren);
        gv_xinren.setAdapter(myAdapter);
        gv_tuijian.setAdapter(myAdapter);
        setListViewHeightBasedOnChildren(gv_tuijian);
        setListViewHeightBasedOnChildren(gv_xinren);
        setListViewHeightBasedOnChildren(gv_renqi);
        gv_renqi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ZhuBoXiangXiActivity.class);
            }
        });
        gv_xinren.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ZhuBoXiangXiActivity.class);
            }
        });
        gv_tuijian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ZhuBoXiangXiActivity.class);
            }
        });
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_gengduo:
                startActivity(ZhuBoListActivity.class);
                break;
            case R.id.tv_gengduo1:
                startActivity(ZhuBoListActivity.class);
                break;
            case R.id.tv_gengduo2:
                startActivity(ZhuBoListActivity.class);
                break;
            case R.id.tv_sousuo:
                startActivity(SouSuoActivity.class);
                break;
        }
    }

    public void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < 2; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;//+ (listView.getHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    static class MyRCAdapter extends RecyclerView.Adapter<MyRCAdapter.ViewHolder> {

        public int mrcPosition;
        private LayoutInflater mInflater;
        private OnItemClickLitener mOnItemClickLitener;

        public MyRCAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public int getItemCount() {
            return settingString.length;
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.layout_home_recycle_textview,
                    viewGroup, false);

            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.mTxt = (TextView) view.findViewById(R.id.home_icon__item_text);
            viewHolder.tv_line = view.findViewById(R.id.tv_line);
            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.mTxt.setText(settingString[i]);
            mrcPosition = i;
            //  Log.d("mafuhua", "mPosition****:" + mrcPosition);
            if (mrcPosition == mRCPosition) {
                viewHolder.mTxt.setTextColor(Color.RED);
                viewHolder.tv_line.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_line.setVisibility(View.GONE);
                viewHolder.mTxt.setTextColor(Color.BLACK);
            }
            //如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
                    }
                });

            }
        }


        /**
         * ItemClick的回调接口
         *
         * @author zhy
         */
        public interface OnItemClickLitener {
            void onItemClick(View view, int position);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView mImg;
            TextView mTxt;
            View tv_line;

            public ViewHolder(View arg0) {
                super(arg0);
            }
        }

    }

    class MyAdapter extends DefaultAdapter {
        public MyAdapter(List datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }
    }

    public class ViewHolder extends BaseHolder<String> {
        public TextView tvname;
        public TextView tvadd;

        @Override
        public View initView() {
            View root = View.inflate(context, R.layout.layout_grid_item, null);
            tvname = (TextView) root.findViewById(R.id.tv_name);
            tvadd = (TextView) root.findViewById(R.id.tv_add);
            return root;
        }

        @Override
        public void refreshView(String data, int position) {

        }
    }
}
