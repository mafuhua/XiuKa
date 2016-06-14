package com.yuen.xiuka;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.umeng.socialize.PlatformConfig;
import com.yuen.xiuka.galleryfinal.GlideImageLoader;
import com.yuen.xiuka.galleryfinal.GlidePauseOnScrollListener;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;


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
        PlatformConfig.setWeixin("wx576cf31829c5138b", "aca59d13d048f58e8747ea437f2a5e66");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("2005558614","91cd97d9e1db43afe8ccf878493dc7b7");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105467568", "pzCSBfkLFaVvAfmX");
        // QQ和Qzone appid appkey

        //建议在application中配置
        //设置主题
        // ThemeConfig theme = ThemeConfig.ORANGE;
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.RED)
                .setTitleBarTextColor(Color.WHITE)
                .setTitleBarIconColor(Color.WHITE)
                .setFabNornalColor(Color.RED)
                .setFabPressedColor(Color.RED)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.RED)
                .build();

      /*  ThemeConfig theme = new ThemeConfig.Builder()
                .build();*/
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
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

