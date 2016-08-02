package com.yuen.xiuka.fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuen.baselib.activity.BaseFragment;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.utils.MyUtils;

import org.xutils.x;

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
    private Context context = MyApplication.context;
    private ListView converlist;
    private List<Conversation> conversationList;
    private RongIMClientWrapper rongIMClient;
    private NewAdapter newAdapter;

    @Override
    public View initView() {
        View inflate = View.inflate(MyApplication.context, R.layout.layout_xiaoxi_fragment, null);
        converlist = (ListView) inflate.findViewById(R.id.converstationlist);

        rongIMClient = RongIM.getInstance().getRongIMClient();
        conversationList = rongIMClient.getConversationList();
        newAdapter = new NewAdapter();
        converlist.setAdapter(newAdapter);
        return inflate;
    }

    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return conversationList == null ? 2 : conversationList.size() + 2;
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

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_converdationlsit, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            switch (getItemViewType(position)) {
                case 0:
                    viewHolder.name.setText("秀咖");
                    viewHolder.name.setTextSize(20);
                    viewHolder.time.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    break;
                case 1:
                    viewHolder.name.setText("未关注人的消息");
                    viewHolder.name.setTextSize(20);
                    viewHolder.time.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    break;
                case 2:
                    position -= 2;
                    TextMessage latestMessage;
                    UserInfo userInfo;
                    if (conversationList.get(position).getLatestMessage() instanceof TextMessage) {
                        latestMessage = (TextMessage) conversationList.get(position).getLatestMessage();
                        userInfo = latestMessage.getUserInfo();
                        viewHolder.content.setText(latestMessage.getContent() + "");

                    } else {
                        MessageContent message = conversationList.get(position).getLatestMessage();
                        userInfo = message.getUserInfo();
                        viewHolder.content.setText("");

                    }

                    if (conversationList.get(position).getUnreadMessageCount() < 1) {
                        viewHolder.count.setVisibility(View.GONE);
                    } else {
                        viewHolder.count.setText(conversationList.get(position).getUnreadMessageCount() + "");
                        viewHolder.count.setVisibility(View.VISIBLE);
                    }
                    if (userInfo == null) {
                        viewHolder.name.setText(conversationList.get(position).getSenderUserId());
                    } else {
                        viewHolder.name.setText(userInfo.getName() + "");

                        x.image().bind(viewHolder.icon, userInfo.getPortraitUri().toString(), MyApplication.optionscache);

                    }
                    viewHolder.time.setText(MyUtils.formatTime(conversationList.get(position).getReceivedTime()) + "");
                    break;
            }


            return convertView;
        }
    }

    @Override
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

    public class ViewHolder {
        public View rootView;
        public ImageView icon;
        public TextView name;
        public TextView time;
        public TextView content;
        public TextView count;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.icon = (ImageView) rootView.findViewById(R.id.icon);
            this.name = (TextView) rootView.findViewById(R.id.name);
            this.time = (TextView) rootView.findViewById(R.id.time);
            this.content = (TextView) rootView.findViewById(R.id.content);
            this.count = (TextView) rootView.findViewById(R.id.count);
        }

    }
}
