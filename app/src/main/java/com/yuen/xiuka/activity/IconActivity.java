package com.yuen.xiuka.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuen.baselib.utils.ToastUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.utils.URLProvider;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class IconActivity extends AppCompatActivity {
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        Intent intent = getIntent();
        final String icon = intent.getStringExtra("icon");
        imageView = (ImageView) findViewById(R.id.imageView);
        x.image().bind(imageView, URLProvider.BaseImgUrl+icon, MyApplication.optionsxq2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(IconActivity.this, R.style.MyDialog);
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
                        saveImg(URLProvider.BaseImgUrl+icon);
                    }

                });
                dialog.show();


                return true;
            }
        });

    }
    private void saveImg(String url) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setAutoRename(true);
        requestParams.setSaveFilePath(Environment.getExternalStorageDirectory() + "/DCIM/Camera/img"+System.currentTimeMillis()+".jpg");
        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                boolean isSaveSuc = saveImageToSysPic(IconActivity.this,result.getAbsolutePath());
                if(isSaveSuc)
                {
                    // 保存成功
                    ToastUtil.toastShortShow("图片保存成功");
                    return ;
                }else {
                    ToastUtil.toastShortShow("图片保存失败");
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
