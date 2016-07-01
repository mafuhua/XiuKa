package com.yuen.xiuka.xiuquan;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.XIUQUANBean;
import com.yuen.xiuka.utils.URLProvider;

import org.xutils.x;

import java.util.List;

public class PagersImgActivity extends com.yuen.xiuka.activity.BaseActivity {

    private ViewPager vp_images;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagers_img);
        initView();
    }
    private List<XIUQUANBean.DataBean.ImageBean> data;
    private int index = 0;
    @Override
    public void initView() {
        index = getIntent().getIntExtra("index", 0);
        data = (List<XIUQUANBean.DataBean.ImageBean>) getIntent().getSerializableExtra("data");
        vp_images = (ViewPager) findViewById(R.id.vp_images);
        myPagerAdapter = new MyPagerAdapter();
        vp_images.setAdapter(myPagerAdapter);
        vp_images.setCurrentItem(index);
    }

    @Override
    public void loadData() {

    }
    class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
         //   imageView.setLayoutParams(new LinearLayout.LayoutParams(AppUtil.dp2Px(PagersImgActivity.this, 10), AppUtil.dp2Px(PagersImgActivity.this, 10)));
           // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + data.get(position).getImg(),imageView);
            x.image().bind(imageView,URLProvider.BaseImgUrl + data.get(position).getImg(), MyApplication.optionsxq);
            container.addView(imageView);
            return imageView;
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
