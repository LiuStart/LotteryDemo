package com.cn.lottery.leeyybssc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/25.
 */

public class Splash_Activity extends Activity {
    private String[] perss = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private RequestQueue mRequestQueue;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Log.d("lee","intent");
                Intent intent = new Intent(Splash_Activity.this,DownLoadActivity.class);
                //url 要跳转的URL
                intent.putExtra("url",url);
                startActivity(intent);
                finish();
            }else if(msg.what == 2){
                Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        sp=getSharedPreferences("USER",MODE_PRIVATE);
        mRequestQueue = Volley.newRequestQueue(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("lee","检查权限");
            checkPri();
        }else {
            getService();
        }
    }

    private void checkPri() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perss, 111);
            return;
        }else {
            getService();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==111){
            if(grantResults.length>0&&ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                getService();
            }else {
                Toast.makeText(this,"请允许权限！",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else {
            Toast.makeText(this,"请允许权限！",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private String url;
    private void getService() {
        //彩77幸运28 "http://tow.caiqiqi08.com/Lottery_server/get_init_data.php?type=android&appid=237573041297"
        //广东11选5  "http://www.6122c.com/Lottery_server/get_init_data?appid=20180206&type=android"
        //魅族 幸运28  ID 201802070953
        //时时彩 ——应用宝 ID 201803081006
        //北京赛车极光账号：xpjpk66	密码：QQQ111qqq
        //幸运28 应用宝 ID 201803201616
        //华为：http://www.27305.com/frontApi/getAboutUs?appid=(appid)
      //  1804171052
        Log.d("lee","开始联网");
        StringRequest request = new StringRequest(Request.Method.GET, "http://www.6122c.com/Lottery_server/get_init_data?appid=dayingjiass001&type=android", new Response.Listener<String>() {

            //  StringRequest request = new StringRequest(Request.Method.GET, "http://www.6122c.com/Lottery_server/get_init_data?appid=baid2018no8&type=android", new Response.Listener<String>() {
      //    StringRequest request = new StringRequest(Request.Method.GET, "http://www.27305.com/frontApi/getAboutUs?appid=1803072129", new Response.Listener<String>() {

            @Override
            public void onResponse(String string) {

            //http://www.6122c.com/Lottery_server/get_init_data?appid=huawno1&type=android

                Log.d("lee",string);
                JSONObject json;
                try {
                    //{"type":"101","rt_code":"201"}
                    json = new JSONObject(string);
                  /*  int rt_code=json.getInt("type");
                    if(rt_code==200){*/
                    int type=json.getInt("type");
                    if(type==200){
                        Log.d("lee","联网成功");
                        //成功
                        try {
                            JSONObject datajson=json.optJSONObject("data");
                            int  jump=datajson.getInt("is_jump");
                            Log.d("lee","is_jump："+jump);
                            url=datajson.getString("jump_url");
                            Log.d("lee","jump_url："+url);
                            if(jump==1){
                                handler.sendEmptyMessageDelayed(1, 500);
                            }else{
                                handler.sendEmptyMessageDelayed(2, 500);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("lee",e.toString());
                            handler.sendEmptyMessageDelayed(2, 500);
                            sp.edit().putBoolean("GO",false).commit();
                        }


                    }else{

                        Log.d("lee","联网失败");
                        handler.sendEmptyMessageDelayed(2, 500);
                        sp.edit().putBoolean("GO",false).commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessageDelayed(2, 500);
                    sp.edit().putBoolean("GO",false).commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("lee",volleyError.toString());
                handler.sendEmptyMessageDelayed(2, 500);
                sp.edit().putBoolean("GO",false).commit();
            }

        });
        mRequestQueue.add(request);
    }

    /**
     * {
     "id": "1",
     "0": "1",
     "url": "test.333",
     "1": "test.333",
     "type": "android",
     "2": "android",
     "show_url": "1",
     "3": "1",
     "appid": "test",
     "4": "test",
     "comment": "test",
     "5": "test",
     "createAt": "2017-09-05 04:53:06",
     "6": "2017-09-05 04:53:06",
     "updateAt": "2017-09-05 04:53:06",
     "7": "2017-09-05 04:53:06"
     }
     * @param ss
     * @return
     */
    private String getBaseString(String ss){
        String string="";
        if(TextUtils.isEmpty(ss)){
            string= "";
        }else {
            string=new String(Base64.decode(ss.getBytes(),Base64.DEFAULT));
        }
       Log.d("lee","string:"+string);
        return string;
    }

}
