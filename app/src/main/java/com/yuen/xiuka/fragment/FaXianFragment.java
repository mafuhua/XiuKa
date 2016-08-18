package com.yuen.xiuka.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.adapter.BaseHolder;
import com.yuen.baselib.adapter.DefaultAdapter;
import com.yuen.xiuka.MYCityPickerActivity;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.SouSuoActivity;
import com.yuen.xiuka.activity.ZhuBoListActivity;
import com.yuen.xiuka.beans.BianQianBean;
import com.yuen.xiuka.beans.ImgBeans;
import com.yuen.xiuka.beans.ShouyeBean;
import com.yuen.xiuka.utils.MyEvent;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;
import com.yuen.xiuka.xiuquan.MyXiuQuanActivity;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/6/13.
 */
public class FaXianFragment extends BaseFragment implements View.OnClickListener {
    public static int mPosition;
    public static int mRCPosition = 0;
    private static String[] settingString = new String[]{"意见", "更新", "缓存", "中心", "我们", "退出", "意见", "更新", "缓存", "中心", "我们", "退出", "意见", "更新", "缓存", "中心", "我们", "退出"};
    // 图片资源ID
    //private final int[] imageIds = {R.drawable.tu1, R.drawable.tu2, R.drawable.tu3};
    private final int[] imageIds = {R.drawable.ssk};
    private RecyclerView mRcHomeHorizontal;
    private MyRCAdapter myRCAdapter;
    private List settingString2 = new ArrayList(Arrays.asList("推荐", "热门", "最新", "人气"));
    private Context context;
    private GridView gv_renqi;
    private ViewPager mVpHomepageDec;
    private TextView tv_gengduo;
    private TextView tv_gengduo1;
    private GridView gv_tuijian;
    private TextView tv_gengduo2;
    private GridView gv_xinren;
    private MyAdapter myAdapter1;
    private RelativeLayout tv_sousuo;
    private ShouyeBean shouyeBean;
    private MyAdapter myAdapter2;
    private MyAdapter myAdapter3;
    private BianQianBean bianQianBean;
    private GridView gv_remen;
    private TextView tv_gengduo3;
    private MyAdapter myAdapter4;
    /**
     * 自动切换是否开启
     */
    private boolean isRunning = false;
    private MyPagerAdapter myPagerAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            // 让viewPager 显示下一页
            if (isRunning && mVpHomepageDec.getCurrentItem() < myPagerAdapter.getCount() - 1) {
                mVpHomepageDec.setCurrentItem(mVpHomepageDec.getCurrentItem() + 1);
                handler.sendEmptyMessageDelayed(88, 3000);
            }
        }

        ;
    };
    private TextView quanguo;
    /**
     * 页面改变时，上一个页面的下标
     */
    private int lastPosition;
    private List<ImgBeans.DataBean> imgBeansData;
    private LinearLayout ll_point_group;
    private String eventAdd;

    @Override
    public void onStart() {
        super.onStart();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered) {
            EventBus.getDefault().register(this);
        }
    }

    public void getImg() {
        XUtils.xUtilsGet(URLProvider.LUNBO, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                // Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                ImgBeans imgBeans = gson.fromJson(result, ImgBeans.class);
                imgBeansData = imgBeans.getData();
                myPagerAdapter = new MyPagerAdapter();
                mVpHomepageDec.setAdapter(myPagerAdapter);
                addPoints();
                isRunning = true;
                handler.sendEmptyMessageDelayed(88, 3000);
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
    public void onDestroy() {
        super.onDestroy();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (registered) {
            EventBus.getDefault().unregister(this);//反注册EventBus
        }

    }

    public void onEventMainThread(MyEvent event) {
        MyEvent.Event eventEvent = event.getEvent();
        switch (eventEvent) {
            case REFRESH_ADD:
                eventAdd = event.getAdd();
                quanguo.setText(event.getAdd());
                getShouye(event.getAdd());
                Toast.makeText(getActivity(), "onEventMainThread收到了消息" + event.getAdd(), Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public View initView() {
        context = getActivity();
        View view = View.inflate(getActivity(), R.layout.layout_homefragment, null);
        mRcHomeHorizontal = (RecyclerView) view.findViewById(R.id.rc_home_horizontal);
        quanguo = (TextView) view.findViewById(R.id.quanguo);
        tv_sousuo = (RelativeLayout) view.findViewById(R.id.tv_sousuo);
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

        mVpHomepageDec = (ViewPager) view.findViewById(R.id.vp_homepage_dec);

        tv_gengduo = (TextView) view.findViewById(R.id.tv_gengduo);
        ll_point_group = (LinearLayout) view.findViewById(R.id.ll_point_group);
        tv_gengduo.setOnClickListener(this);
        tv_gengduo1 = (TextView) view.findViewById(R.id.tv_gengduo1);
        tv_gengduo1.setOnClickListener(this);
        gv_tuijian = (GridView) view.findViewById(R.id.gv_tuijian);
        tv_gengduo2 = (TextView) view.findViewById(R.id.tv_gengduo2);
        tv_gengduo2.setOnClickListener(this);
        tv_gengduo3 = (TextView) view.findViewById(R.id.tv_gengduo3);
        tv_gengduo3.setOnClickListener(this);
        quanguo.setOnClickListener(this);
        gv_xinren = (GridView) view.findViewById(R.id.gv_xinren);
        gv_remen = (GridView) view.findViewById(R.id.gv_remen);
        eventAdd = "全国";

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
        getImg();


        regListener();


        return view;
    }

    private void addPoints() {
        for (int i = 0; i < imgBeansData.size(); i++) {
            // 动态添加指示点
            ImageView point = new ImageView(getActivity());
            point.setBackgroundResource(R.drawable.point_bg); // 设置背景

            // 默认让第一个点是选中状态
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }

            // 布局参数 : 当布局添加子view 时， 布局参数一定要和布局的类型 匹配
            // 向线性布局中，添加子view时，一定要指定线性布局的布局参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, -2);

            layoutParams.leftMargin = 10; // 左边距，10象素
            layoutParams.topMargin = 5; // 上边距 ,5 象素

            ll_point_group.addView(point, layoutParams); // 添加至页面中之前准备好的布局
        }
    }

    private void regListener() {
        //给viewPager 添加页面改变的监听
        mVpHomepageDec.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            /**
             *  当页面选择发生改变时，调用此方法
             *  @param position 新选择的页面的下标
             */
            public void onPageSelected(int position) {
                position = position % imgBeansData.size(); // 防止集合下标越界
              /*
                //改变描述文字
                tvDesc.setText(imageDescriptions[position]);*/
                // 改变指示点
                // 上一个页面，灰点
                ll_point_group.getChildAt(lastPosition).setEnabled(false);
                // 找到对应下标的point ，并改变显示
                ll_point_group.getChildAt(position).setEnabled(true);
                lastPosition = position;// 为上一个页面赋值

            }

            @Override
            // 当页面滑动时，调用此方法
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            // 当页面的滑动状态发生改变时，
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        XUtils.xUtilsGet(URLProvider.BIAOQIAN, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                for (int i = 0; i < settingString2.size(); i++) {
                    if (i > 3) {
                        settingString2.remove(i);
                    }
                }
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
        getShouye("");
    }

    private void getShouye(String add) {
        HashMap<String, String> map = new HashMap<>();
        if (add.equals("全国")) {
            add = "";
        }
        map.put("add", add);
        XUtils.xUtilsPost(URLProvider.INDEX_LIST, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                shouyeBean = gson.fromJson(result, ShouyeBean.class);
                myAdapter1 = new MyAdapter(shouyeBean.getData1());
                myAdapter2 = new MyAdapter(shouyeBean.getData2());
                myAdapter3 = new MyAdapter(shouyeBean.getData3());
                myAdapter4 = new MyAdapter(shouyeBean.getData4());
                gv_renqi.setAdapter(myAdapter1);
                gv_tuijian.setAdapter(myAdapter2);
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
                intent.putExtra("add", eventAdd);
                intent.putExtra("type", settingString2.get(0).toString());
                startActivity(intent);

                break;
            case R.id.tv_gengduo1:
                intent.putExtra("add", eventAdd);
                intent.putExtra("type", settingString2.get(1).toString());
                startActivity(intent);

                break;
            case R.id.tv_gengduo2:
                intent.putExtra("add", eventAdd);
                intent.putExtra("type", settingString2.get(2).toString());
                startActivity(intent);

                break;
            case R.id.tv_gengduo3:
                intent.putExtra("add", eventAdd);
                intent.putExtra("type", settingString2.get(3).toString());
                startActivity(intent);

                break;
            case R.id.tv_sousuo:
                startActivity(SouSuoActivity.class);
                break;
            case R.id.quanguo:
                startActivity(MYCityPickerActivity.class);
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
        int count = listAdapter.getCount() / 3;
        if (count < 1) {
            count = 1;
        } else if (count > 1 && count < 2) {
            count = 2;
        }
        for (int i = 0, len = listAdapter.getCount(); i < count; i++) {
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
                //  viewHolder.tv_line.setVisibility(View.VISIBLE);
            } else {
                // viewHolder.tv_line.setVisibility(View.GONE);
                viewHolder.mTxt.setTextColor(Color.BLACK);
            }
            viewHolder.mTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = settingString2.get(i).toString();
                    Intent intent = new Intent(getActivity(), ZhuBoListActivity.class);
                    intent.putExtra("add", eventAdd);
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

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            // imageView.setScaleType(ImageView.ScaleType.FIT_XY);
           /* LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT);
            imageView .setLayoutParams(mParams);*/
            String uil = URLProvider.BaseImgUrl2 + "pictures/" + imgBeansData.get(position % imgBeansData.size()).getAd_img();
            x.image().bind(imageView, uil, MyApplication.optionsxq3);
            // x.image().bind(imageView, imageIds[position], options);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
