package com.yuen.xiuka.xiuquan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuen.baselib.utils.AppUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.baselib.utils.ToastUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.XiuQuanDataBean;
import com.yuen.xiuka.utils.URLProvider;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class PagersImgActivity extends com.yuen.xiuka.activity.BaseActivity {

    private ViewPager vp_images;
    private MyPagerAdapter myPagerAdapter;
    private PhotoView[] mImageViews;

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
        mImageViews = new PhotoView[data.size()];
        for (int i = 0; i < mImageViews.length; i++) {
            final PhotoView imageView = new PhotoView(this);
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
        public Object instantiateItem(ViewGroup container, final int position) {

            PhotoView imageView = mImageViews[position];

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Dialog dialog = new Dialog(PagersImgActivity.this, R.style.MyDialog);
                    //设置它的ContentView
                    dialog.setContentView(R.layout.task_saveimg_dialog);
                    dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            saveImg(URLProvider.BaseImgUrl + data.get(position).getImg());
                        }

                    });
                    dialog.show();


                    return true;
                }
            });

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

    private void saveImg(String url) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setAutoRename(true);
        requestParams.setSaveFilePath(Environment.getExternalStorageDirectory() + "/DCIM/Camera/img"+System.currentTimeMillis()+".jpg");
        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                boolean isSaveSuc = saveImageToSysPic(PagersImgActivity.this,result.getAbsolutePath());
                if(isSaveSuc)
                {
                    // 保存成功
                    ToastUtil.toastShortShow("图片保存成功");
                    return ;
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.toastShortShow("图片保存失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 保存图片到系统相册
     *
     * @param context
     *          上下文
     * @param picPath
     *          图片绝对路径
     *
     * @return 是否保存成功
     */
    public static boolean saveImageToSysPic(Context context, String picPath)
    {
        try
        {
            if(null != context && null != picPath && !picPath.trim().equals(""))
            {
                // 把文件插入到系统图库
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        picPath, "未知", null);

                // 通知图库更新
                Uri uri = Uri.parse(path);
                path = getFilePathByContentResolver(context,uri);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ path)));

                // path不为空，则说明返回了路径，插入成功
                if(null != path && !path.trim().equals(""))
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据uri获取保存路径
     * @param context
     * @param uri
     * @return
     */
    private static   String getFilePathByContentResolver(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        String filePath  = null;
        if (null == c) {
            throw new IllegalArgumentException(
                    "Query on " + uri + " returns null result.");
        }
        try {
            if ((c.getCount() != 1) || !c.moveToFirst()) {
            } else {
                filePath = c.getString(
                        c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            }
        } finally {
            c.close();
        }
        return filePath;
    }


}
