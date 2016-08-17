package com.yuen.xiuka.activity;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.utils.AppUtil;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.beans.FENSIBean;
import com.yuen.xiuka.beans.TokenBean;
import com.yuen.xiuka.fragment.FaXianFragment;
import com.yuen.xiuka.fragment.FragmentFractory;
import com.yuen.xiuka.fragment.WoDeFragment;
import com.yuen.xiuka.fragment.XiaoXiFragment;
import com.yuen.xiuka.utils.MyEvent;
import com.yuen.xiuka.utils.MyUtils;
import com.yuen.xiuka.utils.PersonTable;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;
import com.yuen.xiuka.xiuquan.XiuQuanFragment2;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIMClientWrapper;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static HashMap<String, PersonTable> userinfomap = new HashMap<>();
    private FragmentManager supportFragmentManager;
    private FrameLayout fl_home_content;
    private RadioButton rb_home_faxian;
    private RadioButton rb_home_xiaoxi;
    private RadioButton rb_home_fabu;
    private RadioButton rb_home_xiuquan;
    private RadioButton rb_home_wode;
    private RadioGroup rg_home;
    private FaXianFragment faxianFragment;
    private XiaoXiFragment xiaoxiFragment;
    private XiuQuanFragment2 xiuquanFragment;
    private WoDeFragment woDeFragment;
    private Fragment currentFragment;
    private FragmentTransaction transaction;
    private ConversationListFragment listfragment;
    private List<Conversation> conversationList;
    private RongIMClientWrapper rongIMClient;
    private NewAdapter newAdapter;
    private int currentcheck;
    private DbManager db;
    private List<PersonTable> persons;
    private View xiaoxidian;


    private RelativeLayout rlTankuang;
    private ImageView ivYugao;
    private ImageView ivbFabu;
    private TextView yugao;
    private ImageView ivQuxiao;
    private TextView fabu;
    private PopupWindow popupWindow;
    private View contentview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState = null;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JPushInterface.setAlias(context, SPUtil.getInt("uid") + "", null);
        SysExitUtil.activityList.add(this);
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered) {
            EventBus.getDefault().register(this);
        }
        rongConnect(SPUtil.getString("token"));
        DbManager.DaoConfig daoConfig = XUtils.getDaoConfig();
        db = x.getDb(daoConfig);
        try {
            persons = db.findAll(PersonTable.class);
            if (persons == null) {

            } else {
                for (PersonTable person : persons) {
                    Log.d("MyApplication", "MyApplication-----" + person.toString());
                    userinfomap.put(person.getId() + "", person);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        initView();
        loadData();

    }


    public void onEventMainThread(MyEvent event) {
        MyEvent.Event eventEvent = event.getEvent();
        switch (eventEvent) {
            case GET_TOKEN:
                getToken();
                break;
            case REFRESH_LIAOTIAN:
                int visibility = xiaoxidian.getVisibility();
                if (visibility == View.GONE && currentcheck != R.id.rb_home_xiaoxi) {
                    xiaoxidian.setVisibility(View.VISIBLE);
                }
                //   Toast.makeText(this, "onEventMainThread收到了消息", Toast.LENGTH_LONG).show();
                break;
        }
       /* String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);

        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (registered) {
            EventBus.getDefault().unregister(this);//反注册EventBus
        }

    }

    private void rongConnect(String token) {
        /**
         * IMKit SDK调用第二步
         *
         * 建立与服务器的连接
         *
         */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
                //     Toast.makeText(context, " 失效的状态处理，需要重新获取 Token", Toast.LENGTH_SHORT).show();
                //   Log.e("MainActivity", "——Connect Token— -" + "失效的状态处理，需要重新获取 Token");

                EventBus.getDefault().post(
                        new MyEvent(MyEvent.Event.GET_TOKEN));
                //

            }


            @Override
            public void onSuccess(String userId) {
                //     Toast.makeText(context, "——onSuccess— -" + userId, Toast.LENGTH_SHORT).show();
                //  Log.e("MainActivity", "——onSuccess— -" + userId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                //  Toast.makeText(context, "——onError— -" + errorCode, Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "——onError— -" + errorCode);
            }
        });
    }

    @Override
    public void initView() {

        fl_home_content = (FrameLayout) findViewById(R.id.fl_home_content);
        fl_home_content.setOnClickListener(this);
        rb_home_faxian = (RadioButton) findViewById(R.id.rb_home_faxian);
        rb_home_faxian.setOnClickListener(this);
        rb_home_xiaoxi = (RadioButton) findViewById(R.id.rb_home_xiaoxi);
        rb_home_xiaoxi.setOnClickListener(this);
        rb_home_fabu = (RadioButton) findViewById(R.id.rb_home_fabu);
        rb_home_fabu.setOnClickListener(this);
        rb_home_xiuquan = (RadioButton) findViewById(R.id.rb_home_xiuquan);
        rb_home_xiuquan.setOnClickListener(this);
        rb_home_wode = (RadioButton) findViewById(R.id.rb_home_wode);
        rb_home_wode.setOnClickListener(this);


        rg_home = (RadioGroup) findViewById(R.id.rg_home);
        xiaoxidian = findViewById(R.id.xiaoxidian);
        rg_home.setOnClickListener(this);
      /*  ivYugao.setOnClickListener(this);
        ivbFabu.setOnClickListener(this);
        ivQuxiao.setOnClickListener(this);*/
        Drawable drawable = getResources().getDrawable(R.drawable.faxian);
        int dp = MyUtils.dip2px(context, 30);
        drawable.setBounds(0, 0, dp, dp);
        rb_home_faxian.setCompoundDrawables(null, drawable, null, null);
        Drawable drawable1 = getResources().getDrawable(R.drawable.xiaoxi);
        drawable1.setBounds(0, 0, dp, dp);
        rb_home_xiaoxi.setCompoundDrawables(null, drawable1, null, null);
        Drawable drawable2 = getResources().getDrawable(R.drawable.xiuquan);
        drawable2.setBounds(0, 0, dp, dp);
        rb_home_xiuquan.setCompoundDrawables(null, drawable2, null, null);
        Drawable drawable3 = getResources().getDrawable(R.drawable.wode);
        drawable3.setBounds(0, 0, dp, dp);
        rb_home_wode.setCompoundDrawables(null, drawable3, null, null);
        Drawable drawable4 = getResources().getDrawable(R.drawable.fabu);
        drawable4.setBounds(0, 0, dp, dp);
        rb_home_fabu.setCompoundDrawables(null, drawable4, null, null);
        currentcheck = R.id.rb_home_faxian;
        rg_home.check(currentcheck);
        supportFragmentManager = getSupportFragmentManager();
        faxianFragment = (FaXianFragment) FragmentFractory.getInstance().createFragment(0);
        xiaoxiFragment = (XiaoXiFragment) FragmentFractory.getInstance().createFragment(1);
        xiuquanFragment = (XiuQuanFragment2) FragmentFractory.getInstance().createFragment(2);
        woDeFragment = (WoDeFragment) FragmentFractory.getInstance().createFragment(3);

        getList(URLProvider.GUANZHU);

  /*  rongIMClient = RongIM.getInstance().getRongIMClient();
    conversationList = rongIMClient.getConversationList();


    listfragment = new ConversationListFragment();

    newAdapter = new NewAdapter(context);
    listfragment.setAdapter(newAdapter);*/
        Uri uri = Uri.parse("rong://" + this.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        // listfragment.setUri(uri);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_home_content, faxianFragment, "faxianFragment")
                .add(R.id.fl_home_content, xiaoxiFragment, "listfragment").hide(xiaoxiFragment)
                .add(R.id.fl_home_content, xiuquanFragment, "xiuquanFragment").hide(xiuquanFragment)
                .add(R.id.fl_home_content, woDeFragment, "woDeFragment").hide(woDeFragment)
                .show(faxianFragment)
                .commit();


        currentFragment = faxianFragment;



       /* RequestParams requestParams = new RequestParams("http://h.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7475ff8ce770e0cf3d7cad63e.jpg");
        requestParams.setAutoRename(true);
        requestParams.setSaveFilePath(Environment.getExternalStorageDirectory() + "/imagcacahe/760e1d2.jpg");
        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Toast.makeText(context, result.getAbsolutePath(), Toast.LENGTH_SHORT).show();
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
     /*   x.image().loadFile("http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg", MyApplication.options, new Callback.CacheCallback<File>() {
            @Override
            public boolean onCache(File result) {
                return false;
            }

            @Override
            public void onSuccess(File result) {
                Toast.makeText(context, result.getAbsolutePath(), Toast.LENGTH_LONG).show();
                File newfile = new File(Environment.getExternalStorageDirectory() + "/imagcacahe/760e1d2.jpg");
                try {
                    FileInputStream fis = new FileInputStream(result);
                    FileOutputStream fos = new FileOutputStream(newfile);
                    byte[] buf = new byte[1024];
                    int i = 0;
                    while ((i = fis.read(buf)) != -1) {
                        fos.write(buf, 0, i);
                    }
                    fis.close();
                    fos.close();
                    Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();

                } catch (IOException ie) {
                    ie.printStackTrace();

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(context,isOnCallback+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        Downloader downloader = new Downloader();
        downloader.downloadImage("http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg",Environment.getExternalStorageDirectory() + "/imagcaca646h.jpg");
  */
    }

    @Override
    public void loadData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        transaction = supportFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.rb_home_faxian:
                currentcheck = R.id.rb_home_faxian;
                rg_home.check(currentcheck);
                if (currentFragment != faxianFragment) {
                    switchContent(currentFragment, faxianFragment, "首页", View.GONE);
                }
                break;
            case R.id.rb_home_xiaoxi:
                int visibility = xiaoxidian.getVisibility();
                if (visibility == View.VISIBLE) {
                    xiaoxidian.setVisibility(View.GONE);
                }
                currentcheck = R.id.rb_home_xiaoxi;
                rg_home.check(currentcheck);
                switchContent(currentFragment, xiaoxiFragment, "快递", View.GONE);
                break;
            case R.id.rb_home_xiuquan:
                currentcheck = R.id.rb_home_xiuquan;
                rg_home.check(currentcheck);
                switchContent(currentFragment, xiuquanFragment, "购物车", View.VISIBLE);
                // xiuquanFragment.xiuquan();
                //   xiuquanFragment.initheader(xiuquanBeanDatas);
                break;
            case R.id.rb_home_wode:
                currentcheck = R.id.rb_home_wode;
                rg_home.check(currentcheck);
                switchContent(currentFragment, woDeFragment, "个人中心", View.GONE);
                break;

            case R.id.rb_home_fabu:
                // 通过静态方法构建一个ObjectAnimator对象
                // 设置作用对象、属性名称、数值集合
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                contentview = inflater.inflate(R.layout.dialog_fabu, null);


                yugao = (TextView) contentview.findViewById(R.id.yugao);
                fabu = (TextView) contentview.findViewById(R.id.fabu);
                ivYugao = (ImageView) contentview.findViewById(R.id.iv_yugao);
                ivbFabu = (ImageView) contentview.findViewById(R.id.ivb_fabu);
                ivQuxiao = (ImageView) contentview.findViewById(R.id.iv_quxiao);
                ivYugao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SPUtil.getString("type").equals("1")) {
                            startActivity(ZhuBoFaBuActivity.class);
                        } else {
                            Toast.makeText(context, "请先认证成为主播", Toast.LENGTH_SHORT).show();
                        }
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                    }
                });
                ivbFabu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(FaBuActivity.class);
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                ivQuxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rotateyAnimRun(ivQuxiao);
                    }
                });
                ObjectAnimator tankuang = ObjectAnimator.ofFloat(contentview, "translationY", 600.0F, 0.0F);
                ObjectAnimator tvYugaotor = ObjectAnimator.ofFloat(fabu, "translationY", -80.0F, 0.0F);
                ObjectAnimator tvFabuotor = ObjectAnimator.ofFloat(yugao, "translationY", -80.0F, 0.0F);
                ObjectAnimator ivYugaotor = ObjectAnimator.ofFloat(ivYugao, "translationY", -80.0F, 0.0F);
                ObjectAnimator ivbFabutor = ObjectAnimator.ofFloat(ivbFabu, "translationY", -80.0F, 0.0F);
                // 设置执行时间(1000ms)
                tankuang.setDuration(500);
                ivYugaotor.setDuration(500);
                tvYugaotor.setInterpolator(new BounceInterpolator());
                tvFabuotor.setInterpolator(new BounceInterpolator());
                ivYugaotor.setInterpolator(new BounceInterpolator());
                ivbFabutor.setDuration(500);
                ivbFabutor.setInterpolator(new BounceInterpolator());


                AnimatorSet bouncer = new AnimatorSet();
                bouncer.play(tankuang);
                bouncer.play(ivYugaotor).with(ivbFabutor).with(tvYugaotor).with(tvFabuotor).after(tankuang);


                contentview.setFocusable(true); // 这个很重要
                contentview.setFocusableInTouchMode(true);
                popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(AppUtil.dp2Px(context, 220));
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

                popupWindow.showAtLocation(contentview, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                //  popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
                bouncer.start();
                popupWindow.update();

            /*    rlTankuang.setVisibility(View.VISIBLE);
                ObjectAnimator tankuang = ObjectAnimator.ofFloat(rlTankuang, "translationY", 600.0F, 0.0F);
                ObjectAnimator ivYugaotor = ObjectAnimator.ofFloat(ivYugao, "translationY", -80.0F, 0.0F);
                ObjectAnimator ivbFabutor = ObjectAnimator.ofFloat(ivbFabu, "translationY", -80.0F, 0.0F);
                // 设置执行时间(1000ms)
                tankuang.setDuration(300);
                ivYugaotor.setDuration(500);
                ivYugaotor.setInterpolator(new BounceInterpolator());
                ivbFabutor.setDuration(500);
                ivbFabutor.setInterpolator(new BounceInterpolator());


                AnimatorSet bouncer = new AnimatorSet();
                bouncer.play(tankuang);
                bouncer.play(ivYugaotor).with(ivbFabutor).after(tankuang);
                bouncer.start();
*/

                break;
         /*   case R.id.iv_yugao:
                if (SPUtil.getString("type").equals("1")) {
                    startActivity(ZhuBoFaBuActivity.class);
                } else {
                    Toast.makeText(context, "请先认证成为主播", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ivb_fabu:
                startActivity(FaBuActivity.class);
                break;
            case R.id.iv_quxiao:
                rotateyAnimRun(ivQuxiao);

                break;*/
        }
    }

    public void rotateyAnimRun(View view) {
        ObjectAnimator rotation = ObjectAnimator//
                .ofFloat(view, "rotation", 0.0F, 360.0F);
        rotation.setDuration(300);
        ObjectAnimator animator = ObjectAnimator.ofFloat(contentview, "translationY", 0.0F, 600.0F);
        // 设置执行时间(1000ms)
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                popupWindow.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet bouncer = new AnimatorSet();
        bouncer.play(rotation);
        bouncer.play(animator).after(rotation);
        bouncer.start();

        //  rlTankuang.setVisibility(View.GONE);
    }


    private void getToken() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("tel", SPUtil.getString("tel"));
        XUtils.xUtilsPost(URLProvider.SAVE_TOKEN, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("MainActivity", result);
                Gson gson = new Gson();
                TokenBean tokenBean = gson.fromJson(result, TokenBean.class);
                SPUtil.saveString("token", tokenBean.getToken());
                rongConnect(tokenBean.getToken());
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

    public void switchContent(Fragment from, Fragment to, String title, int gone) {
        currentFragment = to;
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.fl_home_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    private void getList(String url) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        XUtils.xUtilsPost(url, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mafuhua", result);
                Gson gson = new Gson();
                FENSIBean fensiBean = gson.fromJson(result, FENSIBean.class);

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

    class NewAdapter extends ConversationListAdapter {

        private Context contextt;


        public NewAdapter(Context contextt) {
            super(context);
            this.contextt = contextt;
        }

        @Override
        protected View newView(Context context, int position, ViewGroup group) {
            View inflate = View.inflate(contextt, R.layout.item_converdationlsit, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder(inflate);
            inflate.setTag(viewHolder);
            return inflate;
        }

        @Override
        protected void bindView(View v, int position, UIConversation data) {
            //
            //  data.setUnreadType(UIConversation.UnreadRemindType.REMIND_WITH_COUNTING);
            TextMessage latestMessage;
            UserInfo userInfo;
            ViewHolder viewHolder = (ViewHolder) v.getTag();
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

            //  super.bindView(v,position,data);
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
