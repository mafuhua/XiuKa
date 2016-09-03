package com.yuen.xiuka.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.utils.MyEvent;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.URLProvider;

import java.util.Locale;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.TextInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Bob on 15/8/18.
 * 会话页面
 */
public class ConversationActivity extends ActionBarActivity implements View.OnClickListener, RongIM.UserInfoProvider {

    private String mTargetId;


    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private String title;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        SysExitUtil.activityList.add(this);
        TextInputProvider textInputProvider = new TextInputProvider(RongContext.getInstance());
        RongIM.setPrimaryInputProvider(textInputProvider);
        InputProvider.ExtendProvider[] provider = {
                new ImageInputProvider(RongContext.getInstance()),//图片
                new CameraInputProvider(RongContext.getInstance()),//相机
        };

        RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);

        Intent intent = getIntent();

        getIntentDate(intent);

        isReconnect(intent);

        initView();
        EventBus.getDefault().post(
                new MyEvent(MyEvent.Event.REFRESH_HOUTAIDIAN));

        RongIM.setUserInfoProvider(this, true);
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {

         mTargetId = intent.getData().getQueryParameter("targetId");
        title = intent.getData().getQueryParameter("title");

        MyEvent myEvent = new MyEvent(MyEvent.Event.REFRESH_DIAN);
        myEvent.setmTargetId(mTargetId);
        EventBus.getDefault().post(
                myEvent);

        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        enterFragment(mConversationType, mTargetId);
        RongIM.getInstance().getRongIMClient().setConversationNotificationStatus(mConversationType, mTargetId, Conversation.ConversationNotificationStatus.NOTIFY, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {

            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus status) {
                Log.i("mafuhua", "SetConversationNotificationFragment------" + status);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("mafuhua", "SetConversation//---NotificationFragment");
            }
        });
    }


    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }


    /**
     * 判断消息是否是 push 消息
     */
    private void isReconnect(Intent intent) {


        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals("true")) {

                reconnect(SPUtil.getString("token"));
                Log.d("mafuhua", "/push或通知过来重来呢---------");
            } else {
                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {

                    reconnect(SPUtil.getString("token"));
                    Log.d("mafuhua", "/push重来呢---------");
                } else {
                    enterFragment(mConversationType, mTargetId);
                }
            }
        }
    }


    /**
     * 重连
     *
     * @param token
     */
    private void reconnect(String token) {

        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {

                    enterFragment(mConversationType, mTargetId);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
     /*   Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();*/
    }

    private void initView() {

        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fanhui:
                finish();
                break;
        }
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (PersonTable personTable : MainActivity.userinfomap.values()) {
           if (personTable!=null&&(personTable.getId()+"").equals(s)){
               return new UserInfo(personTable.getId() + "", personTable.getName() + "", Uri.parse(personTable.getImg()));
           }
        }
        return new UserInfo(SPUtil.getInt("uid") + "", SPUtil.getString("name"), Uri.parse(URLProvider.BaseImgUrl + SPUtil.getString("icon")));

    }

}
