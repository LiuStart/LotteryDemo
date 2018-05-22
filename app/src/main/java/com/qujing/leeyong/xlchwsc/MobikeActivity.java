package com.qujing.leeyong.xlchwsc;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobike.library.MobikeView;

import java.util.ArrayList;
import java.util.Random;

public class MobikeActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private MobikeView mobikeView;
    private Button btnTest;
    private ArrayList<TextView> arrayList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                for (TextView textView : arrayList) {
                    textView.setText(new Random().nextInt(25) + "");
                }
            } else if (msg.what == 2) {
                startLottery();
            }

        }
    };
    private boolean ISFIRST;
    private Spinner spinner;
    FrameLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobikeactivity);
        ISFIRST = true;
        mobikeView = findViewById(R.id.mobike_view);
        /*mobikeView.getmMobike().setDensity(1.5f);
        mobikeView.getmMobike().setFriction(0.3f);
        mobikeView.getmMobike().setRestitution(0.2f);
        mobikeView.getmMobike().setRatio(mobikeView.getmMobike().getRatio());
        mobikeView.getmMobike().update();*/
        btnTest = (Button) findViewById(R.id.test);
        layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        initViews(3);
                        break;
                    case 1:
                        initViews(7);
                        break;
                    case 2:
                        initViews(7);
                        break;
                    case 3:
                        initViews(3);
                        break;
                    case 4:
                        initViews(5);
                        break;
                    case 5:
                        initViews(8);
                        break;
                    case 6:
                        initViews(7);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLottery();
            }
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    private void startLottery() {
        if (ISFIRST) {
            ISFIRST = false;
            mobikeView.getmMobike().setDensity(1.2f);
            mobikeView.getmMobike().setFriction(0.1f);
            mobikeView.getmMobike().setRestitution(0.1f);
            mobikeView.getmMobike().setRatio(mobikeView.getmMobike().getRatio());
            mobikeView.getmMobike().update();
        }
        for (TextView textView : arrayList) {
            textView.setText("？");
        }
        mobikeView.getmMobike().random();
        handler.removeMessages(1);
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 14;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                //  vibrator.vibrate(100);
                vibrator.vibrate(300);
                Message msg = new Message();
                msg.what = 2;
                handler.removeMessages(2);
                handler.sendMessageDelayed(msg, 250);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mobikeView.getmMobike().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lee", "stop");
        mobikeView.getmMobike().onStop();
    }


    private void initViews(int count) {
        final float scale = getResources().getDisplayMetrics().density;
        int childCount = mobikeView.getChildCount();

        Log.d("lee", "childCount:" + childCount);
        if (childCount > 0) {
            if (childCount > count) {
                int i = childCount - count;
                for (TextView textView : arrayList) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("？");
                }
                for (int k = 0; k < i; k++) {
                    TextView textView = arrayList.get(k);
                    textView.setVisibility(View.GONE);
                }

            }
            if (childCount < count) {
                int j = count - childCount;
                for (int i = 0; i < j; i++) {
                    TextView textview = (TextView) getLayoutInflater().inflate(R.layout.item_textview, null);
                    textview.setText("？");
                    textview.setTextSize(18);
                    textview.setHeight((int) (50 * scale + 0.5f));
                    textview.setWidth((int) (50 * scale + 0.5f));
                    if (i == count - 1) {
                        textview.setTag(R.id.mobike_view_circle_tag, false);
                    } else {
                        textview.setTag(R.id.mobike_view_circle_tag, true);
                    }
                    arrayList.add(textview);
                    mobikeView.addView(textview, layoutParams);
                }
            }
            if(childCount == count){
                for (TextView textView : arrayList) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("？");
                }
            }
        } else {
            arrayList = new ArrayList<>();
            //球的个数
            for (int i = 0; i < count; i++) {
                TextView textview = (TextView) getLayoutInflater().inflate(R.layout.item_textview, null);
                textview.setText("？");
                textview.setTextSize(18);
                textview.setHeight((int) (50 * scale + 0.5f));
                textview.setWidth((int) (50 * scale + 0.5f));

            /*ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgs[i]);
            if(i == imgs.length - 1){
                imageView.setTag(R.id.mobike_view_circle_tag,false);
            }else{
                imageView.setTag(R.id.mobike_view_circle_tag,true);
            }*/
                if (i == count - 1) {
                    textview.setTag(R.id.mobike_view_circle_tag, false);
                } else {
                    textview.setTag(R.id.mobike_view_circle_tag, true);
                }
                arrayList.add(textview);
                mobikeView.addView(textview, layoutParams);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && data != null) {
            float density = data.getFloatExtra("density", -1);
            float friction = data.getFloatExtra("friction", -1);
            float restitution = data.getFloatExtra("restitution", -1);
            float ratio = data.getFloatExtra("ratio", -1);
            mobikeView.getmMobike().setDensity(density);
            mobikeView.getmMobike().setFriction(friction);
            mobikeView.getmMobike().setRestitution(restitution);
            mobikeView.getmMobike().setRatio(ratio);
            mobikeView.getmMobike().update();

        }
    }

}
