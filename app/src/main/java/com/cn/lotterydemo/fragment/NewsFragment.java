package com.cn.lotterydemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.lotterydemo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment{


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_news, container, false);
        // Inflate the layout for this fragment
        return inflate;
    }
}