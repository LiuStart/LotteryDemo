package com.cn.lottery.leeyybssc;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cn.lottery.leeyybssc.util.AppInstallReceiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/5.
 */

public class DownLoadActivity extends AppCompatActivity{
    int k=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                update();
            }
            if(msg.what==2){
                k++;
                progesss.setProgress((int)Double.parseDouble(df.format(process/total*100)));
                progesssValue.setText(new StringBuffer().append(progesss.getProgress()).append("%"));
                setPosWay1();
            }
        }
    };
    private ProgressBar progesss;
    private TextView progesssValue;
    DecimalFormat    df;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        progesss=findViewById(R.id.progesss1);
        progesssValue = (TextView) findViewById(R.id.progesss_value1);
        regist();
        df= new DecimalFormat("######0.00");
        String url = getIntent().getStringExtra("url");
        downloadFile(url);
    }

    private void regist() {
        AppInstallReceiver receiver=new AppInstallReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addDataScheme("package");
        registerReceiver(receiver,intentFilter);
    }

    int total;
    double process = 0;
    public  void downloadFile(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        Call call = client.newCall(request);
        Log.d("lee", url);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("lee", "onFailure::" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("lee", "onResponse::" + response.code());
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                if (inputStream != null) {
                    File file = new File(Environment.getExternalStorageDirectory() + "/bjsc");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file1 = new File(
                            Environment.getExternalStorageDirectory() + "/bjsc",
                            "115.apk");

                    total = (int)response.body().contentLength();
                    fileOutputStream = new FileOutputStream(file1);
                    byte[] buf = new byte[2000]; // 这个是缓冲区
                    int ch = -1;

                    while ((ch = inputStream.read(buf)) != -1) {
                        fileOutputStream.write(buf, 0, ch);
                        process += ch;
                        Log.d("lee","进度："+process+"//"+total);
                        // 这里就是关键的实时更新进度了！
                        handler.sendEmptyMessage(2);
                    }
                }
                fileOutputStream.flush();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                handler.sendEmptyMessageDelayed(1, 500);
            }
        });
    }

    protected  void update() {
        // TODO Auto-generated method stub
        Log.d("lee", "开始执行安装: ");
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.fromFile(new File(
                        Environment.getExternalStorageDirectory(), "ssc.apk")),"application/vnd.android.package-archive");
                startActivity(intent);*/
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File apkFile = new File(Environment.getExternalStorageDirectory() + "/bjsc/115.apk");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.d("lee", "版本大于 N ，开始使用 fileProvider 进行安装");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    DownLoadActivity.this
                    , "com.cn.lottery.leeyybssc.fileprovider"
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Log.d("lee", "正常进行安装");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    private void setPosWay1() {
        progesssValue.post(new Runnable() {
            @Override
            public void run() {
                setPos();
            }
        });
    }

    /**
     * 设置进度显示在对应的位置
     */
    public void setPos() {
        int w = getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) progesssValue.getLayoutParams();
        int pro = progesss.getProgress();
        int tW = progesssValue.getWidth();
        if (w * pro / 100 + tW * 0.3 > w) {
            params.leftMargin = (int) (w - tW * 1.1);
        } else if (w * pro / 100 < tW * 0.7) {
            params.leftMargin = 0;
        } else {
            params.leftMargin = (int) (w * pro / 100 - tW * 0.7);
        }
        progesssValue.setLayoutParams(params);

    }

}
