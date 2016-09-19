package com.yuen.xiuka.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.GongGaoActivity;
import com.yuen.xiuka.activity.MainActivity;
import com.yuen.xiuka.activity.PinglunListActivity;
import com.yuen.xiuka.activity.WguanZhuConvertList;
import com.yuen.xiuka.beans.ConverTListViewHolder;
import com.yuen.xiuka.beans.MYBean;
import com.yuen.xiuka.beans.PushBean;
import com.yuen.xiuka.beans.Shuzi;
import com.yuen.xiuka.utils.MyEvent;
import com.yuen.xiuka.utils.MyUtils;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIMClientWrapper;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * Created by Administrator on 2016/6/13.
 */
public class XiaoXiFragment extends BaseFragment implements RongIM.UserInfoProvider {

    private ListView converlist;
    private List<Conversation> conversationList;
    private List<Conversation> guanzhuList = new ArrayList<>();

    private List<Integer> guanzhuidList = new ArrayList<>();
    private List<Conversation> WguanzhuList = new ArrayList<>();
    private RongIMClientWrapper rongIMClient;
    private NewAdapter newAdapter;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private DbManager db;
    private boolean unreadMessage;
    private boolean pinglundian;
    private MYBean.DataBean myBeanData;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(android.os.Message msg) {
            rongIMClient = RongIM.getInstance().getRongIMClient();
            getListData();
            for (int i = 0; i < WguanzhuList.size(); i++) {
                int unreadMessageCount = WguanzhuList.get(i).getUnreadMessageCount();
                if (unreadMessageCount > 0) {
                    unreadMessage = true;
                    break;
                }
            }
            UserInfo userInfo = msg.getData().getParcelable("user");
            if (userInfo != null && userInfo.getPortraitUri().toString().length() > 0) {
                PersonTable person = new PersonTable();
                person.setId(Integer.parseInt(userInfo.getUserId()));
                person.setName(userInfo.getName());
                person.setImg(userInfo.getPortraitUri().toString());
                MainActivity.userinfomap.put(userInfo.getUserId(), person);
                newAdapter.notifyDataSetChanged();
            /*    try {
                    db.saveOrUpdate(person);
                } catch (DbException e) {
                    e.printStackTrace();
                }*/
            } else {
                newAdapter.notifyDataSetChanged();

            }
            MyEvent myEvent = new MyEvent(MyEvent.Event.REFRESH_LIAOTIAN);
            myEvent.setGuanzhuList(WguanzhuList);
            EventBus.getDefault().post(
                    myEvent);
            return true;
        }
    });
    private int pinglunshuzi = 0;

    public void my(String targetId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", targetId);
        XUtils.xUtilsPost(URLProvider.MY, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MYBean myBean = gson.fromJson(result, MYBean.class);
                myBeanData = myBean.getData();
                PersonTable person = new PersonTable();
                person.setId(Integer.parseInt(myBeanData.getUid()));
                person.setName(myBeanData.getName());
                person.setImg(URLProvider.BaseImgUrl + myBeanData.getImage());
                MainActivity.userinfomap.put(myBeanData.getUid() + "", person);

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
        });

    }

    public void shuzi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        XUtils.xUtilsPost(URLProvider.SHUZI, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("XiaoXiFragment", result);
                Gson gson = new Gson();
                Shuzi shuzi = gson.fromJson(result, Shuzi.class);
                pinglunshuzi = shuzi.getShuzi();
                if (pinglunshuzi>0){
                    MyEvent myEvent3 = new MyEvent(MyEvent.Event.REFRESH_PINGLUNDIAN);
                    EventBus.getDefault().post(myEvent3);
                    SPUtil.saveInt("pinglun",1);
                }
                newAdapter.notifyDataSetChanged();
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
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (registered) {
            EventBus.getDefault().unregister(this);//反注册EventBus
        }

    }

    public void onEventMainThread(MyEvent event) {
        MyEvent.Event eventEvent = event.getEvent();
        switch (eventEvent) {
            case NOTIFICATION_PINGLUN:
                String getmPush = event.getmPush();
                Gson gson = new Gson();
                PushBean pushBean = gson.fromJson(getmPush, PushBean.class);
                if (pushBean.getTxt().getType().equals("2")) {
                    pinglundian = true;
                    newAdapter.notifyDataSetChanged();
                    shuzi();

                }

                //    initNotify(pushBean);
                break;
            case NOTIFICATION_PINGLUNDIAN:
                pinglundian = false;
                pinglunshuzi = 0;

               // Toast.makeText(getActivity(), "NOTIFICATION_PINGLUNDIAN", Toast.LENGTH_SHORT).show();
                newAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(
                        new MyEvent(MyEvent.Event.REFRESH_HOUTAIDIAN));
                SPUtil.saveInt("pinglun",0);
                //    initNotify(pushBean);
                break;

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View initView() {

        final View inflate = View.inflate(getActivity(), R.layout.layout_xiaoxi_fragment, null);
        converlist = (ListView) inflate.findViewById(R.id.converstationlist);
        btn_fanhui = (Button) inflate.findViewById(R.id.btn_fanhui);
        btn_fanhui.setVisibility(View.GONE);
        tv_titlecontent = (TextView) inflate.findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("消息");
        rongIMClient = RongIM.getInstance().getRongIMClient();
        DbManager.DaoConfig daoConfig = XUtils.getDaoConfig();
        db = x.getDb(daoConfig);
        getListData();
        newAdapter = new NewAdapter();
        converlist.setAdapter(newAdapter);

        converlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(GongGaoActivity.class);
                } else if (position == 1) {
                    pinglundian = false;
                    pinglunshuzi = 0;
                    Intent intent = new Intent(getActivity(), PinglunListActivity.class);
                    //   intent.putExtra("list", (Serializable) WguanzhuList);
                    startActivity(intent);
                } else if (position == 2) {
                    unreadMessage = false;
                    Intent intent = new Intent(getActivity(), WguanZhuConvertList.class);
                    intent.putExtra("list", (Serializable) WguanzhuList);
                    startActivity(intent);
                } else {
                    if (RongIM.getInstance() != null) {
                        Conversation conversation = guanzhuList.get(position - 3);
                        if (MainActivity.userinfomap.get(guanzhuList.get(position - 3).getTargetId()) != null) {
                            RongIM.getInstance().startPrivateChat(getActivity(), conversation.getTargetId(),
                                    MainActivity.userinfomap.get(guanzhuList.get(position - 3).getTargetId()).getName());
                        } else {
                            RongIM.getInstance().startPrivateChat(getActivity(), conversation.getTargetId(), "");
                        }
                    }
                }
            }
        });

        converlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clearDialog(guanzhuList.get(position - 3).getTargetId(), position);
                return false;
            }
        });
        return inflate;
    }

    private void getListData() {
        conversationList = rongIMClient.getConversationList();
        guanzhuList.clear();
        WguanzhuList.clear();
        try {
            List<PersonTable> persons = db.findAll(PersonTable.class);
            for (int i = 0; i < conversationList.size(); i++) {
                if (persons == null) {
                    break;
                }
                Conversation conversation = conversationList.get(i);
                for (int j = 0; j < persons.size(); j++) {
                    PersonTable personTable = persons.get(j);
                    if (Integer.parseInt(conversation.getTargetId()) == (personTable.getId())) {
                        guanzhuList.add(conversation);

                        break;
                    } else if (j == persons.size() - 1) {
                        WguanzhuList.add(conversation);
                    }
                }
                my(conversation.getTargetId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                MessageContent messageContent = message.getContent();
                UserInfo userInfo = messageContent.getUserInfo();
                //  android.os.Message message1 = new android.os.Message();
                //    message1.obj = userInfo;

                android.os.Message msg = android.os.Message.obtain();
                Bundle b = new Bundle();// 存放数据
                b.putParcelable("user", userInfo);
                msg.setData(b);
                handler.sendMessage(msg);

                if (messageContent instanceof TextMessage) {//文本消息
                    TextMessage textMessage = (TextMessage) messageContent;
                    Log.d("mafuhua", "onReceived-TextMessage:jkj" + textMessage.getContent());
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListData();
        shuzi();
        newAdapter.notifyDataSetChanged();
    }

    protected void clearDialog(final String targetId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认刪除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RongIM.getInstance().getRongIMClient().removeConversation(Conversation.ConversationType.PRIVATE, targetId);
                guanzhuList.remove(position - 3);
                newAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (int i = 0; i < MainActivity.userinfomap.size(); i++) {
            PersonTable personTable = MainActivity.userinfomap.get(i);
            return new UserInfo(personTable.getId() + "", personTable.getName() + "", Uri.parse(personTable.getImg()));
        }
        return new UserInfo(SPUtil.getInt("uid") + "", SPUtil.getString("name"), Uri.parse(URLProvider.BaseImgUrl + SPUtil.getString("icon")));
    }

    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return guanzhuList == null ? 3 : guanzhuList.size() + 3;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else if (position == 1) {
                return 1;
            } else if (position == 2) {
                return 2;
            } else {
                return 3;
            }

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ConverTListViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_converdationlsit, null);
                viewHolder = new ConverTListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ConverTListViewHolder) convertView.getTag();
            }

            switch (getItemViewType(position)) {
                case 0:
                    viewHolder.name.setText("秀咖");
                    viewHolder.name.setTextSize(20);
                    viewHolder.time.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.icon.setBackgroundResource(R.drawable.ka);
                    break;
                case 1:
                    viewHolder.name.setText("评论");
                    viewHolder.name.setTextSize(20);
                    viewHolder.time.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.icon.setBackgroundResource(R.drawable.pinglunlist);
                    if (pinglundian || pinglunshuzi > 0) {
                        viewHolder.count.setText(pinglunshuzi+"");
                        viewHolder.count.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.count.setVisibility(View.GONE);
                    }
                    break;
                case 2:
                    viewHolder.name.setText("未关注人的消息");
                    viewHolder.name.setTextSize(20);
                    viewHolder.time.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.icon.setBackgroundResource(R.drawable.weiguanzbhu);
                    if (unreadMessage) {
                        viewHolder.pinglundian.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.pinglundian.setVisibility(View.GONE);
                    }
                    break;
                case 3:
                    position -= 3;
                    TextMessage latestMessage;
                    if (guanzhuList.get(position).getLatestMessage() instanceof TextMessage) {
                        latestMessage = (TextMessage) guanzhuList.get(position).getLatestMessage();
                        viewHolder.content.setText(latestMessage.getContent() + "");
                    } else {
                        viewHolder.content.setText("");
                    }
                    if (guanzhuList.get(position).getUnreadMessageCount() < 1) {
                        viewHolder.count.setVisibility(View.GONE);
                    } else {
                        viewHolder.count.setText(guanzhuList.get(position).getUnreadMessageCount() + "");
                        viewHolder.count.setVisibility(View.VISIBLE);
                    }
                    if (MainActivity.userinfomap.get(guanzhuList.get(position).getTargetId()) != null) {
                        viewHolder.name.setText(MainActivity.userinfomap.get(guanzhuList.get(position).getTargetId()).getName());
                        x.image().bind(viewHolder.icon, MainActivity.userinfomap.get(guanzhuList.get(position).getTargetId()).getImg(), MyApplication.optionscache);
                    } else {
                        viewHolder.name.setText(guanzhuList.get(position).getTargetId());
                        x.image().bind(viewHolder.icon, "", MyApplication.optionscache);
                    }
                    // x.image().bind(viewHolder.icon, "http://192.168.0.123/xiuka/upload/avatar/201608/1470292503-16432.jpg", MyApplication.optionscache);
                    viewHolder.time.setText(MyUtils.formatTime(guanzhuList.get(position).getReceivedTime()));
                    break;
            }
            return convertView;
        }
    }


}
