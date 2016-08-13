package com.yuen.xiuka.xiuquan;

import android.GestureImageView;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuen.baselib.utils.AppUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.XiuQuanDataBean;
import com.yuen.xiuka.utils.URLProvider;

import java.util.List;

public class PagersImgActivity extends com.yuen.xiuka.activity.BaseActivity {

    private ViewPager vp_images;
    private MyPagerAdapter myPagerAdapter;
    private GestureImageView[] mImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagers_img);
        initView();
        SysExitUtil.activityList.add(this);
    }
    private List<XiuQuanDataBean.ImageBean> data;
    private int index = 0;
    @Override
    public void initView() {
        index = getIntent().getIntExtra("index", 0);
        data = (List<XiuQuanDataBean.ImageBean>) getIntent().getSerializableExtra("data");
        vp_images = (ViewPager) findViewById(R.id.vp_images);

        //将图片装载到数组中
        mImageViews = new GestureImageView[data.size()];
        for (int i = 0; i < mImageViews.length; i++) {
            final GestureImageView imageView = new GestureImageView(this);
            // imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mImageViews[i] = imageView;
        }

        myPagerAdapter = new MyPagerAdapter();
        vp_images.setAdapter(myPagerAdapter);
        vp_images.setCurrentItem(index);
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            GestureImageView imageView = mImageViews[position];
//            ImageLoader.getInstance().displayImage(urls.get(position), imageView, options);
            Glide.with(imageView.getContext())
                    .load(URLProvider.BaseImgUrl + data.get(position).getImg())
                    .override(AppUtil.getWidth(PagersImgActivity.this) / 2, AppUtil.getHeigth(PagersImgActivity.this) / 2)
                    .crossFade()
                    .fitCenter()
                    .into(imageView);
            container.addView(imageView);
            return imageView;


     /*       GestureImageView  imageView = new GestureImageView (context);

         //   imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
         //   imageView.setLayoutParams(new LinearLayout.LayoutParams(AppUtil.dp2Px(PagersImgActivity.this, 10), AppUtil.dp2Px(PagersImgActivity.this, 10)));
           // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + data.get(position).getImg(),imageView);
            x.image().bind(imageView,URLProvider.BaseImgUrl + data.get(position).getImg(), MyApplication.optionsxq2);
            container.addView(imageView);
            return imageView;*/
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return data==null?0: data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
