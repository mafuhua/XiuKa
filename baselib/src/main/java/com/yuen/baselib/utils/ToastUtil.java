package com.yuen.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuen.baselib.ApplicationEx;
import com.yuen.baselib.R;
import com.yuen.baselib.widgets.DefineCustomProgressDialog;


/**
 * 弹出框工具类
 */
public class ToastUtil {
    private static Toast toast = null;
    private static DefineCustomProgressDialog myprogressDialog = null;

    /**
     * @param text 提示文字
     */
    public static void toastShortShow(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            toastShow(ApplicationEx.getInstance(), text, Toast.LENGTH_SHORT);
        }
    }

    /**
     * @param textResId 提示文字所在的资源id
     */
    public static void toastShortShow(int textResId) {
        toastShow(ApplicationEx.getInstance(), textResId, Toast.LENGTH_SHORT);
    }

    /**
     * @param context 内容器实体
     * @param text    提示文字内容
     */
    public static void toastShortShow(Context context, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            toastShow(context, text, Toast.LENGTH_SHORT);
        }
    }

    /**
     * @param context   内容器实体
     * @param textResId 提示文字所在资源id
     */
    public static void toastShortShow(Context context, int textResId) {
        toastShow(context, textResId, Toast.LENGTH_SHORT);
    }

    /**
     * @param context   内容器实体
     * @param textResId 提示文字所在资源id
     * @param duration  提示时间
     */
    public static void toastShow(Context context, int textResId, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, textResId, duration);
        } else {
            toast.setText(textResId);
        }
        toast.show();
    }

    /**
     * @param context  内容器实体
     * @param msg      提示消息内容
     * @param duration 提示时间
     */
    public static void toastShow(Context context, CharSequence msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showMessage(String toastContent, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // 根据指定的布局文件创建一个具有层级关系的View对象
        View layout = inflater.inflate(R.layout.layout_toast, null);
        LinearLayout root = (LinearLayout) layout
                .findViewById(R.id.toast_layout_root);
        root.getBackground().setAlpha(200);// 0~255透明度值

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(toastContent);

        Toast toast = new Toast(context);
        // 设置Toast的位置
        //		 toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        // toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        // 让Toast显示为我们自定义的样子
        toast.setView(layout);
        toast.show();
    }

    /**
     * 显示loading
     */
    public static void startProgressDialog(Activity context, String message) {
        myprogressDialog = DefineCustomProgressDialog.createDialog(context);
        myprogressDialog.setMessage(message);
        myprogressDialog.show();
    }

    /**
     * 关闭loading
     */
    public static void stopProgressDialog() {
        if (myprogressDialog != null && myprogressDialog.isShowing()) {
            myprogressDialog.dismiss();
            myprogressDialog = null;
        }
    }
}
