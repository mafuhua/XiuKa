package com.yuen.xiuka;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.activity.PhotoActivity;
import com.yuen.xiuka.utils.URLProvider;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.notification.PushNotificationMessage;

/**
 * Created by Administrator on 2016/7/28.
 */
public class RongCloudEvent implements
        RongIM.UserInfoProvider, RongIM.ConversationBehaviorListener,
        RongIMClient.ConnectionStatusListener, RongIMClient.OnReceivePushMessageListener{
    private static RongCloudEvent mRongCloudInstance;
    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {

            synchronized (RongCloudEvent.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new RongCloudEvent(context);
                }
            }
        }
    }
    private Context mContext;
    /**
     * 构造方法。
     *
     * @param context 上下文。
     */
    private RongCloudEvent(Context context) {
        mContext = context;
        initDefaultListener();

    }
    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static RongCloudEvent getInstance() {
        return mRongCloudInstance;
    }

    /**
     * RongIM.init(this) 后直接可注册的Listener。
     */
    private void initDefaultListener() {

        RongIM.setUserInfoProvider(this, true);//设置用户信息提供者。
        RongIM.setConversationBehaviorListener(this);//设置会话界面操作的监听器。

//        RongIM.setOnReceivePushMessageListener(this);//自定义 push 通知。
        //消息体内是否有 userinfo 这个属性
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.getInstance().setCurrentUserInfo(new UserInfo(SPUtil.getInt("uid")+"",SPUtil.getString("name"), Uri.parse(URLProvider.BaseImgUrl+SPUtil.getString("icon"))));
    }
    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus){

            case CONNECTED://连接成功。

                break;
            case DISCONNECTED://断开连接。

                break;
            case CONNECTING://连接中。

                break;
            case NETWORK_UNAVAILABLE://网络不可用。

                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线

                break;
        }
    }
    private static final String TAG = RongCloudEvent.class.getSimpleName();
    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        if (message.getContent() instanceof ImageMessage) {
            ImageMessage imageMessage = (ImageMessage) message.getContent();
            Intent intent = new Intent(context, PhotoActivity.class);

            intent.putExtra("photo", imageMessage.getLocalUri() == null ? imageMessage.getRemoteUri() : imageMessage.getLocalUri());
            if (imageMessage.getThumUri() != null)
                intent.putExtra("thumbnail", imageMessage.getThumUri());

            context.startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }





    @Override
    public boolean onReceivePushMessage(PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public UserInfo getUserInfo(String s) {
        return new UserInfo(SPUtil.getInt("uid")+"",SPUtil.getString("name"), Uri.parse(URLProvider.BaseImgUrl+SPUtil.getString("icon")));
    }
}
