package com.cn.lottery.leeyybssc.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;

import com.cn.lottery.leeyybssc.R;
import com.cn.lottery.leeyybssc.util.DialogUtil;

/**
 * Created by Administrator on 2018/5/25.
 */

public class MyFragment extends Fragment {
    private String[] weburl1 = {"https://m.cai310.cn/chart/pailiesan.html?n=PaiLieSan",
            "https://m.cai310.cn/chart/pailiewu.html?n=PaiLieWu",
            "https://m.cai310.cn/chart/shishicai.html?n=jlssc"
            , "https://m.cai310.cn/chart/kuaisan.html?n=k3",
            "https://m.cai310.cn/chart/kuaileshifen.html?n=cqklsf",
            "https://m.cai310.cn/chart/shiyixuanwu.html?n=qh115"};

    private String[] weburl2 = {"https://m.cai310.cn/intro/PaiLieSan.html",
            "https://m.cai310.cn/intro/PaiLieWu.html",
            "https://m.cai310.cn/intro/jlssc.html"
            , "https://m.cai310.cn/intro/k3.html",
            "https://m.cai310.cn/intro/cqklsf.html",
            "https://m.cai310.cn/intro/qh115.html"};



    private int index;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("index");

    }
    private WebView webView;
    private RadioGroup radioGroup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.myfragment, null);
        webView= (WebView) inflate.findViewById(R.id.webview);
        final Dialog loadingDialog = DialogUtil.createLoadingDialog(getContext(), "正在获取...");
        loadingDialog.show();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setAllowContentAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        radioGroup = (RadioGroup) inflate.findViewById(R.id.contacts_rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.zoushi:
                        webView.loadUrl(weburl1[index]);
                        break;
                    case R.id.wanfa:
                        webView.loadUrl(weburl2[index]);
                        break;
                }
            }
        });
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {


             //   super.onPageFinished(view, url);

//                String js = ADFilterTool.getClearAdDivJs(MainActivity.this);
//                Log.v("adJs", js);
//                appView.loadUrl(js);

                Log.e("adJs", "dasdasdasdadsadad");
                //ui.style.visibility="hidden";
                String hsstr = "javascript:var adDiv=document.getElementsByClassName(\"top\")[0];adDiv.parentNode.removeChild(adDiv)";
//<div class="chart-footer">
                String hsstr1 = "javascript:var adDiv=document.getElementsByClassName(\"chart-footer\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr2 = "javascript:var adDiv=document.getElementsByClassName(\"ui-header\")[0];adDiv.parentNode.removeChild(adDiv)";
//
                String hsstr3 = "javascript:var adDiv=document.getElementsByClassName(\"tuig\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr4 = "javascript:var adDiv=document.getElementsByTagName(\"footer\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr5 = "javascript:var adDiv=document.getElementsByTagName(\"footer\")[0];adDiv.parentNode.removeChild(adDiv)";
//                String hsstr7 = "javascript:var adDiv=document.getElementsByTagName(\"iframe\")[0];adDiv.parentNode.removeChild(adDiv)";
//                String hsstr8 = "javascript:var adDiv=document.getElementsByTagName(\"iframe\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr6 = "javascript:var adDiv=document.getElementsByClassName(\"vMod\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr7 = "javascript:var adDiv=document.getElementsByClassName(\"vMod\")[0];adDiv.parentNode.removeChild(adDiv)";

                String hsstr9 = "javascript:var adDiv=document.getElementsByClassName(\"vMod_functionBar\")[0];adDiv.parentNode.removeChild(adDiv)";
                String hsstr10 = "javascript:var adDiv=document.getElementsByClassName(\"footer\")[0];adDiv.parentNode.removeChild(adDiv)";
//
                webView.loadUrl(hsstr1);
                webView.loadUrl(hsstr);
//                appView.loadUrl(hsstr3);
//                appView.loadUrl(hsstr4);
//                appView.loadUrl(hsstr5);
//                appView.loadUrl(hsstr6);
//                appView.loadUrl(hsstr7);
////                appView.loadUrl(hsstr8);
//                appView.loadUrl(hsstr9);
//                appView.loadUrl(hsstr10);
                loadingDialog.dismiss();
            }
        });

        webView.loadUrl(weburl1[index]);
        return inflate;
    }

}
