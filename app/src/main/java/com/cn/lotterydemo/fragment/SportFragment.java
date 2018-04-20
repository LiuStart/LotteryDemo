package com.cn.lotterydemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.lotterydemo.R;

/**
 * Created by Administrator on 2018/4/20.
 */

public class SportFragment extends Fragment {
    public SportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.sport_fragment, container, false);
        return inflate;
    }
}
