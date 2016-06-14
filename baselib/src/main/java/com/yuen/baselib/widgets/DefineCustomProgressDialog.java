package com.yuen.baselib.widgets;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.yuen.baselib.R;


/**
 * 
 * @ClassName: DefineCustomProgressDialog
 * @Description: TODO 自定义ProgressDialog
 * @author lxs
 */
public class DefineCustomProgressDialog extends Dialog {

	private static DefineCustomProgressDialog customProgressDialog = null;

	public DefineCustomProgressDialog(Context context) {
		super(context);
	}

	public DefineCustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static DefineCustomProgressDialog createDialog(Activity context) {
		// 实例化dialog并设将其样式设置为全屏的
		// customProgressDialog = new DefineCustomProgressDialog(context,
		// android.R.style.Theme_Translucent_NoTitleBar);
		customProgressDialog = new DefineCustomProgressDialog(context,
				R.style.style_customProgressDialog);
		customProgressDialog
				.setContentView(R.layout.layout_defineprogressdialog);

		return customProgressDialog;
	}

	/**
	 * 设置信息
	 * 
	 * @param strMessage
	 * @return
	 */
	public DefineCustomProgressDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) customProgressDialog
				.findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return customProgressDialog;
	}
}
