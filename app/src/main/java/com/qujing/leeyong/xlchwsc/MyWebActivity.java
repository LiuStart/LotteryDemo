package com.qujing.leeyong.xlchwsc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Administrator on 2018/5/10.
 */

public class MyWebActivity extends AppCompatActivity{
    WebView webView;
    String data="<p class=\"art_p\">新浪科技讯 5月10日午间消息，针对滴滴顺风车乘客遇害一事，滴滴今日发布声明称，负有不可推卸的责任，向其家人道歉。目前滴滴已经成立了专项工作组，配合警方开展案件侦查工作。</p><p class=\"art_p\">据媒体报道，被害乘客李女士是一名空姐，在郑州航空港区叫了一辆滴滴顺风车赶往市内，中途遇害。被害人父亲称，听警官说“这个滴滴司机带着凶器”。目前凶手仍在潜逃，公安机关正在全力抓捕。</p><p class=\"art_p\">针对此事，滴滴今日发布声明称，对李女士搭乘顺风车遇害一事感到万分悲痛和愧疚。“我们真诚地和李女士的家人道歉，作为平台我们辜负了用户的信任，在这件事情上，我们负有不可推卸的责任。”</p><p class=\"art_p\">滴滴方面称，滴滴已经成立了专项工作组，配合警方开展案件侦查工作，目前案件正在侦破过程中。滴滴还称，在做好后续工作的同时，将全面彻查各项业务，避免类似事件的发生。</p><p class=\"art_p\">网约车的安全问题一直是行业关注的焦点之一。对于此类事件，一方面是平台对司机的背景筛查，另一方面则是事中的救援和事后的赔偿善后问题。</p><p class=\"art_p\">今年3月，滴滴宣布在现有的安全保障体系基础上推出安全保障服务品牌关怀宝，不过该服务覆盖的是专车和快车两个业务线，而顺风车不属于严格意义上的网约车业务，并未包含在其中。（张俊）</p><p class=\"art_p\">以下为滴滴声明全文：</p><p class=\"art_p\">对郑州顺风车乘客遇害感到万分悲痛和愧疚</p><p class=\"art_p\">对于郑州顺风车乘客李女士遇害一事，我们感到万分悲痛和愧疚，在这样的悲剧面前，任何言语都无法表达我们沉痛的自责。我们真诚地和李女士的家人道歉，作为平台我们辜负了用户的信任，在这件事情上，我们负有不可推卸的责任。</p><p class=\"art_p\">滴滴已经成立了专项工作组，密切配合警方开展案件侦查工作，目前案件正在侦破过程中。请李女士的家人以及公众放心，滴滴将尽最大努力协助警方尽快破案，将凶手绳之以法，还李女士和家人一个公道。</p><p class=\"art_p\">再次向乘客家人以及公众道歉。我们会全力做好后续工作，同时全面彻查各项业务，避免类似事件的发生。</p><p class=\"art_p\">2018年5月10日</p><p class=\"art_p\">滴滴出行</p><p class=\"art_p\">点击进入专题：<br/> 空姐乘网约车惨遭杀害 滴滴审核流程遭疑</p>";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView=new WebView(this);
        setContentView(webView);
        WebSettings webSettings = webView.getSettings();

// 支持javascript
        webSettings.setJavaScriptEnabled(true);

// 支持使用localStorage(H5页面的支持)
        webSettings.setDomStorageEnabled(true);

// 支持数据库
        webSettings.setDatabaseEnabled(true);

// 支持缓存
        webSettings.setAppCacheEnabled(true);
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCaceDir);

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
        // 进度显示及隐藏
        webView.loadDataWithBaseURL(null,data,"text/html", "UTF-8",null);
    }
    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
