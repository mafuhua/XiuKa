package com.yuen.xiuka.activity;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yuen.baselib.utils.SPUtil;
import com.yuen.baselib.utils.SysExitUtil;
import com.yuen.xiuka.R;
import com.yuen.xiuka.fragment.FaXianFragment;
import com.yuen.xiuka.fragment.FragmentFractory;
import com.yuen.xiuka.fragment.WoDeFragment;
import com.yuen.xiuka.fragment.XiaoXiFragment;
import com.yuen.xiuka.utils.MyUtils;
import com.yuen.xiuka.xiuquan.XiuQuanFragment2;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;

public class MainActivity extends BaseActivity implements View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d("mafuhua", "savedInstanceState----------:" + savedInstanceState);
            savedInstanceState = null;
            if (savedInstanceState == null) {
                Log.d("mafuhua", "savedInstanceState**********----------:" + savedInstanceState);
            }
        }
        super.onCreate(savedInstanceState);
        SysExitUtil.activityList.add(this);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
        /**
         * 设置接收 push 消息的监听器。
         */
    //    RongIM.setOnReceivePushMessageListener(new MyReceivePushMessageListener());
        /**
         *  设置接收消息的监听器。
         */
    //    RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
        /**
         * IMKit SDK调用第二步
         *
         * 建立与服务器的连接
         *
         */
        RongIM.connect(SPUtil.getString("token"), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
                Log.e("MainActivity", "——Connect Token— -" + "失效的状态处理，需要重新获取 Token");
            }

            @Override
            public void onSuccess(String userId) {
                Log.e("MainActivity", "——onSuccess— -" + userId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("MainActivity", "——onError— -" + errorCode);
            }
        });

        Toast.makeText(context, "这是win", Toast.LENGTH_SHORT).show();

        Toast.makeText(context, "这是osx", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "这是osx2", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "这是osx3", Toast.LENGTH_SHORT).show();

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
        rg_home.setOnClickListener(this);
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
        rg_home.check(R.id.rb_home_faxian);
        supportFragmentManager = getSupportFragmentManager();
        faxianFragment = (FaXianFragment) FragmentFractory.getInstance().createFragment(0);
        xiaoxiFragment = (XiaoXiFragment) FragmentFractory.getInstance().createFragment(1);
        xiuquanFragment = (XiuQuanFragment2) FragmentFractory.getInstance().createFragment(2);
        woDeFragment = (WoDeFragment) FragmentFractory.getInstance().createFragment(3);
       listfragment = new ConversationListFragment();
       /* listfragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + this.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        listfragment.setUri(uri);*/

        getSupportFragmentManager().beginTransaction().add(R.id.fl_home_content, faxianFragment, "faxianFragment")
              /*  .add(R.id.fl_home_content, listfragment, "listfragment").hide(listfragment)*/
                .add(R.id.fl_home_content, xiuquanFragment, "xiuquanFragment").hide(xiuquanFragment)
                .add(R.id.fl_home_content, woDeFragment, "woDeFragment").hide(woDeFragment)
                .show(faxianFragment)
                .commit();


        currentFragment = faxianFragment;


  /*      RequestParams requestParams = new RequestParams("http://h.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7475ff8ce770e0cf3d7cad63e.jpg");
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
        });
*/
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
  */  }

    @Override
    public void loadData() {


    }

    @Override
    public void onClick(View v) {
        transaction = supportFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.rb_home_faxian:
                if (currentFragment != faxianFragment) {
                    switchContent(currentFragment, faxianFragment, "首页", View.GONE);
                }
                break;
            case R.id.rb_home_xiaoxi:
             //   switchContent(currentFragment, listfragment, "快递", View.GONE);
                break;
            case R.id.rb_home_xiuquan:

                switchContent(currentFragment, xiuquanFragment, "购物车", View.VISIBLE);
               // xiuquanFragment.xiuquan();
             //   xiuquanFragment.initheader(xiuquanBeanDatas);
                break;
            case R.id.rb_home_wode:
                switchContent(currentFragment, woDeFragment, "个人中心", View.GONE);
                break;

            case R.id.rb_home_fabu:
                startActivity(ZhuBoFaBuActivity.class);
                break;
        }
    }

    public void switchContent(Fragment from, Fragment to, String title, int gone) {
        currentFragment = to;
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.fl_home_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }
}
