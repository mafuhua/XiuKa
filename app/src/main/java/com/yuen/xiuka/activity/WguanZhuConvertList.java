package com.yuen.xiuka.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.ConverTListViewHolder;
import com.yuen.xiuka.utils.MyUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIMClientWrapper;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

public class WguanZhuConvertList extends AppCompatActivity {
    private Context context;
    private ListView converlist;
    private List<Conversation> conversationList;
    private ArrayList<Conversation> guanzhuList;
    private List<Conversation> WguanzhuList = new ArrayList<>();
    private RongIMClientWrapper rongIMClient;
    private NewAdapter newAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wguan_zhu_convert_list);
        context = this;
        Intent intent = getIntent();
         guanzhuList =intent.getParcelableArrayListExtra("list");
        initView();
     //   initData();
    }

    public void initView() {

        converlist = (ListView) findViewById(R.id.converstationlist);
        newAdapter = new NewAdapter();
        converlist.setAdapter(newAdapter);

        converlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else if (position == 1) {

                } else {
                    if (RongIM.getInstance() != null) {
                        Conversation conversation = guanzhuList.get(position - 2);
                        RongIM.getInstance().startPrivateChat(context, conversation.getTargetId(), "好友");
                    }
                }
            }
        });

        converlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clearDialog(guanzhuList.get(position - 2).getTargetId());
                return false;
            }
        });

    }


    public void initData() {
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                MessageContent messageContent = message.getContent();
                conversationList = rongIMClient.getConversationList();
                if (messageContent instanceof TextMessage) {//文本消息
                    TextMessage textMessage = (TextMessage) messageContent;
                    Log.d("mafuhua", "onReceived-TextMessage:jkj" + textMessage.getContent());
                    newAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }


    protected void clearDialog(final String targetId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确认刪除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                RongIM.getInstance().getRongIMClient().removeConversation(Conversation.ConversationType.PRIVATE, targetId);

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
            return guanzhuList == null ? 0 : guanzhuList.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ConverTListViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_converdationlsit, null);
                viewHolder = new ConverTListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ConverTListViewHolder) convertView.getTag();
            }
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
            if (guanzhuList.get(position).getConversationTitle() == null) {
                viewHolder.name.setText(guanzhuList.get(position).getSenderUserId());
            } else {
                viewHolder.name.setText(guanzhuList.get(position).getConversationTitle());

                x.image().bind(viewHolder.icon, guanzhuList.get(position).getPortraitUrl(), MyApplication.optionscache);

            }
            viewHolder.time.setText(MyUtils.formatTime(guanzhuList.get(position).getReceivedTime()));
            return convertView;
        }
    }
}


