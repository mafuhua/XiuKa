package com.yuen.xiuka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.utils.URLProvider;

import org.xutils.x;

public class IconActivity extends AppCompatActivity {
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        Intent intent = getIntent();
        String icon = intent.getStringExtra("icon");
        imageView = (ImageView) findViewById(R.id.imageView);
        x.image().bind(imageView, URLProvider.BaseImgUrl+icon, MyApplication.optionsxq2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
