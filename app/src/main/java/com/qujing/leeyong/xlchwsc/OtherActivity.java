package com.qujing.leeyong.xlchwsc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.qujing.leeyong.xlchwsc.fragment.MyFragment;

import java.util.ArrayList;

/**
 * Created by runlinkeji on 2017/12/6.
 */

public class OtherActivity extends AppCompatActivity {
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
   // private String[] mTitles = {"英超", "西甲", "德甲", "法甲", "意甲", "中超","欧冠","亚冠"};
    private String[] mTitles = {"排列三", "排列五", "新时时彩", "快三", "快乐十分", "十一选五"};
    SlidingTabLayout tabLayout_3;


    private MyPagerAdapter mAdapter;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        tabLayout_3 = (SlidingTabLayout) findViewById(R.id.tl_3);


        for (int i = 0; i < mTitles.length; i++) {

            MyFragment fg3 = new MyFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("index", i);
          //  bundle1.putString("title", i+"");
            fg3.setArguments(bundle1);
            mFragments2.add(fg3);
        }

        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        tabLayout_3.setViewPager(vp, mTitles, this, mFragments2);


    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments2.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments2.get(position);
        }
    }

}
