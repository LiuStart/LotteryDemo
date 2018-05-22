package com.qujing.leeyong.xlchwsc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qujing.leeyong.xlchwsc.adapter.HistoryAdapter;
import com.qujing.leeyong.xlchwsc.bean.KaiJiangInfo;
import com.qujing.leeyong.xlchwsc.util.CheckUtil;
import com.qujing.leeyong.xlchwsc.util.DialogUtil;
import com.qujing.leeyong.xlchwsc.util.ParseJsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class HistoryActivity extends AppCompatActivity {
    private ImageView mBack;
    /**
     * 大乐透近期开奖
     */
    private TextView mName;
    private ListView mLvListview;
    private SharedPreferences caipiaoName;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                mLvListview.setAdapter(new HistoryAdapter(kaiJiangInfoArrayList,HistoryActivity.this));
                handler.sendEmptyMessage(2);
            }else if(msg.what==2){
                if(loadingDialog!=null&&loadingDialog.isShowing()){
                    loadingDialog.dismiss();
                }
            }else if(msg.what==3){
                Intent intent = getIntent();
                String name = intent.getStringExtra("NAME");
                if(name!=null){
                    mName.setText(caipiaoName.getString(name,"  ")+"近期开奖");
                    getLotteryData(name);
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaijiang_activity);
        initView();
        caipiaoName = getSharedPreferences("CAIPIAONAME", Context.MODE_PRIVATE);
        showDialog();
        handler.sendEmptyMessageDelayed(3,3000);
    }
    Dialog loadingDialog;
    private void showDialog(){
        loadingDialog = DialogUtil.createLoadingDialog(this, "加载中..");
        loadingDialog.show();
        //  loadingDialog = DialogUtil.showWaitDialog(getContext(), "加载中...");
    }
    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mName = (TextView) findViewById(R.id.name);
        mLvListview = (ListView) findViewById(R.id.lv_listview);
    }
    private RequestQueue mRequestQueue = null;
    private List<KaiJiangInfo> kaiJiangInfoArrayList;
    private void getLotteryData(final String  name) {
        if (!CheckUtil.isNetworkAvailable(this)) {
              Toast.makeText(HistoryActivity.this, "没有检测到数据连接，请检查设备网络状态！", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(2);
            return;
        }
        kaiJiangInfoArrayList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                mRequestQueue = Volley.newRequestQueue(HistoryActivity.this);

                    String url = "http://f.apiplus.net/" + name + "-20.json";
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d("lee", s);
                            kaiJiangInfoArrayList = ParseJsonUtil.ParseKaijiang(s);
                            handler.sendEmptyMessage(1);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("lee", volleyError.toString());
                            //   handler.sendEmptyMessage(2);
                            Toast.makeText(HistoryActivity.this, "数据获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(3);
                        }
                    });
                    mRequestQueue.add(request);


            }
        }.start();

    }
}
