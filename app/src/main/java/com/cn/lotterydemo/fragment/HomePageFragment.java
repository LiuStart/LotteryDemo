package com.cn.lotterydemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.lotterydemo.R;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment{


    public HomePageFragment() {
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
        View inflate = inflater.inflate(R.layout.item_lottery_info, container, false);
        list=new ArrayList<>();
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
        }
        return inflate;
    }
}
