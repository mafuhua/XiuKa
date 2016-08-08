package com.yuen.xiuka.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.ConverTListViewHolder;
import com.yuen.xiuka.utils.MyUtils;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.WPersonTable;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIMClientWrapper;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;

public class WguanZhuConvertList extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ListView converlist;
    private List<Conversation> conversationList;
    private ArrayList<Conversation> guanzhuList;
    private List<Conversation> WguanzhuList = new ArrayList<>();
    private RongIMClientWrapper rongIMClient;
    private NewAdapter newAdapter;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wguan_zhu_convert_list);
        context = this;
        Intent intent = getIntent();
        guanzhuList = (ArrayList<Conversation>) intent.getSerializableExtra("list");
        initView();
        SysExitUtil.activityList.add(this);
        DbManager.DaoConfig daoConfig = XUtils.getDaoConfig();
        db = x.getDb(daoConfig);
        for (Conversation conversation : guanzhuList) {
            PersonTable personTable = MainActivity.userinfomap.get(conversation.getTargetId());
            if (personTable != null) {
                WPersonTable wperson = new WPersonTable();
                wperson.setId(personTable.getId());
                wperson.setName(personTable.getName());
                wperson.setImg(personTable.getImg());
                try {
                    db.saveOrUpdate(wperson);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

        }
        try {
            List<WPersonTable> wPersonTables = db.findAll(WPersonTable.class);
            if (wPersonTables==null||wPersonTables.size()<1)return;
            for (WPersonTable wPersonTable : wPersonTables) {
                PersonTable personTable = new PersonTable();
                personTable.setId(wPersonTable.getId());
                personTable.setImg(wPersonTable.getImg());
                personTable.setName(wPersonTable.getName());
                MainActivity.userinfomap.put(wPersonTable.getId()+"",personTable);
                newAdapter.notifyDataSetChanged();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

            /*    try {
                    db.saveOrUpdate(person);
                } catch (DbException e) {
                    e.printStackTrace();
                }*/
        //   initData();
    }

    public void initView() {
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        btn_fanhui.setOnClickListener(this);
        tv_titlecontent = (TextView) findViewById(R.id.tv_titlecontent);
        tv_titlecontent.setText("未关注人消息");
        converlist = (ListView) findViewById(R.id.converstationlist);
        newAdapter = new NewAdapter();
        converlist.setAdapter(newAdapter);

        converlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (RongIM.getInstance() != null) {
                    Conversation conversation = guanzhuList.get(position);

                    if (MainActivity.userinfomap.get(guanzhuList.get(position).getTargetId()) != null) {
                        RongIM.getInstance().startPrivateChat(context, conversation.getTargetId(),
                                MainActivity.userinfomap.get(guanzhuList.get(position).getTargetId()).getName());
                    } else {
                        RongIM.getInstance().startPrivateChat(context, conversation.getTargetId(), "");
                    }
                }

            }
        });

        converlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clearDialog(guanzhuList.get(position).getTargetId(), position);
                return false;
            }
        });

    }


    public void initData() {
      /*  RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
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
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fanhui:
                finish();
                break;
        }
    }

    protected void clearDialog(final String targetId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确认刪除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RongIM.getInstance().getRongIMClient().removeConversation(Conversation.ConversationType.PRIVATE, targetId);
                guanzhuList.remove(position);
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
                viewHolder.count.setVisibility(View.GONE);
            }
            if (MainActivity.userinfomap.get(guanzhuList.get(position).getTargetId()) != null) {
                viewHolder.name.setText(MainActivity.userinfomap.get(guanzhuList.get(position).getTargetId()).getName());
                x.image().bind(viewHolder.icon,MainActivity.userinfomap.get(guanzhuList.get(position).getTargetId()).getImg(), MyApplication.optionscache);

            } else {
                viewHolder.name.setText(guanzhuList.get(position).getTargetId());
            }
            viewHolder.time.setText(MyUtils.formatTime(guanzhuList.get(position).getReceivedTime()));
            return convertView;
        }
    }
}


