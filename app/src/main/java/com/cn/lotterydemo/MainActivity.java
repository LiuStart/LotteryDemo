package com.cn.lotterydemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.cn.lotterydemo.adapter.MainAdapter;
import com.cn.lotterydemo.fragment.HomePageFragment;
import com.cn.lotterydemo.fragment.KaijiangFragment;
import com.cn.lotterydemo.fragment.SportFragment;
import com.cn.lotterydemo.fragment.ZoushiFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private List<Fragment> mList; //ViewPager的数据源
    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("lee",item.getGroupId()+"");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_news:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_sport:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_user:

                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(new KaijiangFragment());
        mList.add(new ZoushiFragment());
        mList.add(new SportFragment());
        mList.add(new HomePageFragment());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(this);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(mainAdapter); //视图加载适配器
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        //ViewPager滑动
        switch (position){
            case 0:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_news);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_sport);
                break;
            case 3:
                navigation.setSelectedItemId(R.id.navigation_user);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
