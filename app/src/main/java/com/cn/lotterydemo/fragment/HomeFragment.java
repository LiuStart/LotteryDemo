package com.cn.lotterydemo.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cn.lotterydemo.R;
import com.cn.lotterydemo.WaveSwipeStyleActivity;
import com.cn.lotterydemo.adapter.KaijiangAdapter;
import com.cn.lotterydemo.bean.KaiJiangInfo;
import com.cn.lotterydemo.util.CheckUtil;
import com.cn.lotterydemo.util.ParseJsonUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    private Spinner spinner;
    private ListView  listview;
    private WebView webView;
    private SharedPreferences sp;
    private RelativeLayout head;
   private ListView listView;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            listView.setAdapter(new KaijiangAdapter(getContext(), kaiJiangInfoArrayList));
        }
    };
    private AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        Button test=inflate.findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WaveSwipeStyleActivity.class));
            }
        });
        Button mobike=inflate.findViewById(R.id.mobike);
        mobike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listView=inflate.findViewById(R.id.listview);
        showDialog();
        initCaipiao();
        getLotteryData();
        return inflate;
    }

    private void showDialog(){
        /*ProgressBar bar=new ProgressBar(getContext());
        *//*AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.show_dialog, null);
        builder.setView(inflate);
        alertDialog = builder.create();*//*
        bar.s*/
    }

    private ArrayList<KaiJiangInfo> kaiJiangInfoArrayList;
    private RequestQueue mRequestQueue = null;
    private void getLotteryData() {
        if (!CheckUtil.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "没有检测到数据连接，请检查设备网络状态！", Toast.LENGTH_SHORT).show();
          //  handler.sendEmptyMessage(2);
            return;
        }
        kaiJiangInfoArrayList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                mRequestQueue = Volley.newRequestQueue(getContext());
                for (int k = 0; k < urlList.size(); k++) {
                    String name = urlList.get(k);
                    String url = "http://f.apiplus.net/" + name + "-1.json";
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d("lee", s);
                            List<KaiJiangInfo> kaiJiangInfos = ParseJsonUtil.ParseKaijiang(s);
                            if (kaiJiangInfos != null && kaiJiangInfos.size() > 0) {
                                kaiJiangInfoArrayList.add(kaiJiangInfos.get(0));
                            } else {
                                kaiJiangInfoArrayList.add(new KaiJiangInfo("06,10,21,28,29,31+12", "2018-04-15 21:18:20", "2018042", "ssq"));
                            }
                            Log.d("lee", "kaiJiangInfos:" + kaiJiangInfoArrayList.size());
                            if (kaiJiangInfoArrayList.size() == 9) {
                               /* for(KaiJiangInfo info:kaiJiangInfoArrayList){
                                    Log.d("lee",info.toString());
                                }*/
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("lee", volleyError.toString());
                         //   handler.sendEmptyMessage(2);
                            Toast.makeText(getContext(), "数据获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequestQueue.add(request);
                }

            }
        }.start();

    }
    private ArrayList<String> urlList;
    private void initCaipiao(){
        urlList = new ArrayList<>();
        urlList.add("cqssc");
        urlList.add("qxc");
        urlList.add("zcjqc");
        urlList.add("dlt");
        urlList.add("fc3d");
        urlList.add("pl3");
        urlList.add("pl5");
        urlList.add("qlc");
        urlList.add("ssq");
    }
}
