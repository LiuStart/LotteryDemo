package com.cn.cqsscfx;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
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

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baidu.mapapi.SDKInitializer;
import com.cn.cqsscfx.adapter.MainAdapter;
import com.cn.cqsscfx.fragment.HomeFragment;
import com.cn.cqsscfx.fragment.NewsFragment;
import com.cn.cqsscfx.fragment.SportFragment;
import com.cn.cqsscfx.fragment.UserFragment;
import com.stonesun.newssdk.NewsAgent;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,OnMenuItemClickListener, OnMenuItemLongClickListener, BottomNavigationBar.OnTabSelectedListener {
    private List<Fragment> mList; //ViewPager的数据源
    private ViewPager viewPager;
    private BottomNavigationBar bottomNavigationBar;
    public FragmentManager fragmentManager;
    private SharedPreferences sp;

       /* @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("lee",item.getGroupId()+"");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    bt.setVisibility(View.VISIBLE);
                   title.setText("开\t\t\t\t奖");
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
        }*/
   private ImageView bt;
   private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SDKInitializer.initialize(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
       // initToolbar();
        title=findViewById(R.id.title);
        title.setText("首\t\t\t\t页");

        // 开启调试模式
        NewsAgent.setDebugMode(true);
        // 初始化黑牛
        NewsAgent.init(this);
        NewsAgent.setPermission(this, true);//sdk请求权限
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPri();
        }*/
        Log.d("lee","test");
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
        bottomNavigationBar =findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        initBottomNavigationBar();
        initData();
        initCaipiaoData();

        /*SweetAlertDialog sd = new SweetAlertDialog(this);
        sd.setCancelable(true);
        sd.setCanceledOnTouchOutside(true);
        sd.show();*/
        /*Dialog loadingDialog = DialogUtil.createLoadingDialog(this, "正在加载");
        loadingDialog.show();*/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (null != extras) {
            String  ss = extras.getString("message");
            setCostomMsg(ss);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPri() {
        if (! Settings.canDrawOverlays(MainActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent,10);
        }
    }

    private void setCostomMsg(String ss) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("提示信息");
        builder.setMessage(ss);
        builder.setIcon(R.drawable.btn_about_on);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog  alertDialog = builder.create();
        alertDialog.show();
    }

    private void initCaipiaoData() {
        SharedPreferences caipiao = getSharedPreferences("CAIPIAO", Context.MODE_PRIVATE);
        caipiao.edit().putString("重庆时时彩 - 高频", "http://f.apiplus.net/cqssc-10.json").commit();
        caipiao.edit().putString("黑龙江时时彩 - 高频", "http://f.apiplus.net/hljssc-10.json").commit();
        caipiao.edit().putString("新疆时时彩 - 高频", "http://f.apiplus.net/xjssc-10.json").commit();
        caipiao.edit().putString("超级大乐透", "http://f.apiplus.net/dlt-10.json").commit();
        caipiao.edit().putString("福彩3d", "http://f.apiplus.net/fc3d-10.json").commit();
        caipiao.edit().putString("排列3", "http://f.apiplus.net/pl3-10.json").commit();
        caipiao.edit().putString("排列5", "http://f.apiplus.net/pl5-10.json").commit();
        caipiao.edit().putString("七乐彩", "http://f.apiplus.net/qlc-10.json").commit();
        caipiao.edit().putString("七星彩", "http://f.apiplus.net/qxc-10.json").commit();
        caipiao.edit().putString("双色球", "http://f.apiplus.net/ssq-10.json").commit();
        caipiao.edit().putString("六场半全场", "http://f.apiplus.net/zcbqc-10.json").commit();
        caipiao.edit().putString("四场进球彩", "http://f.apiplus.net/zcjqc-10.json").commit();
        caipiao.edit().putString("安徽11选5 - 高频", "http://f.apiplus.net/ah11x5-10.json").commit();
        caipiao.edit().putString("北京11选5 - 高频", "http://f.apiplus.net/bj11x5-10.json").commit();
        caipiao.edit().putString("福建11选5 - 高频", "http://f.apiplus.net/fj11x5-10.json").commit();
        caipiao.edit().putString("广东11选5 - 高频", "http://f.apiplus.net/gd11x5-10.json").commit();
        caipiao.edit().putString("甘肃11选5 - 高频", "http://f.apiplus.net/gs11x5-10.json").commit();
        caipiao.edit().putString("广西11选5 - 高频", "http://f.apiplus.net/fx11x5-10.json").commit();
        SharedPreferences caipiaoIcon = getSharedPreferences("CAIPIAOICON", Context.MODE_PRIVATE);
        SharedPreferences caipiaoName = getSharedPreferences("CAIPIAONAME", Context.MODE_PRIVATE);
        caipiaoIcon.edit().putInt("dlt", R.drawable.dlt).commit();
        caipiaoIcon.edit().putInt("cqssc", R.drawable.cqssc).commit();
        caipiaoIcon.edit().putInt("qxc", R.drawable.qxc).commit();
        caipiaoIcon.edit().putInt("zcjqc", R.drawable.zcjqc).commit();
        caipiaoIcon.edit().putInt("fc3d", R.drawable.fc3d).commit();
        caipiaoIcon.edit().putInt("pl3", R.drawable.pl3).commit();
        caipiaoIcon.edit().putInt("pl5", R.drawable.pl5).commit();
        caipiaoIcon.edit().putInt("qlc", R.drawable.qlc).commit();
        caipiaoIcon.edit().putInt("ssq", R.drawable.ssq).commit();

        caipiaoName.edit().putString("dlt", "大乐透").commit();
        caipiaoName.edit().putString("cqssc", "重庆时时彩").commit();
        caipiaoName.edit().putString("qxc", "七星彩").commit();
        caipiaoName.edit().putString("zcjqc", "四场进球彩").commit();
        caipiaoName.edit().putString("fc3d", "福彩3D").commit();
        caipiaoName.edit().putString("pl3", "排列3").commit();
        caipiaoName.edit().putString("pl5","排列5").commit();
        caipiaoName.edit().putString("qlc", "七乐彩").commit();
        caipiaoName.edit().putString("ssq", "双色球").commit();
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
        mToolBarTextView.setText("开\t\t\t\t奖");
    }
    //初始化底部导航条
    private void initBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        /*badgeItem = new BadgeItem()
                .setHideOnSelect(true) //设置被选中时隐藏角标
                ;*/
        /**
         * 设置模式
         * 1、BottomNavigationBar.MODE_DEFAULT 默认
         * 如果Item的个数<=3就会使用MODE_FIXED模式，否则使用MODE_SHIFTING模式
         *
         * 2、BottomNavigationBar.MODE_FIXED 固定大小
         * 填充模式，未选中的Item会显示文字，没有换挡动画。
         *
         * 3、BottomNavigationBar.MODE_SHIFTING 不固定大小
         * 换挡模式，未选中的Item不会显示文字，选中的会显示文字。在切换的时候会有一个像换挡的动画
         */
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        /**
         * 设置背景的样式
         * 1、BottomNavigationBar.BACKGROUND_STYLE_DEFAULT 默认
         * 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC 。
         * 如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
         *
         * 2、BottomNavigationBar.BACKGROUND_STYLE_STATIC 导航条的背景色是白色，
         * 加上setBarBackgroundColor（）可以设置成你所需要的任何背景颜色
         * 点击的时候没有水波纹效果
         *
         * 3、BottomNavigationBar.BACKGROUND_STYLE_RIPPLE 导航条的背景色是你设置的处于选中状态的
         * Item的颜色（ActiveColor），也就是setActiveColorResource这个设置的颜色
         * 点击的时候有水波纹效果
         */
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //设置导航条背景颜色
        //在BACKGROUND_STYLE_STATIC下，表示整个容器的背景色。
        // 而在BACKGROUND_STYLE_RIPPLE下，表示选中Item的图标和文本颜色。默认 Color.WHITE
        bottomNavigationBar.setBarBackgroundColor(R.color.bg_bottom);
        //选中时的颜色,在BACKGROUND_STYLE_STATIC下，表示选中Item的图标和文本颜色。
        // 而在BACKGROUND_STYLE_RIPPLE下，表示整个容器的背景色。默认Theme's Primary Color
        //bottomNavigationBar.setActiveColor(R.color.black);
        //未选中时的颜色，表示未选中Item中的图标和文本颜色。默认为 Color.LTGRAY
        //bottomNavigationBar.setInActiveColor("#FFFFFF");



        bottomNavigationBar.addItem(new BottomNavigationItem( R.drawable.ic_home_black_24dp, "首页").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_fiber_new_black_24dp, "资讯").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_fitness_center_black_24dp, "体育").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_black_24dp, "我的").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成

        //如果使用颜色的变化不足以展示一个选项Item的选中与非选中状态，
        // 可以使用BottomNavigationItem.setInActiveIcon()方法来设置。
