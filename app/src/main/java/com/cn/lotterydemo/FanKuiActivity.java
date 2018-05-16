package com.cn.lotterydemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.lotterydemo.util.CheckUtil;

/**
 * Created by Administrator on 2018/5/3.
 */

public class FanKuiActivity extends AppCompatActivity{
   private  TextView mTextCount;
   private Handler handler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           if(msg.what==1){
               loadingDialog.dismiss();
               Toast.makeText(FanKuiActivity.this,"提交成功！",Toast.LENGTH_SHORT).show();
               finish();
           }
           if(msg.what==2){
               loadingDialog = createLoadingDialog(FanKuiActivity.this, "正在提交中...");
               Log.d("lee","show");
               loadingDialog.show();
               handler.sendEmptyMessageDelayed(1,1000);
           }

       }
   };
    Dialog loadingDialog;
    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fankuiactivity);
        editText=findViewById(R.id.et_content);
         mTextCount=findViewById(R.id.text_count);
        editText.addTextChangedListener(new TextWatcher() { //对EditText进行监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTextCount.setText("剩余字数：" + (200 - editable.length()));
            }
        });
        Button bt=findViewById(R.id.btn_submit);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString())){
                    Toast.makeText(FanKuiActivity.this,"内容不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(CheckUtil.isNetworkAvailable(FanKuiActivity.this)){

                    handler.sendEmptyMessage(2);
                }else {
                    Toast.makeText(FanKuiActivity.this,"当前网络不可用！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout =v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage =v.findViewById(R.id.img);
        TextView tipTextView = v.findViewById(R.id.tipTextView);
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_dialog);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//指定会全局弹出
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }

}
