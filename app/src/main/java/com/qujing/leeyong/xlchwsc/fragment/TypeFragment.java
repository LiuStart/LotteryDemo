package com.qujing.leeyong.xlchwsc.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.qujing.leeyong.xlchwsc.R;
import com.qujing.leeyong.xlchwsc.adapter.ViewPagerAdapter;
import com.qujing.leeyong.xlchwsc.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class TypeFragment extends Fragment {


    private String[] weburl1 = {"http://m.jc258.cn/europe/home/leaguerank/36?mi=0",
            "http://m.jc258.cn/europe/home/leaguerank/31?mi=0",
            "http://m.jc258.cn/europe/home/leaguerank/8?mi=0"
            , "http://m.jc258.cn/europe/home/leaguerank/11?mi=0",
            "http://m.jc258.cn/europe/home/leaguerank/34?mi=0",
            "http://m.jc258.cn/europe/home/leaguerank/60?mi=0"
            , "http://m.jc258.cn/europe/home/cuprank/103",
            "http://m.jc258.cn/europe/home/cuprank/192"};

    private String[] weburl2 = {"http://m.jc258.cn/europe/home/schedule/36",
            "http://m.jc258.cn/europe/home/schedule/31",
            "http://m.jc258.cn/europe/home/schedule/8"
            , "http://m.jc258.cn/europe/home/schedule/11",
            "http://m.jc258.cn/europe/home/schedule/34",
            "http://m.jc258.cn/europe/home/schedule/60"
            , "http://m.jc258.cn/europe/home/cupmatch/103", "http://m.jc258.cn/europe/home/cupmatch/192"};
    private String[] weburl3 = {"http://m.jc258.cn/europe/home/resultstatistics/36",
            "http://m.jc258.cn/europe/home/resultstatistics/31",
            "http://m.jc258.cn/europe/home/resultstatistics/8"
            , "http://m.jc258.cn/europe/home/resultstatistics/11",
            "http://m.jc258.cn/europe/home/resultstatistics/34",
            "http://m.jc258.cn/europe/home/resultstatistics/60"
            , "http://m.jc258.cn/europe/home/cupdata/103",
            "http://m.jc258.cn/europe/home/cupdata/192"};

    int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("index");

    }

    RadioGroup myRg;
    List<Fragment> fm_list;
    NoScrollViewPager viewpager;
    ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.type_frament, null);
        myRg = (RadioGroup) v.findViewById(R.id.contacts_rg);
        viewpager = (NoScrollViewPager) v.findViewById(R.id.viewpager);
        fm_list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String weburl = "";
            if (i == 0) {
                weburl = weburl1[index];

                WebviewFrament fg3 = new WebviewFrament();
                Bundle bundle1 = new Bundle();
                bundle1.putString("url", weburl);
                fg3.setArguments(bundle1);
                fm_list.add(fg3);
            } else if (i == 1) {
                weburl = weburl3[index];
                WebviewFrament fg3 = new WebviewFrament();
                Bundle bundle1 = new Bundle();
                bundle1.putString("url", weburl);
                fg3.setArguments(bundle1);
                fm_list.add(fg3);
            } else if (i == 2) {
                weburl = weburl2[index];
                WebviewFrament fg3 = new WebviewFrament();
                Bundle bundle1 = new Bundle();
                bundle1.putString("url", weburl);
                fg3.setArguments(bundle1);
                fm_list.add(fg3);
            }

        }
        adapter = new ViewPagerAdapter(getChildFragmentManager(), fm_list,
                null);
        viewpager.setAdapter(adapter);
        viewpager.setNoScroll(true);
        myRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int index = 0;
                switch (checkedId) {
                    case R.id.contacts_rb1:
                        index = 0;
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.contacts_rb3:
                        index = 1;
                        viewpager.setCurrentItem(1);
                        break;
//                    case R.id.contacts_rb3:
//                        index = 1;
//                        viewpager.setCurrentItem(2);
//                        break;

                }

            }
        });
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        myRg.check(R.id.contacts_rb1);
                        break;
                    case 1:
                        myRg.check(R.id.contacts_rb3);
                        break;
//                    case 2:
//                        myRg.check(R.id.contacts_rb3);
//                        break;

                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        return v;
    }


}