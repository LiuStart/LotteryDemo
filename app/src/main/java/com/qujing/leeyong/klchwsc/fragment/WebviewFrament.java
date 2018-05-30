package com.qujing.leeyong.klchwsc.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.qujing.leeyong.klchwsc.R;
import com.wang.avi.AVLoadingIndicatorView;

@SuppressLint("ValidFragment")
public class WebviewFrament extends Fragment {
    private String mTitle;
    WebView appView;

    RelativeLayout ladding;
    AVLoadingIndicatorView ladd;
//
//    public static WebviewFrament getInstance(String title) {
//        WebviewFrament sf = new WebviewFrament();
//        sf.mTitle = title;
//        return sf;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle= getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.webview_frament, null);
        appView = (WebView) v.findViewById(R.id.webview);
        ladding = (RelativeLayout) v.findViewById(R.id.rl_loadding);
        ladd = (AVLoadingIndicatorView) v.findViewById(R.id.loadding);
        ladd.show();
        initMethod();


        return v;
    }


    private void initMethod() {
        WebSettings webSettings = appView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setAllowContentAccess(true);
        //设置webview属性可以对js进行操作

        //允許webView寫入Cookie
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(appView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        appView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Uri uri = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
//        saicheng_shuju
        Log.e("mTitle",mTitle);

        appView.setWebViewClient(new WebViewClient() {

            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
                    return true;
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("shoudddd","dasdasdasdas");
                return super.shouldOverrideUrlLoading(view, request);


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {


                super.onPageStarted(view, url, favicon);
                ladding.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {


                super.onPageFinished(view, url);

//                String js = ADFilterTool.getClearAdDivJs(MainActivity.this);
//                Log.v("adJs", js);
//                appView.loadUrl(js);

                Log.e("adJs", "dasdasdasdadsadad");
                String hsstr = "javascript:var adDiv=document.getElementsByClassName(\"saicheng_shuju\")[0];adDiv.parentNode.removeChild(adDiv)";
//
                String hsstr2 = "javascript:var adDiv=document.getElementsByClassName(\"ui-header\")[0];adDiv.parentNode.removeChild(adDiv)";
//
                String hsstr3 = "javascript:var adDiv=document.getElementsByClassName(\"tuig\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr4 = "javascript:var adDiv=document.getElementsByTagName(\"footer\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr5 = "javascript:var adDiv=document.getElementsByTagName(\"footer\")[0];adDiv.parentNode.removeChild(adDiv)";
//                String hsstr7 = "javascript:var adDiv=document.getElementsByTagName(\"iframe\")[0];adDiv.parentNode.removeChild(adDiv)";
//                String hsstr8 = "javascript:var adDiv=document.getElementsByTagName(\"iframe\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr6 = "javascript:var adDiv=document.getElementsByClassName(\"vMod\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr7 = "javascript:var adDiv=document.getElementsByClassName(\"vMod\")[0];adDiv.parentNode.removeChild(adDiv)";

                String hsstr9= "javascript:var adDiv=document.getElementsByClassName(\"vMod_functionBar\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr10 = "javascript:var adDiv=document.getElementsByClassName(\"footer\")[0];adDiv.parentNode.removeChild(adDiv)";
//
                appView.loadUrl(hsstr);
                appView.loadUrl(hsstr2);
//                appView.loadUrl(hsstr3);
//                appView.loadUrl(hsstr4);
//                appView.loadUrl(hsstr5);
//                appView.loadUrl(hsstr6);
//                appView.loadUrl(hsstr7);
////                appView.loadUrl(hsstr8);
//                appView.loadUrl(hsstr9);
//                appView.loadUrl(hsstr10);
                ladding.setVisibility(View.GONE);
            }

        });


        appView.loadUrl(mTitle);
    }
}