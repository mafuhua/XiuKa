package com.yuen.xiuka.fragment;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.yuen.baselib.activity.BaseFragment;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.GongGaoActivity;
import com.yuen.xiuka.activity.WguanZhuConvertList;
import com.yuen.xiuka.beans.ConverTListViewHolder;
import com.yuen.xiuka.utils.MyUtils;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class XiaoXiFragment extends BaseFragment {

    private ListView converlist;
    private List<Conversation> conversationList;
    private List<Conversation> guanzhuList = new ArrayList<>();
    private HashMap<String, PersonTable> userinfomap = new HashMap<>();
    private List<Integer> guanzhuidList = new ArrayList<>();
    private List<Conversation> WguanzhuList = new ArrayList<>();
    private RongIMClientWrapper rongIMClient;
    private NewAdapter newAdapter;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(android.os.Message msg) {
            newAdapter.notifyDataSetChanged();
            return true;
        }
    });
    private DbManager db;

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
                    Intent intent = new Intent(getActivity(), WguanZhuConvertList.class);
                    intent.putExtra("list", (Serializable) WguanzhuList);
                    startActivity(intent);
                } else {
                    if (RongIM.getInstance() != null) {
                        Conversation conversation = guanzhuList.get(position - 2);
                        RongIM.getInstance().startPrivateChat(getActivity(), conversation.getTargetId(), "好友");
                    }
                }
            }
        });

        converlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clearDialog(guanzhuList.get(position - 2).getTargetId(), position);
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
                    userinfomap.put(personTable.getId() + "", personTable);
                    if (Integer.parseInt(conversation.getTargetId()) == (personTable.getId())) {
                        guanzhuList.add(conversation);
                        break;
                    } else if (j == persons.size() - 1) {
                        WguanzhuList.add(conversation);
                    }
                }
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
                try {
                    PersonTable person = new PersonTable();
                    person.setId(Integer.parseInt(userInfo.getUserId()));
                    person.setName(userInfo.getName());
                    person.setImg("jafjl");
                    db.saveOrUpdate(person);
                } catch (DbException e) {
                    e.printStackTrace();
                }

                rongIMClient = RongIM.getInstance().getRongIMClient();
                getListData();
                android.os.Message message1 = new android.os.Message();
                handler.sendMessage(message1);
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
        newAdapter.notifyDataSetChanged();
    }

    protected void clearDialog(final String targetId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认刪除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RongIM.getInstance().getRongIMClient().removeConversation(Conversation.ConversationType.PRIVATE, targetId);
                guanzhuList.remove(position - 2);
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

    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return guanzhuList == null ? 2 : guanzhuList.size() + 2;
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
            } else {
                return 2;
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
                    viewHolder.name.setText("未关注人的消息");
                    viewHolder.name.setTextSize(20);
                    viewHolder.time.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.icon.setBackgroundResource(R.drawable.weiguanzbhu);
                    break;
                case 2:
                    position -= 2;
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
                 /*   if (guanzhuList.get(position).getConversationTitle() == null) {
                        viewHolder.name.setText(guanzhuList.get(position).getSenderUserId());
                    } else {
                        viewHolder.name.setText(guanzhuList.get(position).getConversationTitle());
                        x.image().bind(viewHolder.icon, guanzhuList.get(position).getPortraitUrl(), MyApplication.optionscache);
                    }*/
                    viewHolder.name.setText(userinfomap.get(guanzhuList.get(position).getTargetId()).getName());
                    // x.image().bind(viewHolder.icon,userinfomap.get(guanzhuList.get(position).getTargetId()).getImg(), MyApplication.optionscache);
                    x.image().bind(viewHolder.icon, "http://192.168.0.123/xiuka/upload/avatar/201608/1470292503-16432.jpg", MyApplication.optionscache);

                    viewHolder.time.setText(MyUtils.formatTime(guanzhuList.get(position).getReceivedTime()));
                    break;
            }
            return convertView;
        }
    }


}
