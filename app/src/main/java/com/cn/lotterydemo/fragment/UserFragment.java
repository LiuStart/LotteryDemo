package com.cn.lotterydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.lotterydemo.LoginActivity;
import com.cn.lotterydemo.R;

import java.util.ArrayList;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_user, container, false);
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
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return inflate;
    }
}
