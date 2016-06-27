package com.yuen.xiuka.utils;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.yuen.xiuka.MyApplication;

import cz.msebera.android.httpclient.Header;

public class Downloader {
    public static AsyncHttpClient mHttpc = new AsyncHttpClient();
    public static String TAG = "Downloader";  
      
    public void downloadText(String uri){  
        mHttpc.get(uri, null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }


        });  
    }  
      
    public void downloadImage(String uri, String savePath){  
        mHttpc.get(uri, new ImageResponseHandler(savePath));  
    }  
      
    public class ImageResponseHandler extends BinaryHttpResponseHandler {
        private String mSavePath;  
          
        public ImageResponseHandler(String savePath){  
            super();  
            mSavePath = savePath;  
        }  

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
            Toast.makeText(MyApplication.context, mSavePath, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {

        }
    }
};  