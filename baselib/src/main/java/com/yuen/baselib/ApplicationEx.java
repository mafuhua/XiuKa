package com.yuen.baselib;

import android.app.Application;

import com.yuen.baselib.activity.MyActivityManager;

/**
 * Created by BaoHang on 15/3/23.
 * Description:
 * 进行系统初始化操作，且用于系统级的数据缓存，防止在应用运行过程中被app回收掉。在Application里面我们主要进行显示屏幕数据
 * ，系统SDK缓存目录，图片异步加载，友盟统计等初始化操作. <br/>
 */
public class ApplicationEx extends Application {

    private static ApplicationEx mApplication = null;
    /**
     * Activity管理器
     */
    private MyActivityManager mActivityManager = null;

    /**
     * 单例方法
     *
     * @return ApplicationEx
     */
    public static ApplicationEx getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mActivityManager = new MyActivityManager();

    }
    /**
     * 获取activity管理器
     *
     * @return CustomActivityManager
     */
    public MyActivityManager getActivityManager() {
        return mActivityManager;
    }

}
