/**
 * @Title: FragmentWebView.java
 * @Package com.langji.wufeng.util.widget
 * @Description: TODO
 * @author BaoHang baohang2011@gmail.com
 * @date 2014年4月17日 下午1:29:11
 * @version V1.0
 */
package com.yuen.baselib.widgets;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuen.baselib.R;

import java.util.Map;

/**
 * @author BaoHang baohang2011@gmail.com
 * @ClassName: FragmentWebView
 * @Description: 网页组件
 * @date 2014年4月17日 下午1:29:11
 */
public class FragmentWebView {
    private MyWebViewClient webClient;
    private MyWebChromeClient chromeClient;
    private WebView web;
    private ProgressBar web_process;
    private TextView tv_refreshing;

    public FragmentWebView(Activity activity, WebViewCallBack callback) {
        web_process = (ProgressBar) activity.findViewById(R.id.web_process);
        web = (WebView) activity.findViewById(R.id.web);
        tv_refreshing = (TextView) activity.findViewById(R.id.tv_refreshing);
        webClient = new MyWebViewClient(callback);
        chromeClient = new MyWebChromeClient(null);
    }

    public FragmentWebView(Activity activity, WebViewCallBack callback, WebViewProgressChanged progressChanged) {
        this(activity, callback);
        chromeClient = new MyWebChromeClient(progressChanged);
    }


    public void loadUrl(String url) {
        initWebView(false);
        web.loadUrl(url);
    }

    public void loadUrl(String url, Map<String, String> map) {
        initWebView(false);
        web.loadUrl(url, map);
    }

    public void loadData(String data) {
        initWebView(true);
        web.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

    public WebView getWebView() {
        return web;
    }

    private void initWebView(boolean isdata) {
        // 如果访问的页面中有Javascript，则webview必须设置支持Javascript
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // 如果希望点击页面中链接继续在当前browser中响应，而不是新开Android的系统browser中响应该链接，必须覆盖
        // webview的WebViewClient对象。
        // WebViewClient主要帮助WebView处理各种通知、请求事件的
        web.setWebViewClient(webClient);
        web.setWebChromeClient(chromeClient);
        // 设置缓存模式
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置是否使用HTML5缓存
        web.getSettings().setAppCacheEnabled(false);
        // 数据缓存
        web.getSettings().setDatabaseEnabled(false);
        // 不保存表单数据
        web.getSettings().setSaveFormData(false);
        // 不保存密码
        web.getSettings().setSavePassword(false);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setSupportZoom(true);
        // 获取焦点
        web.requestFocus();
        // 清除缓存
        web.clearCache(true);
        web.clearHistory();
        web.clearFormData();
        // Clear the view so that onDraw() will draw nothing but white
        // background, and onMeasure() will return 0 if MeasureSpec is not
        // MeasureSpec.EXACTLY
        web.clearView();
        web.clearAnimation();
        web.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        if (isdata) {
            if (web_process != null) {
                web_process.setVisibility(View.GONE);
            }
            if (tv_refreshing != null) {
                tv_refreshing.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @author BaoHang baohang2011@gmail.com
     * @ClassName: MyWebViewClient
     * @Description: 网页跳转情况监听器
     * @date 2014年4月17日 下午1:34:20
     */
    class MyWebViewClient extends WebViewClient {
        private WebViewCallBack callBack;

        public MyWebViewClient(WebViewCallBack callback) {
            callBack = callback;
        }

        // 在页面加载开始时调用
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (null != web_process && url != null) {
                web_process.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
            }
        }

        // 在页面加载结束时调用
        @Override
        public void onPageFinished(WebView view, String url) {
            if (null != web_process && url != null) {
                web_process.setVisibility(View.GONE);
            }
        }

        @SuppressWarnings("deprecation")
        private void showRefreshView(final WebView view, final String url) {
            if (url != null) {
                view.stopLoading();
                view.clearView();
                view.setVisibility(View.GONE);
                if (null != tv_refreshing) {
                    tv_refreshing.setVisibility(View.VISIBLE);
                    tv_refreshing.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            tv_refreshing.setVisibility(View.GONE);
                            view.loadUrl(url);
                        }
                    });
                }
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            showRefreshView(view, failingUrl);
        }

        // 只有在调用webview.loadURL的时候才会调用
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (callBack != null) {
                return callBack.urlCallBack(view, url);
            } else {
                view.loadUrl(url);
                return true;
            }
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        private WebViewProgressChanged progressChanged;

        public MyWebChromeClient(WebViewProgressChanged progressChanged) {
            this.progressChanged = progressChanged;

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (web_process != null) {
                web_process.setProgress(newProgress);
            }
            if (progressChanged != null) {
                progressChanged.onProgressChanged(view, newProgress);
            }
        }

        @Override
        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            super.onConsoleMessage(message, lineNumber, sourceID);
            if (progressChanged != null) {
                progressChanged.onConsoleMessage(message, lineNumber, sourceID);
            }

        }
    }

    /**
     * @author BaoHang baohang2011@gmail.com
     * @ClassName: WebViewCallBack
     * @Description: 网络请求回调
     * @date 2014年4月17日 下午1:26:48
     */
    public interface WebViewCallBack {
        boolean urlCallBack(WebView web, String url);

    }

    public interface WebViewProgressChanged {
        void onProgressChanged(WebView view, int newProgress);

        void onConsoleMessage(String message, int lineNumber, String sourceID);
    }

}
