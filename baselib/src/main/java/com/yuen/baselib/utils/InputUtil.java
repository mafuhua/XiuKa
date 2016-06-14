package com.yuen.baselib.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by WangXY on 2015/10/22.20:49.
 */
public class InputUtil {

    /**
     * 打开输入法键盘
     *
     * @param editText 输入框控件
     */
    public static void showInputMethodView(Context context, EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm) {
            imm.showSoftInput(editText, 0);
        }
    }

    public static void showInputMethodView(Context context,
                                           final EditText editText, long millseconds) {
        editText.requestFocus();
        final InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    imm.showSoftInput(editText, 0);
                }
            }, millseconds);
        }
    }

    /**
     * 关闭输入法键盘
     *
     * @param editText 输入框控件
     */
    public static void hideInputMethdView(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static String getInputString(EditText editText) {
        if (editText == null) {
            return null;
        } else {
            return editText.getText().toString();
        }

    }

    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }
}
