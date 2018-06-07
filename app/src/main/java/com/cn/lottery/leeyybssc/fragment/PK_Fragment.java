package com.cn.lottery.leeyybssc.fragment;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cn.lottery.leeyybssc.R;
import com.cn.lottery.leeyybssc.util.MyLogger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by Administrator on 2018/6/6.
 */

public class PK_Fragment extends Fragment{
    private  WebView webView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private RefreshLayout mRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.pk_fragment,null);
        webView=inflate.findViewById(R.id.pkwebview);
       WebSettings webSettings = webView.getSettings();
       // webSettings.setJavaScriptEnabled(false);
// 支持使用localStorage(H5页面的支持)
        webSettings.setDomStorageEnabled(true);
// 支持数据库
        webSettings.setDatabaseEnabled(true);
// 支持缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
// 设置可以支持缩放
        webSettings.setUseWideViewPort(true);
// 扩大比例的缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
// 隐藏缩放按钮
        webSettings.setDisplayZoomControls(false);
// 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
// 隐藏滚动条
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                MyLogger.Log("onPageFinished");
             //   String hsstr = "javascript:var adDiv=document.getElementsByClassName(\"batten\")[0];adDiv.parentNode.removeChild(adDiv)";
            //    webView.loadUrl(hsstr);
                mRefreshLayout.finishRefresh();
            }
        });
        mRefreshLayout = inflate.findViewById(R.id.refreshLayout_fragment);
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        mRefreshLayout.setEnableOverScrollBounce(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //  handler.sendEmptyMessageDelayed(2,2000);
                webView.loadUrl("http://www.xiongjianty.com/");
            }
        });
        if(isViewVisible){
            //第一次进入触发自动刷新，演示效果
            Log.d("lee","mRefreshLayout.autoRefresh();");
            mRefreshLayout.autoRefresh();
        }
        ISVIEWCREATE=true;
        mRefreshLayout.autoRefresh();
        return inflate;
    }
    private boolean ISVIEWCREATE=false;
    private boolean isViewVisible;//view是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        Log.d("lee","体育是否可见："+isViewVisible);
    }

    @Override
    public void onDestroyView() {
        Log.d("lee","onDestroyView");
        ISVIEWCREATE=false;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        ISVIEWCREATE=false;
        Log.d("lee","onDestroy");
        super.onDestroy();
    }
}
