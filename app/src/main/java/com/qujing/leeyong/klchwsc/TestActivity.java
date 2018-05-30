package com.qujing.leeyong.klchwsc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dalong.carrousellayout.CarrouselLayout;
import com.qujing.leeyong.klchwsc.adapter.GridAdapter;
import com.qujing.leeyong.klchwsc.bean.BlogModel;
import com.qujing.leeyong.klchwsc.util.MyLogger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/5/21.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout root;
    /**
     * test
     */
    private Button mTest;
    private int count;
    private Vibrator vibrator;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(count!=0){
                    changeView();
                    vibrator.vibrate(50);
                    handler.sendEmptyMessageDelayed(1,200);
                    count--;
                }else {
                    Toast.makeText(TestActivity.this,"已经随机生成一注！",Toast.LENGTH_SHORT).show();
                    // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                  //  cm.setText(tvMsg.getText());
                }


            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        initView();
        CarrouselLayout carrousel = (CarrouselLayout) findViewById(R.id.carrousel);
        carrousel.setR(400)//设置R的大小
                .setAutoRotation(true)//是否自动切换
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒
        root = findViewById(R.id.root);
        getHtml();
        setView();
    }

    private GridAdapter adapter;
    private GridAdapter adapter_h;
    ArrayList<String> list;
    GridView gridView;
    GridView gridView_h;
    ArrayList<String> contentList;
    ArrayList<String> contentList_h;
    ArrayList<String> list_h;
    private void setView() {
        float density = getResources().getDisplayMetrics().density;
        for (int k = 0; k < 4; k++) {
            TextView textView = (TextView) LayoutInflater.from(TestActivity.this).inflate(R.layout.item_textview, null);
            textView.setText("22");
            textView.setHeight((int) (25 * density + 0.5f));
            textView.setWidth((int) (25 * density + 0.5f));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 10, 0);
            textView.setLayoutParams(layoutParams);
            textView.setOnClickListener(null);
            root.addView(textView);
        }

        gridView = findViewById(R.id.gridview);
        list = new ArrayList<>();
        int j = 1;
        for (int k = 0; k < 33; k++) {
            if(k<9){
                list.add("0"+j + "");
            }else {
                list.add(j + "");
            }

            j++;
        }
        int a = 1;
        list_h = new ArrayList<>();
        for (int k = 0; k < 16; k++) {
            if(k<9){
                list_h.add("0"+a + "");
            }else {
                list_h.add(a + "");
            }

            a++;
        }
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyLogger.Log("parent:" + parent.getCount());

                RelativeLayout relativeLayout = (RelativeLayout) view;
                TextView textView = (TextView) relativeLayout.getChildAt(0);
                boolean selected = relativeLayout.isSelected();
                if (selected) {
                    textView.setSelected(true);
                } else {
                    textView.setSelected(false);
                }
                int tag = (int) textView.getTag();
                MyLogger.Log("tag:" + tag);
                if (tag == 0) {
                    textView.setBackground(getResources().getDrawable(R.drawable.shapetextview));
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setTag(1);
                } else {
                    textView.setBackground(getResources().getDrawable(R.drawable.shape_xuanhao));
                    textView.setTextColor(getResources().getColor(R.color.black));
                    textView.setTag(0);
                }

            }

        });
        contentList = new ArrayList<>();
        adapter = new GridAdapter(this, list, contentList,0);
        gridView.setAdapter(adapter);
        contentList_h = new ArrayList<>();
        adapter_h = new GridAdapter(this, list_h, contentList_h,1);
        gridView_h.setAdapter(adapter_h);
    }

    private void changeView() {
        contentList.clear();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String s = new Random().nextInt(34) + "";
            if (!contentList.contains(s)) {
                MyLogger.Log(s);
                contentList.add(s);
            }
            if (contentList.size() == 6) {
                break;
            }
        }
        MyLogger.Log("contentList:" + contentList.size());
       // adapter = new GridAdapter(this, list, contentList);
        contentList_h.clear();
        for (int i = 0; i < 1; i++) {
            String s = new Random().nextInt(16) + "";
                MyLogger.Log(s);
                contentList_h.add(s);
        }
        adapter.notifyDataSetInvalidated();
        adapter_h.notifyDataSetInvalidated();
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
                        //   parseHtml(s);
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
        MyLogger.Log("elements.isEmpty():" + elements.isEmpty() + elements.size());

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
        MyLogger.Log("获取的数据：" + lists.size());
        for (BlogModel blogModel1 : lists) {
            MyLogger.Log(blogModel1.toString());
        }
    }

    private void initView() {
        mTest = (Button) findViewById(R.id.test);
        mTest.setOnClickListener(this);
        gridView_h=findViewById(R.id.gridview_h);
        gridView_h.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView_h.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyLogger.Log("parent:" + parent.getCount());

                RelativeLayout relativeLayout = (RelativeLayout) view;
                TextView textView = (TextView) relativeLayout.getChildAt(0);
                boolean selected = relativeLayout.isSelected();
                if (selected) {
                    textView.setSelected(true);
                } else {
                    textView.setSelected(false);
                }
                int tag = (int) textView.getTag();
                MyLogger.Log("tag:" + tag);
                if (tag == 0) {
                    textView.setBackground(getResources().getDrawable(R.drawable.shapetextview_h));
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setTag(1);
                } else {
                    textView.setBackground(getResources().getDrawable(R.drawable.shape_xuanhao_h));
                    textView.setTextColor(getResources().getColor(R.color.black));
                    textView.setTag(0);
                }

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.test:
                count=5;
                handler.sendEmptyMessage(1);
                break;
        }
    }
}
