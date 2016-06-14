package com.yuen.baselib.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yuen.baselib.R;
import com.yuen.baselib.utils.ToastUtil;

import java.io.Serializable;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 加载界面的资源id
     */
    protected int mLayoutResID = -1;

    /**
     * @param layoutResID 界面资源文件id
     */
    public BaseActivity(int layoutResID) {
        this.mLayoutResID = layoutResID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutResID);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * initTitle:初始化标题. <br/>
     *
     * @param id 标题资源文件id
     */
    public void initTitle(int id) {

    }

    /**
     * initTitle:初始化标题. <br/>
     *
     * @param name 标题文本
     */
    public void initTitle(String name) {

    }


    /**
     * 弹出界面提示内容
     *
     * @param msg 提示内容文字内容
     */
    public void showToast(String msg) {
        ToastUtil.toastShortShow(getApplication(), msg);
    }

    /**
     * 弹出界面提示内容
     *
     * @param textResId 文本资源id
     */
    public void showToast(int textResId) {
        ToastUtil.toastShortShow(getApplication(), textResId);
    }

    /**
     * 得到界面跳转过来的数值，如果上一个界面有数据传递过来，那么这边需要进行重写该方法
     */
    public void getIntentData() {

    }

    /**
     * 得到相关界面的组件，需要重写
     */
    public abstract void findIds();

    /**
     * 初始化界面，对相关界面控件进行赋值
     */
    public abstract void initView();

    /**
     * 初始化数据，从网络或本地拉取数据
     */
    public abstract void loadData();


    /**
     * 打开界面，带传递参数
     *
     * @param cls 需要打开的界面
     * @param obj 传递过去的界面参数
     */
    public void startActivity(Class<?> cls, Object obj) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivity(intent);
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

    /**
     * 打开界面
     *
     * @param cls 需要打开的界面
     */
    public void startActivity(Context context,Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

    public void startActivityForNew(Class<?> cls, Object obj) {
        Intent intent = new Intent(this, cls);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivity(intent);
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

    /**
     * 打开界面，带参数，需要有返回参数
     *
     * @param cls         需要打开的界面
     * @param obj         传递过去的界面参数
     * @param requestCode 界面跳转请求码
     */
    public void startActivityForResult(Class<?> cls, Object obj, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

}
