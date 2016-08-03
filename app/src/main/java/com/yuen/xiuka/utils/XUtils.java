package com.yuen.xiuka.utils;

import android.os.Environment;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/24.
 */
public  class XUtils {
    public static void xUtilsGet(String url, Callback.CommonCallback commonCallback) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, commonCallback);
    }


    public static void xUtilsPost(String url, HashMap<String, String> map, Callback.CommonCallback commonCallback) {
        RequestParams params = new RequestParams(url);
        Iterator<?> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
            String key = entry.getKey().toString();
          //  Log.d("mafuhua", key);
            String val = entry.getValue().toString();
            params.addBodyParameter(key, val);

        }

        x.http().post(params, commonCallback);
    }

    static DbManager.DaoConfig daoConfig;
    public static DbManager.DaoConfig getDaoConfig(){
        File file=new File(Environment.getExternalStorageDirectory().getPath());
        if(daoConfig==null){
            daoConfig=new DbManager.DaoConfig()
                    .setDbName("shiyan.db")     // 不设置dbDir时, 默认存储在app的私有目录.
                    //.setDbDir(file)
                    .setDbVersion(1)
                    .setAllowTransaction(true)
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            // 开启WAL, 对写入加速提升巨大
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    })
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }
}
