package com.yuen.xiuka.lisetner;

import android.app.Activity;
import android.util.Log;

import com.yuen.xiuka.beans.Friend;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class MyReceiveMessageListener extends Activity implements RongIMClient.OnReceiveMessageListener {

    public static String targetId;
    public static List<Friend> userIdList = new ArrayList<>();
    public  List<String> IdList = new ArrayList<>();

    /**
     * 收到消息的处理。
     *
     * @param message 收到的消息实体。
     * @param left    剩余未拉取消息数目。
     * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
     */
    @Override
    public boolean onReceived(Message message, int left) {
        targetId = message.getTargetId();
        Log.d("mafuhua", "------------南方就是罚款------"+targetId);

        TextMessage content = (TextMessage) message.getContent();
       // Toast.makeText(this, "content:" + content, Toast.LENGTH_SHORT).show();
        content.getContent();

        Log.d("mafuhua", "message**:" + message.getTargetId());
      /*  XUtils.xUtilsGet(ContactURL.GET_FRIEND_INFO + targetId, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", "-----GET_FRIEND_INFO-----" + result);
                Gson gson = new Gson();
                FriendBean friendBean = gson.fromJson(result, FriendBean.class);
                FriendBean.DataBean data = friendBean.getData();
                Friend friend = new Friend();
                if (userIdList.size() < 1) {
                    friend.setUserId(MainActivity.userid);
                    friend.setUserName(MainActivity.username);
                    friend.setPortraitUri(MainActivity.shop_imgs);
                    userIdList.add(friend);
                }
                if (IdList.contains(targetId)){

                }else {
                    userIdList.add(new Friend(data.getUid(),data.getNickname(),data.getImg()));
                }
                //  userIdList.add(new Friend("359", "移动", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));
                IdList.add(targetId);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });*/

        return true;
    }

}