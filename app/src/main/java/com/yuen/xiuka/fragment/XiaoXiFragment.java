package com.yuen.xiuka.fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuen.baselib.activity.BaseFragment;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.utils.MyUtils;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
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
    private List<Conversation> guanzhuList = new ArrayList<>();
    private List<Conversation> WguanzhuList = new ArrayList<>();
    private RongIMClientWrapper rongIMClient;
    private NewAdapter newAdapter;

    @Override
    public View initView() {
        View inflate = View.inflate(getActivity(), R.layout.layout_xiaoxi_fragment, null);
        converlist = (ListView) inflate.findViewById(R.id.converstationlist);

        rongIMClient = RongIM.getInstance().getRongIMClient();
        conversationList = rongIMClient.getConversationList();

        /**
         * findAll的使用
         该方法主要是返回当前表里面的所有数据
         需求:查找person表里面的所有数据
         */
        try {
            DbManager.DaoConfig daoConfig = XUtils.getDaoConfig();
            DbManager db = x.getDb(daoConfig);
            List<PersonTable>  persons = db.findAll(PersonTable.class);



            for (int i = 0; i < conversationList.size(); i++) {
                if (persons==null){
                    break;
                }
                for (PersonTable personTable : persons) {
                    Log.e("persons", personTable.toString());
                    Conversation conversation = conversationList.get(i);
                    if (Integer.parseInt(conversation.getTargetId())==(personTable.getId())){
                        guanzhuList.add(conversation);
                    } else{
                        WguanzhuList.add(conversation);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        newAdapter = new NewAdapter();
        converlist.setAdapter(newAdapter);

        converlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else if (position == 1) {
                } else {
                    if (RongIM.getInstance() != null) {
                        Conversation conversation = conversationList.get(position - 2);
                        UserInfo userInfo = conversation.getLatestMessage().getUserInfo();
                        //  Uri aPrivate = RongContext.getInstance().getConversationTemplate("private").getPortraitUri(conversation.getTargetId());

                        RongIM.getInstance().startPrivateChat(getActivity(), conversation.getTargetId(), userInfo.getName()+"");
                    }
                }
            }
        });
        return inflate;
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
                convertView = View.inflate(getActivity(), R.layout.item_converdationlsit, null);
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
                    if (conversationList.get(position).getConversationTitle() == null) {
                        viewHolder.name.setText(conversationList.get(position).getSenderUserId());
                    } else {
                        viewHolder.name.setText(conversationList.get(position).getConversationTitle());

                        x.image().bind(viewHolder.icon, conversationList.get(position).getPortraitUrl(), MyApplication.optionscache);

                    }
                    viewHolder.time.setText(MyUtils.formatTime(conversationList.get(position).getReceivedTime()));
                    break;
            }


            return convertView;
        }
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