//        new BottomNavigationItem(R.drawable.ic_home_white_24dp, "公交")//这里表示选中的图片
//                .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.ic_launcher));//非选中的图片
    }
    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject close = new MenuObject("取 消");
        close.setResource(R.drawable.icn_close);
        MenuObject send = new MenuObject("幸运摇一摇");
        send.setResource(R.drawable.yaoyiyao);
        MenuObject like = new MenuObject("我要去彩站");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.map);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("最新走势图");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.zoushi));
        addFr.setDrawable(bd);

       /* MenuObject addFav = new MenuObject("Add to favorites");
        addFav.setResource(R.drawable.icn_4);

        MenuObject block = new MenuObject("Block user");
        block.setResource(R.drawable.icn_5);
*/
        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        /*menuObjects.add(addFav);
        menuObjects.add(block);*/
        return menuObjects;
    }

    @Override
    public void onTabSelected(int position) {
       // title.setText("我\t\t\t\t的");
        viewPager.setCurrentItem(position);
        switch (position){
            case 0:
                title.setText("首\t\t\t\t页");
                break;
            case 1:
                title.setText("资\t\t\t\t讯");
                break;
            case 2:
                title.setText("体\t\t\t\t育");
                break;
            case 3:
                title.setText("我\t\t\t\t的");
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }


    @Override
    public void onPageSelected(int position) {
        //ViewPager滑动
            bottomNavigationBar.selectTab(position);

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
        switch (position){
            case 0:
                //取消
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, MobikeActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, ZoushiActivty.class));
                break;

        }

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
