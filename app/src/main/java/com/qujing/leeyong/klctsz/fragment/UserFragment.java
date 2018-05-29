package com.qujing.leeyong.klctsz.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qujing.leeyong.klctsz.FanKuiActivity;
import com.qujing.leeyong.klctsz.LoginActivity;
import com.qujing.leeyong.klctsz.R;
import com.qujing.leeyong.klctsz.ShuoMingActivity;
import com.qujing.leeyong.klctsz.UserInfoActivity;
import com.qujing.leeyong.klctsz.util.BitMapUtil;
import com.qujing.leeyong.klctsz.util.CheckUtil;
import com.qujing.leeyong.klctsz.util.DialogUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {


    public UserFragment() {
        // Required empty public constructor
    }

    private TextView cityWewather;
    private TextView city;
    private ImageView temp;
    private ImageView tmpSrc;
    private LinearLayout wanfan;
    private LinearLayout shuoming;
    private LinearLayout updata;
    private LinearLayout login;
    private ArrayList<String>list;
    private TextView userHead;
    private SharedPreferences USER;
    private CircleImageView headImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_user, container, false);
        headImage=inflate.findViewById(R.id.userhead);
        /*list=new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("13");
        list.add("4");
        list.add("8");
        list.add("0");
        list.add("33");
        final float scale = getContext().getResources().getDisplayMetrics().density;
        LinearLayout group=(LinearLayout)inflate.findViewById(R.id.group);
        for (String num:list){
            TextView textview = (TextView)inflater.inflate(R.layout.item_textview, null);
            textview.setText(num);
            ViewGroup.LayoutParams layoutParams = textview.getLayoutParams();
            textview.setHeight((int)(25 * scale + 0.5f));
            textview.setWidth((int)(25 * scale + 0.5f));
            group.addView(textview);
        }*/
        getActivity().setTitle("设置中心");
        RelativeLayout loginview= (RelativeLayout) inflate.findViewById(R.id.login_view);
        loginview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(USER.getString("NAME",""))){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                }
            }
        });
        USER=getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userHead=inflate.findViewById(R.id.userview);
        if(!TextUtils.isEmpty(USER.getString("NAME",""))){
            userHead.setText(USER.getString("NAME",""));
        }
        Log.d("lee","onCreateView");
        Log.d("lee",""+headImage.getWidth());
        Log.d("lee",""+headImage.getHeight());
        com.zcw.togglebutton.ToggleButton toggleButton=inflate.findViewById(R.id.toggle);
        toggleButton.setToggleOn();
        RelativeLayout fankui=inflate.findViewById(R.id.re_fankui);
        fankui.setOnClickListener(this);
        RelativeLayout gengxin=inflate.findViewById(R.id.re_gengxin);
        gengxin.setOnClickListener(this);
        RelativeLayout tuisong=inflate.findViewById(R.id.re_tuisong);
        tuisong.setOnClickListener(this);
        RelativeLayout qingchu=inflate.findViewById(R.id.re_qingchu);
        qingchu.setOnClickListener(this);
        RelativeLayout shengming=inflate.findViewById(R.id.re_shengming);
        shengming.setOnClickListener(this);
        RelativeLayout fengxiang=inflate.findViewById(R.id.re_fenxiang);
        fengxiang.setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onResume() {
        Log.d("lee","onResume");

        if(!TextUtils.isEmpty(USER.getString("NAME",""))){
            userHead.setText(USER.getString("NAME",""));
            headImage.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("lee",""+headImage.getWidth());
                    Log.d("lee",""+headImage.getHeight());
                    String nameHead=USER.getString("NAME","")+"HEAD";
                    if(TextUtils.isEmpty(USER.getString(nameHead,""))){
                        headImage.setBackgroundResource(R.drawable.userhead);
                    }else{
                        headImage.setImageBitmap(BitMapUtil.loadBitmap(USER.getString(nameHead,""),headImage));
                        // headImage.setImageBitmap(BitmapFactory.decodeFile(USER.getString(nameHead,"")));
                    }
                }
            });
        }else{
            userHead.setText("登陆\\注册");
            headImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.userhead));
        }


        super.onResume();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            loadingDialog.dismiss();
            Toast.makeText(getContext(),"当前为最新版本！",Toast.LENGTH_SHORT).show();
        }
    };
    Dialog loadingDialog;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_fankui:
                    startActivity(new Intent(getContext(), FanKuiActivity.class));
                break;
            case R.id.re_gengxin:
                    if(CheckUtil.isNetworkAvailable(getContext())){
                        loadingDialog = DialogUtil.createLoadingDialog(getContext(), "检查更新中");
                        loadingDialog.show();
                        handler.sendEmptyMessageDelayed(1,1000);
                    }else{
                        Toast.makeText(getContext(),"当前网络不可用！",Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.re_tuisong:
                break;
            case R.id.re_qingchu:
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),AlertDialog.THEME_HOLO_LIGHT);
                builder.setTitle("提示信息");
                builder.setMessage("确定要删除吗？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.re_shengming:
                startActivity(new Intent(getContext(), ShuoMingActivity.class));
                break;
            case R.id.re_fenxiang:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, "给你分享一个免费好用的软件，名字叫：" +getResources().getString(R.string.app_name)+"，快去下载体验吧！");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "分享"));
                break;
        }
    }
}
