package com.cn.lotterydemo.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.cn.lotterydemo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    private Spinner spinner;
    private ListView  listview;
    private WebView webView;
    private SharedPreferences sp;
    private RelativeLayout head;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
       /* Button login=inflate.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        return inflate;
    }




}
