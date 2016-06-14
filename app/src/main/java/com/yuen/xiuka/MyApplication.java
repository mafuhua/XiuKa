package com.yuen.xiuka;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;


/**
 * Created by Administrator on 2016/3/22.
 */
public class MyApplication extends Application {
    public static Context context;
    public static ImageOptions options;
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        context = this;
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);
        options = new ImageOptions.Builder()
                .setRadius(20)
                        // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
                        // 图片缩放模式
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)

                .build();

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
           // RongIM.init(this);
        }
    }
    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}

