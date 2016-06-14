package com.yuen.baselib.utils2;

import android.app.Activity;
import android.os.Bundle;

import com.yuen.baselib.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ToastUtils.startProgressDialog(this, "加载中,请稍后");
    }

    
}
