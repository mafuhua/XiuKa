package com.yuen.xiuka.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.google.gson.Gson;
import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.SouSuoActivity;
import com.yuen.xiuka.activity.ZhuBoListActivity;
import com.yuen.xiuka.beans.BianQianBean;
import com.yuen.xiuka.beans.ShouyeBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;
import com.yuen.xiuka.xiuquan.MyXiuQuanActivity;

import org.xutils.common.Callback;
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
    private List settingString2 = new ArrayList(Arrays.asList("推荐", "热门", "最新", "人气"));
    private Context context;
    private GridView gv_renqi;

    private ImageView iv_baoming;
    private TextView tv_gengduo;
    private TextView tv_gengduo1;
    private GridView gv_tuijian;
    private TextView tv_gengduo2;
    private GridView gv_xinren;
    private MyAdapter myAdapter1;
    private TextView tv_sousuo;
    private ShouyeBean shouyeBean;
    private MyAdapter myAdapter2;
    private MyAdapter myAdapter3;
    private BianQianBean bianQianBean;
    private GridView gv_remen;
    private TextView tv_gengduo3;
    private MyAdapter myAdapter4;

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
        mRcHomeHorizontal.setAdapter(myRCAdapter);

        gv_renqi.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return MotionEvent.ACTION_MOVE == event.getAction() ? true
                        : false;
            }
        });

        iv_baoming = (ImageView) view.findViewById(R.id.iv_baoming);
        x.image().bind(iv_baoming, "aa.jpg");
        iv_baoming.setOnClickListener(this);
        tv_gengduo = (TextView) view.findViewById(R.id.tv_gengduo);
        tv_gengduo.setOnClickListener(this);
        tv_gengduo1 = (TextView) view.findViewById(R.id.tv_gengduo1);
        tv_gengduo1.setOnClickListener(this);
        gv_tuijian = (GridView) view.findViewById(R.id.gv_tuijian);
        tv_gengduo2 = (TextView) view.findViewById(R.id.tv_gengduo2);
        tv_gengduo2.setOnClickListener(this);
        tv_gengduo3 = (TextView) view.findViewById(R.id.tv_gengduo3);
        tv_gengduo3.setOnClickListener(this);
        gv_xinren = (GridView) view.findViewById(R.id.gv_xinren);
        gv_remen = (GridView) view.findViewById(R.id.gv_remen);


        gv_renqi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MyXiuQuanActivity.class);
                intent.putExtra("id", shouyeBean.getData1().get(position).getUid() + "");
                intent.putExtra("name", shouyeBean.getData1().get(position).getName());
                startActivity(intent);
            }
        });
        gv_xinren.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MyXiuQuanActivity.class);
                intent.putExtra("id", shouyeBean.getData3().get(position).getUid() + "");
                intent.putExtra("name", shouyeBean.getData3().get(position).getName());
                startActivity(intent);
            }
        });
        gv_tuijian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MyXiuQuanActivity.class);
                intent.putExtra("id", shouyeBean.getData2().get(position).getUid() + "");
                intent.putExtra("name", shouyeBean.getData2().get(position).getName());
                startActivity(intent);
            }
        });
        gv_remen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MyXiuQuanActivity.class);
                intent.putExtra("id", shouyeBean.getData4().get(position).getUid() + "");
                intent.putExtra("name", shouyeBean.getData4().get(position).getName());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        XUtils.xUtilsGet(URLProvider.BIAOQIAN, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                bianQianBean = gson.fromJson(result, BianQianBean.class);
                for (int i = 0; i < bianQianBean.getData().size(); i++) {
                    BianQianBean.DataBean dataBean = bianQianBean.getData().get(i);
                    settingString2.add(dataBean.getName());
                }
                myRCAdapter.notifyDataSetChanged();
                // startActivity(ZhuBoListActivity.class);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        XUtils.xUtilsGet(URLProvider.INDEX_LIST, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                shouyeBean = gson.fromJson(result, ShouyeBean.class);
                myAdapter1 = new MyAdapter(shouyeBean.getData1());
                myAdapter2 = new MyAdapter(shouyeBean.getData2());
                myAdapter3 = new MyAdapter(shouyeBean.getData3());
                myAdapter4 = new MyAdapter(shouyeBean.getData4());
                gv_tuijian.setAdapter(myAdapter1);
                gv_renqi.setAdapter(myAdapter2);
                gv_xinren.setAdapter(myAdapter3);
                gv_remen.setAdapter(myAdapter4);

                setListViewHeightBasedOnChildren(gv_tuijian);
                setListViewHeightBasedOnChildren(gv_xinren);
                setListViewHeightBasedOnChildren(gv_renqi);
                setListViewHeightBasedOnChildren(gv_remen);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ZhuBoListActivity.class);
        switch (v.getId()) {
            case R.id.tv_gengduo:

                intent.putExtra("type", settingString2.get(0).toString());
                startActivity(intent);

                break;
            case R.id.tv_gengduo1:
                intent.putExtra("type", settingString2.get(1).toString());
                startActivity(intent);

                break;
            case R.id.tv_gengduo2:
                intent.putExtra("type", settingString2.get(2).toString());
                startActivity(intent);

                break;
            case R.id.tv_gengduo3:
                intent.putExtra("type", settingString2.get(3).toString());
                startActivity(intent);

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

    class MyRCAdapter extends RecyclerView.Adapter<MyRCAdapter.ViewHolder> {

        public int mrcPosition;
        private LayoutInflater mInflater;

        public MyRCAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getItemCount() {
            return settingString2.size();
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
            viewHolder.mTxt.setText(settingString2.get(i).toString());
            mrcPosition = i;
            //  Log.d("mafuhua", "mPosition****:" + mrcPosition);
            if (mrcPosition == mRCPosition) {
                viewHolder.mTxt.setTextColor(Color.RED);
                viewHolder.tv_line.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_line.setVisibility(View.GONE);
                viewHolder.mTxt.setTextColor(Color.BLACK);
            }
            viewHolder.mTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = settingString2.get(i).toString();
                    Intent intent = new Intent(getActivity(), ZhuBoListActivity.class);
                    intent.putExtra("type", type);
                    startActivity(intent);
                 //   Toast.makeText(context, type, Toast.LENGTH_SHORT).show();
                    myRCAdapter.notifyDataSetChanged();
                }
            });
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

    class MyAdapter extends DefaultAdapter<ShouyeBean.DataBean> {
        public MyAdapter(List datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }
    }

    public class ViewHolder extends BaseHolder<ShouyeBean.DataBean> {
        public TextView tvname;
        public TextView tvadd;
        public ImageView user_icon;

        @Override
        public View initView() {
            View root = View.inflate(context, R.layout.layout_grid_item, null);
            tvname = (TextView) root.findViewById(R.id.tv_name);
            user_icon = (ImageView) root.findViewById(R.id.user_icon);
            tvadd = (TextView) root.findViewById(R.id.tv_add);
            return root;
        }

        @Override
        public void refreshView(ShouyeBean.DataBean data, int position) {
            tvname.setText(data.getName());
            tvadd.setText(data.getAdd());
            x.image().bind(user_icon, URLProvider.BaseImgUrl + data.getImage(), MyApplication.optionsxq);
        }
    }
}
