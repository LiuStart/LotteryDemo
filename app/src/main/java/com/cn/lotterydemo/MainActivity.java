package com.cn.lotterydemo;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.lotterydemo.adapter.MainAdapter;
import com.cn.lotterydemo.fragment.HomeFragment;
import com.cn.lotterydemo.fragment.NewsFragment;
import com.cn.lotterydemo.fragment.SportFragment;
import com.cn.lotterydemo.fragment.UserFragment;
import com.stonesun.newssdk.NewsAgent;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,OnMenuItemClickListener, OnMenuItemLongClickListener {
    private List<Fragment> mList; //ViewPager的数据源
    private ViewPager viewPager;
    private BottomNavigationView navigation;
    public FragmentManager fragmentManager;
    private SharedPreferences sp;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("lee",item.getGroupId()+"");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    bt.setVisibility(View.VISIBLE);
                   title.setText("首\t\t\t\t页");
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_news:
                    bt.setVisibility(View.GONE);
                    title.setText("资\t\t\t\t讯");
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_sport:
                    bt.setVisibility(View.GONE);
                    title.setText("体\t\t\t\t育");
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_user:
                    bt.setVisibility(View.GONE);
                    title.setText("我\t\t\t\t的");
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };
   private ImageView bt;
   private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
       // initToolbar();
        title=findViewById(R.id.title);
        title.setText("首\t\t\t\t页");
        // 开启调试模式
        NewsAgent.setDebugMode(true);
        // 初始化黑牛
        NewsAgent.init(this);
        NewsAgent.setPermission(this, true);//sdk请求权限
        initMenuFragment();
        initViewPager();
        bt=findViewById(R.id.show);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
            }
        });
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initData();
    }


    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(new HomeFragment());
        mList.add(new NewsFragment());
        mList.add(new SportFragment());
        mList.add(new UserFragment());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(this);
        MainAdapter mainAdapter = new MainAdapter(fragmentManager, mList);
        viewPager.setAdapter(mainAdapter); //视图加载适配器
    }
    private ContextMenuDialogFragment mMenuDialogFragment;
    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
         menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }
    private TextView mToolBarTextView;
    Toolbar mToolbar;
    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolBarTextView = findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        //这里可以隐藏后退的按钮
       /* if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }*/
        /*mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        mToolBarTextView.setText("首\t\t\t\t页");
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);
        MenuObject send = new MenuObject("Send message");
        send.setResource(R.drawable.icn_1);
        MenuObject like = new MenuObject("Like profile");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("Add to friends");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("Add to favorites");
        addFav.setResource(R.drawable.icn_4);

        MenuObject block = new MenuObject("Block user");
        block.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        Log.d("lee","menu.size()"+menu.size());
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }



    MenuItem menuItem;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                /*if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }
    private void initData() {
        sp=getSharedPreferences("SPORT",MODE_PRIVATE);
        sp.edit().putInt("步行者",R.drawable.bxz).commit();
        sp.edit().putInt("独行侠",R.drawable.dxx).commit();
        sp.edit().putInt("公牛",R.drawable.gn).commit();
        sp.edit().putInt("国王",R.drawable.gw).commit();
        sp.edit().putInt("黄蜂",R.drawable.hf).commit();
        sp.edit().putInt("火箭",R.drawable.hj).commit();
        sp.edit().putInt("湖人",R.drawable.hr).commit();
        sp.edit().putInt("活塞",R.drawable.hs).commit();
        sp.edit().putInt("灰熊",R.drawable.hx).commit();
        sp.edit().putInt("掘金",R.drawable.jj).commit();
        sp.edit().putInt("爵士",R.drawable.js).commit();
        sp.edit().putInt("快船",R.drawable.kc).commit();
        sp.edit().putInt("凯尔特人",R.drawable.ketr).commit();
        sp.edit().putInt("开拓者",R.drawable.ktz).commit();
        sp.edit().putInt("雷霆",R.drawable.lt).commit();
        sp.edit().putInt("篮网",R.drawable.lw).commit();
        sp.edit().putInt("老鹰",R.drawable.ly).commit();
        sp.edit().putInt("76人",R.drawable.man).commit();
        sp.edit().putInt("马刺",R.drawable.mc).commit();
        sp.edit().putInt("猛龙",R.drawable.ml).commit();
        sp.edit().putInt("魔术",R.drawable.ms).commit();
        sp.edit().putInt("尼克斯",R.drawable.nks).commit();
        sp.edit().putInt("奇才",R.drawable.qc).commit();
        sp.edit().putInt("骑士",R.drawable.qs).commit();
        sp.edit().putInt("热火",R.drawable.rh).commit();
        sp.edit().putInt("森林狼",R.drawable.sll).commit();
        sp.edit().putInt("鹈鹕",R.drawable.th).commit();
        sp.edit().putInt("太阳",R.drawable.ty).commit();
        sp.edit().putInt("雄鹿",R.drawable.xl).commit();
        sp.edit().putInt("勇士",R.drawable.ys).commit();
    }
}
