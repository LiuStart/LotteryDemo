package com.qujing.leeyong.klctsz;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qujing.leeyong.klctsz.bean.WeatherBean;
import com.qujing.leeyong.klctsz.util.LocationUtil;
import com.qujing.leeyong.klctsz.util.ParseJsonUtil;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by Administrator on 2018/5/10.
 */

public class WeatherActivity extends AppCompatActivity {
    //后退
    private ImageView mBtBack;
    /**
     * 北 京
     */
    private TextView mPlace;
    /**
     * 多云
     */
    private TextView mDesc;
    /**
     * 更新时间：18:00
     */
    private TextView mUpdateTime;
    /**
     * 进度条
     */
    private ProgressBar mPb;
    /**
     * 26
     */
    private TextView mTemp;
    /**
     * 紫外线指数   弱
     */
    private TextView mHzdesc;
    /**
     * 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
     */
    private TextView mHzmsg;
    /**
     * 感冒指数   少发
     */
    private TextView mGmdesc;
    /**
     * 各项气象条件适宜，无明显降温过程，发生感冒机率较低。
     */
    private TextView mGmmsg;
    /**
     * 洗车指数   较适宜
     */
    private TextView mXcdesc;
    /**
     * 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
     */
    private TextView mXcmsg;
    /**
     * 穿衣指数   舒适
     */
    private TextView mCydesc;
    /**
     * 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
     */
    private TextView mCymsg;
    /**
     * 运动指数   适宜
     */
    private TextView mYddesc;
    /**
     * 天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。
     */
    private TextView mYdmsg;
    //风级数
    private TextView windpower;
    //pm描述
    private TextView pmDesc;
    //pm
    private TextView pm;
    //湿度
    private TextView shidu;
    //风
    private TextView wind;
    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

 //   private TextView place;
 private String[] perss =new String[]{Manifest.permission.LOCATION_HARDWARE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                    mPlace.setText(weatherBean.getPlace());
                    mDesc.setText(weatherBean.getDesc());
                    mTemp.setText(weatherBean.getTemp());
                    mUpdateTime.setText("更新时间："+weatherBean.getUpdateTime());
                    shidu.setText(weatherBean.getShidu()+"%");
                    pm.setText(weatherBean.getPm());
                    pmDesc.setText(weatherBean.getPmdesc());
                    windpower.setText(weatherBean.getWindpower());
                    wind.setText(weatherBean.getWind());
                    mHzdesc.setText(weatherBean.getSundesc());
                    mHzmsg.setText(weatherBean.getSunmsg());
                    mGmdesc.setText(weatherBean.getGm());
                    mGmmsg.setText(weatherBean.getGmmsg());
                    mXcdesc.setText(weatherBean.getXc());
                    mXcmsg.setText(weatherBean.getXcmsg());
                    mCydesc.setText(weatherBean.getCy());
                    mCymsg.setText(weatherBean.getCymsg());
                    mYddesc.setText(weatherBean.getYd());
                    mYdmsg.setText(weatherBean.getYdmsg());
                    mPb.setVisibility(View.INVISIBLE);
            }

        }
    };
    private WeatherBean weatherBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        initView();
      //  place = $(R.id.place);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPri();
        }
        getLocation();

    }

    private void getLocation() {
        mPb.setVisibility(View.VISIBLE);
        LocationUtil.init(WeatherActivity.this);
        LocationUtil.startLocal(new LocationUtil.localCompleteListener() {
            @Override
            public void localComplete(String city) {
                getWeather(city);
            }
        },true);
    }

    private void checkPri() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.LOCATION_HARDWARE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perss, 321);
            return;
        }
    }
    private void initView() {
        mBtBack = (ImageView) findViewById(R.id.bt_back);
        mBtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPlace = (TextView) findViewById(R.id.place);
        mDesc = (TextView) findViewById(R.id.desc);
        mUpdateTime = (TextView) findViewById(R.id.update_time);
        mPb = (ProgressBar) findViewById(R.id.pb);
        mTemp = (TextView) findViewById(R.id.temp);
        mHzdesc = (TextView) findViewById(R.id.hzdesc);
        mHzmsg = (TextView) findViewById(R.id.hzmsg);
        mGmdesc = (TextView) findViewById(R.id.gmdesc);
        mGmmsg = (TextView) findViewById(R.id.gmmsg);
        mXcdesc = (TextView) findViewById(R.id.xcdesc);
        mXcmsg = (TextView) findViewById(R.id.xcmsg);
        mCydesc = (TextView) findViewById(R.id.cydesc);
        mCymsg = (TextView) findViewById(R.id.cymsg);
        mYddesc = (TextView) findViewById(R.id.yddesc);
        mYdmsg = (TextView) findViewById(R.id.ydmsg);
        pm=$(R.id.pm);
        pmDesc=$(R.id.pmdesc);
        wind=$(R.id.wind);
        windpower=$(R.id.windpower);
        shidu=$(R.id.shidu);
    }
    RequestQueue requestQueue;
    private void getWeather(final String city) {
        new Thread() {
            @Override
            public void run() {
                requestQueue  = Volley.newRequestQueue(WeatherActivity.this);
                StringRequest request = new StringRequest(Request.Method.GET, "https://way.jd.com/jisuapi/weather?city="+city+"&appkey=771f04e5d315a61f73e99d4b402f62ed", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                         weatherBean = ParseJsonUtil.parseWeather(s);
                         handler.sendEmptyMessage(1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("lee", volleyError.toString());
                        //   handler.sendEmptyMessage(2);
                        Toast.makeText(getContext(), "数据获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(request);
            }

        }.start();
    }
}
