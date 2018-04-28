package com.cn.lotterydemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.lotterydemo.LoginActivity;
import com.cn.lotterydemo.R;
import com.cn.lotterydemo.UserInfoActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment{


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
        getActivity().setTitle("用户中心");
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
        String nameHead=USER.getString("NAME","")+"HEAD";
        if(TextUtils.isEmpty(USER.getString(nameHead,""))){
            headImage.setBackgroundResource(R.drawable.userhead);
        }else{
            headImage.setImageBitmap(BitmapFactory.decodeFile(USER.getString(nameHead,"")));
        }
        return inflate;
    }

    @Override
    public void onResume() {
        if(!TextUtils.isEmpty(USER.getString("NAME",""))){
            userHead.setText(USER.getString("NAME",""));
        }
        super.onResume();
    }

}
