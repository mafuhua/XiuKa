package com.yuen.xiuka.utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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

}
