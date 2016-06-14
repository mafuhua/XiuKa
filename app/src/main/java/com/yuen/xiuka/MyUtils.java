package com.yuen.xiuka;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.image.ImageOptions;

import java.io.File;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MyUtils {
    public static ImageOptions options = new ImageOptions.Builder()
            //设置使用缓存
            .setUseMemCache(true)
            // 图片缩放模式
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .build();
    private static Toast toast = null;

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context  内容器实体
     * @param msg      提示文字所在资源id
     * @param duration 提示时间
     */
    public static void toastShow(Context context, String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static String getInputString(Context context, EditText editText, String msg) {
        if (editText == null) {
            return null;
        } else {
            String content = editText.getText().toString();
            if (TextUtils.isEmpty(content)) {
                toastShow(context, msg, Toast.LENGTH_SHORT);
                return "";
            }
            return content;
        }
    }

    /**
     * @param file
     * @Description 删除文件或文件夹
     */
    public static void deletefile(File file) {
        if (!file.exists()) {
            return; // 不存在直接返回
        }

        if (file.isFile()) {
            file.delete(); // 若是文件则删除后返回
            return;
        }

        // 若是目录递归删除后,并最后删除目录后返回
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete(); // 如果是空目录，直接删除
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deletefile(childFiles[i]); // 递归删除子文件或子文件夹
            }
            file.delete(); // 删除最后的空目录
        }
        return;
    }

}
