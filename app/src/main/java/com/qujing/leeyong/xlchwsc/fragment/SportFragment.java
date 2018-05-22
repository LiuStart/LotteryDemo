package com.qujing.leeyong.xlchwsc.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qujing.leeyong.xlchwsc.R;
import com.qujing.leeyong.xlchwsc.adapter.SportAdapter;
import com.qujing.leeyong.xlchwsc.bean.SportBean;
import com.qujing.leeyong.xlchwsc.util.ParseJsonUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/20.
 */
//http://api.avatardata.cn/Nba/NomalRace?key=d41bff6029f4420e9ddf9a028ab569cd
public class SportFragment extends Fragment {
    public SportFragment() {
        // Required empty public constructor
    }
    private StringRequest stringRequest;
    private boolean ISVIEWCREATE=false;
    private String url="http://api.avatardata.cn/Nba/NomalRace?key=d41bff6029f4420e9ddf9a028ab569cd";
    private ListView listView;
    private ArrayList<SportBean> sportBeans;
    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(getContext(),"没有获取到比赛信息！",Toast.LENGTH_SHORT).show();
            }else if(msg.what==1){
                Log.d("lee","设置adapter"+sportBeans.size());
                listView.setAdapter(new SportAdapter(getContext(),sportBeans));
                handler.sendEmptyMessage(2);
            }else if(msg.what==2){
                mRefreshLayout.finishRefresh();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        Log.d("lee","onCreateView："+isViewVisible);
        View inflate = inflater.inflate(R.layout.fragment_sport, container, false);
        listView=inflate.findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SportBean bean = sportBeans.get(position);
                String playOff = bean.getPlayOff();
                if(TextUtils.isEmpty(playOff)){
                    playOff="http://kbs.sports.qq.com/#nba";
                }
                Uri uri = Uri.parse(playOff);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                /*Intent intent=new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",playOff);*/
                startActivity(intent);
            }
        });
        mRefreshLayout = (RefreshLayout)inflate.findViewById(R.id.refreshLayout_fragment);
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        mRefreshLayout.setEnableOverScrollBounce(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
              //  handler.sendEmptyMessageDelayed(2,2000);
                getSportData();
            }
        });
        if(isViewVisible){
            //第一次进入触发自动刷新，演示效果
            Log.d("lee","mRefreshLayout.autoRefresh();");
            mRefreshLayout.autoRefresh();
        }
        ISVIEWCREATE=true;
        return inflate;
    }
    private boolean isViewVisible;//view是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        Log.d("lee","体育是否可见："+isViewVisible);
        if(isViewVisible&&ISVIEWCREATE){
            //第一次进入触发自动刷新，演示效果
            Log.d("lee","mRefreshLayout.autoRefresh();");
            mRefreshLayout.autoRefresh();
        }
    }
    private RequestQueue requestQueue;
    private void getSportData(){
        requestQueue=Volley.newRequestQueue(getContext());
        new Thread(){
            @Override
            public void run() {
                stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("lee","获取数据成功");
                        sportBeans = ParseJsonUtil.parseSportJson(s);
                        if(sportBeans.size()==0){
                            handler.sendEmptyMessage(0);
                        }else {
                            handler.sendEmptyMessageDelayed(1,300);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        handler.sendEmptyMessage(0);
                    }
                });
                requestQueue.add(stringRequest);
            }
        }.start();
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
