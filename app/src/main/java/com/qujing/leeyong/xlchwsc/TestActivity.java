package com.qujing.leeyong.xlchwsc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dalong.carrousellayout.CarrouselLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2018/5/21.
 */

public class TestActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        CarrouselLayout carrousel= (CarrouselLayout) findViewById(R.id.carrousel);
        carrousel.setR(400)//设置R的大小
                .setAutoRotation(true)//是否自动切换
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒
        new Thread(){
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("http://live.caipiao.163.com/").get();
                    Elements elements = document.select("div.matcheListBox");
                    Log.d("lee",elements.select("a").attr("title"));
                } catch (IOException e) {
                    Log.d("lee",e.toString());
                }

            }
        }.start();
    }
}
