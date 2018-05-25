package com.qujing.leeyong.xlchwsc;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dalong.carrousellayout.CarrouselLayout;
import com.qujing.leeyong.xlchwsc.adapter.GridAdapter;
import com.qujing.leeyong.xlchwsc.bean.BlogModel;
import com.qujing.leeyong.xlchwsc.util.MyLogger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/21.
 */

public class TestActivity extends AppCompatActivity{
    private LinearLayout root;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        CarrouselLayout carrousel= (CarrouselLayout) findViewById(R.id.carrousel);
        carrousel.setR(400)//设置R的大小
                .setAutoRotation(true)//是否自动切换
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒
        root=findViewById(R.id.root);
        getHtml();
        setView();
    }

    private void setView() {
        float density = getResources().getDisplayMetrics().density;
        for (int k=0;k<4;k++){
            TextView textView = (TextView) LayoutInflater.from(TestActivity.this).inflate(R.layout.item_textview, null);
            textView.setText("22");
            textView.setHeight((int)(25 * density + 0.5f));
            textView.setWidth((int)(25 * density + 0.5f));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 10, 0);
            textView.setLayoutParams(layoutParams);
            root.addView(textView);
        }
        GridView gridView=findViewById(R.id.gridview);
        ArrayList<String> list=new ArrayList<>();
        int j=0;
        for (int k=0;k<20;k++){
            list.add(j+"");
            j++;
        }
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyLogger.Log("parent:"+parent.getCount());

                RelativeLayout relativeLayout= (RelativeLayout) view;
                TextView textView = (TextView) relativeLayout.getChildAt(0);
                textView.setBackground(getResources().getDrawable(R.drawable.shapetextview));
                textView.setTextColor(getResources().getColor(R.color.white));
            }
        });
        gridView.setAdapter(new GridAdapter(this,list));
     //   gridView.getItem

    }

    private RequestQueue mRequestQueue = null;
    private void getHtml() {
        new Thread() {
            @Override
            public void run() {
                mRequestQueue = Volley.newRequestQueue(TestActivity.this);
                    StringRequest request = new StringRequest(Request.Method.GET, "https://www.jianshu.com/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            MyLogger.Log("获取成功！");
                         //   Log.d("lee", s);
                            parseHtml(s);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("lee", volleyError.toString());
                        }
                    });
                    mRequestQueue.add(request);
                }
        }.start();
    }

    private void parseHtml(String html) {
        //将html转为Document对象
        Document document = Jsoup.parse(html);
        //获得li的元素集合
        Elements elements = document.select("div#list-container ul li");
        MyLogger.Log("elements.isEmpty():"+elements.isEmpty()+elements.size());

        List<BlogModel> lists = new ArrayList<>();
        BlogModel blogModel;
        for (Element element : elements) {
            //获得作者
            String author = element.select("div.info a").first().text();
            //获得标题
            String title = element.select("a.title").first().text();
            //获得图片url，因为文章有可能没有图片，所以这里需要特殊处理一下
            String image = element.select("a.wrap-img").first() != null ?
                    element.select("a.wrap-img").first().children().first().attr("src") : "";
            //获得文章详情url
            String targetUrl = element.select("a.title").first().attr("href");
            blogModel = new BlogModel();
            blogModel.setAuthor(author);
            blogModel.setName(title);
            blogModel.setImage(image);
            blogModel.setTargetUrl(targetUrl);
            lists.add(blogModel);
        }
        MyLogger.Log("获取的数据："+lists.size());
        for (BlogModel blogModel1:lists){
            MyLogger.Log(blogModel1.toString());
        }
    }
}
