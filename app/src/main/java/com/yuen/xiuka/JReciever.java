package com.yuen.xiuka;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.activity.Close;
import com.yuen.xiuka.activity.Pinlun2Activity;
import com.yuen.xiuka.beans.PushBean;
import com.yuen.xiuka.utils.MyEvent;
import com.yuen.xiuka.xiuquan.MyXiuQuanActivity;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/8/8.
 */
public class JReciever extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: ");

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");
            //    receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            openNotification(context, bundle);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }


    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        MyEvent myEvent = new MyEvent(MyEvent.Event.NOTIFICATION_PINGLUN);
        myEvent.setmPush(extras);
        EventBus.getDefault().post(myEvent);
        Log.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
      /*  if (extras.length()<3){
            Intent mIntent = new Intent(MyApplication.context, MainActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
            return;
        }*/
        Gson gson = new Gson();
        PushBean pushBean = gson.fromJson(extras, PushBean.class);
        if (pushBean.getTxt().getType().equals("2")) {
            if (pushBean.getTxt().getName().contains("13634870628")) {
                Intent mIntent = new Intent(MyApplication.context, Close.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SPUtil.saveString("close", "13634870628");
                context.startActivity(mIntent);
            } else {
                MyEvent myEvent = new MyEvent(MyEvent.Event.NOTIFICATION_PINGLUNDIAN);
                EventBus.getDefault().post(myEvent);
                Intent mIntent = new Intent(MyApplication.context, Pinlun2Activity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("data", pushBean.getTxt().getXid());
                context.startActivity(mIntent);
            }

        } else if (pushBean.getTxt().getType().equals("3")) {
            Intent mIntent = new Intent(MyApplication.context, MyXiuQuanActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mIntent.putExtra("id", pushBean.getTxt().getFabuid());
            mIntent.putExtra("name", pushBean.getTxt().getFabuname());
            context.startActivity(mIntent);
        }

    }
}
