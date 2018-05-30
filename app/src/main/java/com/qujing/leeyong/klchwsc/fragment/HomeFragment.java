package com.qujing.leeyong.klchwsc.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dalong.carrousellayout.CarrouselLayout;
import com.dalong.carrousellayout.OnCarrouselItemClickListener;
import com.qujing.leeyong.klchwsc.HistoryActivity;
import com.qujing.leeyong.klchwsc.MapActivity;
import com.qujing.leeyong.klchwsc.NewsEveryDay;
import com.qujing.leeyong.klchwsc.OtherActivity;
import com.qujing.leeyong.klchwsc.R;
import com.qujing.leeyong.klchwsc.SsqActivity;
import com.qujing.leeyong.klchwsc.WeatherActivity;
import com.qujing.leeyong.klchwsc.XuanHaoActivity;
import com.qujing.leeyong.klchwsc.adapter.KaijiangAdapter;
import com.qujing.leeyong.klchwsc.bean.KaiJiangInfo;
import com.qujing.leeyong.klchwsc.util.CheckUtil;
import com.qujing.leeyong.klchwsc.util.DialogUtil;
import com.qujing.leeyong.klchwsc.util.ParseJsonUtil;
import com.yayandroid.rotatable.Rotatable;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.CubeOutTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnBannerListener, View.OnClickListener {


    private View view;
    private ImageView mPl3;
    private ImageView mQlc;
    private ImageView mFc3d;
    private ImageView mCqssc;
    private ImageView mDlt;
    private ImageView mSsq;
    private ImageView mQxc;
    private LinearLayout mFirst;
    private LinearLayout mSecond;
    private LinearLayout mThird;
    private LinearLayout mFourth;
    private LinearLayout mFifth;
    private LinearLayout mSixth;
    private LinearLayout mSeventh;
    private LinearLayout mEighth;
    private LinearLayout mNinth;

    public HomeFragment() {
        // Required empty public constructor
    }

    private Spinner spinner;
    private ListView listview;
    private WebView webView;
    private SharedPreferences sp;
    private RelativeLayout head;
    private ListView listView;
    private Banner banner;
    List list;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                listView.setAdapter(new KaijiangAdapter(getContext(), kaiJiangInfoArrayList));
                handler.sendEmptyMessage(3);
            }
            if (msg.what == 2) {
                handler.sendEmptyMessage(3);
                Toast.makeText(getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 3) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
            if (msg.what == 4) {
                int i = new Random().nextInt(8);
                int j = new Random().nextInt(2);
                if(j%2==0){
                    rotatables.get(i).rotate(Rotatable.ROTATE_Y,360,3000);
                }else {
                    rotatables.get(i).rotate(Rotatable.ROTATE_X,360,3000);
                }
                handler.sendEmptyMessageDelayed(4,1500);
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        String[] urls = getContext().getResources().getStringArray(R.array.url);
        //   list = Arrays.asList(urls);
        list = new ArrayList();
        list.add("assets://weather.jpg");
        list.add("assets://ssqkj.jpg");
        list.add("assets://redian.jpg");
        list.add("assets://nba.jpg");
        listView = inflate.findViewById(R.id.listview);
        banner = inflate.findViewById(R.id.banner);
        String[] tips = getResources().getStringArray(R.array.title);
        // List list = Arrays.asList(urls);
        List list1 = Arrays.asList(tips);
        List<String> titles = new ArrayList(list1);
        banner.setBannerAnimation(CubeOutTransformer.class);
        banner.setDelayTime(3000);
        banner.setBannerTitles(titles);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setImages(BANNER_ITEMS)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
        LinearLayout yaoyiyao = inflate.findViewById(R.id.yaoyiyao);
        yaoyiyao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), XuanHaoActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout caizhan = inflate.findViewById(R.id.gomap);
        caizhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout zoushi = inflate.findViewById(R.id.zoushi);
        zoushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OtherActivity.class);
                startActivity(intent);
            }
        });
        showDialog();
        initCaipiao();
        initView(inflate);
        if (CheckUtil.isNetworkAvailable(getContext())) {
            getLotteryData();
        } else {
            handler.sendEmptyMessage(2);
        }

        return inflate;
    }

    @Override
    public void onClick(View v) {
        Log.d("lee", "onClick");

        switch (v.getId()) {
            case R.id.pl3:

                break;
            case R.id.qlc:

                break;
            case R.id.fc3d:

                break;
            case R.id.cqssc:

                break;
            case R.id.dlt:

                break;
            case R.id.ssq:

                break;
            case R.id.qxc:

                break;
            case R.id.first:
                break;
            case R.id.second:
                break;
            case R.id.third:
                break;
            case R.id.fourth:
                break;
            case R.id.fifth:
                break;
            case R.id.sixth:
                break;
            case R.id.seventh:
                break;
            case R.id.eighth:
                break;
            case R.id.ninth:
                break;
        }
    }

    public static class BannerItem {

        public int pic;
        public String title;

        public BannerItem() {
        }

        public BannerItem(String title, int pic) {
            this.pic = pic;
            this.title = title;
        }
    }

    public static List<BannerItem> BANNER_ITEMS = new ArrayList<BannerItem>() {{
        add(new BannerItem("", R.mipmap.redian));
        add(new BannerItem("", R.mipmap.ssqkj));
        add(new BannerItem("", R.mipmap.weather));
        add(new BannerItem("", R.mipmap.nba));
    }};

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setImageResource(((BannerItem) path).pic);
        }
    }

    private void initView(final View inflate) {
        final ImageView ssc = inflate.findViewById(R.id.cqssc);
        ssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("NAME", "cqssc");
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("NAME", kaiJiangInfoArrayList.get(position).getKaijiangName());
                startActivity(intent);
            }
        });
        //mLoopRotarySwitchView.setLoopRotationX(progress - seekBar.getMax() / 2);  180
        CarrouselLayout carrousel = (CarrouselLayout) inflate.findViewById(R.id.carrousel);
        carrousel.setRotationX(-15);
        carrousel.setOnCarrouselItemClickListener(new OnCarrouselItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("lee", "" + position);
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getContext(), HistoryActivity.class);
                        intent.putExtra("NAME", "pl3");
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getContext(), HistoryActivity.class);
                        intent.putExtra("NAME", "qlc");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getContext(), HistoryActivity.class);
                        intent.putExtra("NAME", "fc3d");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getContext(), HistoryActivity.class);
                        intent.putExtra("NAME", "cqssc");
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getContext(), HistoryActivity.class);
                        intent.putExtra("NAME", "dlt");
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(getContext(), HistoryActivity.class);
                        intent.putExtra("NAME", "ssq");
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(getContext(), HistoryActivity.class);
                        intent.putExtra("NAME", "qxc");
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(getContext(), HistoryActivity.class);
                        intent.putExtra("NAME", "zcjqc");
                        startActivity(intent);
                        break;
                }
            }
        });
        carrousel.setR(400)//设置R的大小
                .setAutoRotation(true)//是否自动切换
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒


        mPl3 = (ImageView) inflate.findViewById(R.id.pl3);
        mPl3.setOnClickListener(this);
        mQlc = (ImageView) inflate.findViewById(R.id.qlc);
        mQlc.setOnClickListener(this);
        mFc3d = (ImageView) inflate.findViewById(R.id.fc3d);
        mFc3d.setOnClickListener(this);
        mCqssc = (ImageView) inflate.findViewById(R.id.cqssc);
        mCqssc.setOnClickListener(this);
        mDlt = (ImageView) inflate.findViewById(R.id.dlt);
        mDlt.setOnClickListener(this);
        mSsq = (ImageView) inflate.findViewById(R.id.ssq);
        mSsq.setOnClickListener(this);
        mQxc = (ImageView) inflate.findViewById(R.id.qxc);
        mQxc.setOnClickListener(this);

        LinearLayout first = inflate.findViewById(R.id.first);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "sdasd", Toast.LENGTH_SHORT).show();
            }
        });
        rotatables=new ArrayList<>();
        mFirst = (LinearLayout) inflate.findViewById(R.id.first);
        mFirst.setOnClickListener(this);
        Rotatable rotatable1 = new Rotatable.Builder(mFirst)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable1.setTouchEnable(false);
        rotatables.add(rotatable1);
        mSecond = (LinearLayout) inflate.findViewById(R.id.second);
        mSecond.setOnClickListener(this);
        Rotatable rotatable2 = new Rotatable.Builder(mSecond)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable2.setTouchEnable(false);
        rotatables.add(rotatable2);
        mThird = (LinearLayout) inflate.findViewById(R.id.third);
        mThird.setOnClickListener(this);
        Rotatable rotatable3 = new Rotatable.Builder(mThird)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable3.setTouchEnable(false);
        rotatables.add(rotatable3);
        mFourth = (LinearLayout) inflate.findViewById(R.id.fourth);
        mFourth.setOnClickListener(this);
        Rotatable rotatable4 = new Rotatable.Builder(mFourth)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable4.setTouchEnable(false);
        rotatables.add(rotatable4);
        mFifth = (LinearLayout) inflate.findViewById(R.id.fifth);
        mFifth.setOnClickListener(this);
        Rotatable rotatable5 = new Rotatable.Builder(mFifth)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable5.setTouchEnable(false);
        rotatables.add(rotatable5);
        mSixth = (LinearLayout) inflate.findViewById(R.id.sixth);
        mSixth.setOnClickListener(this);
        Rotatable rotatable6 = new Rotatable.Builder(mSixth)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable6.setTouchEnable(false);
        rotatables.add(rotatable6);
        mSeventh = (LinearLayout) inflate.findViewById(R.id.seventh);
        mSeventh.setOnClickListener(this);
        Rotatable rotatable7 = new Rotatable.Builder(mSeventh)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable7.setTouchEnable(false);
        rotatables.add(rotatable7);
        mEighth = (LinearLayout) inflate.findViewById(R.id.eighth);
        mEighth.setOnClickListener(this);
        Rotatable rotatable8 = new Rotatable.Builder(mEighth)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable8.setTouchEnable(false);
        rotatables.add(rotatable8);
        mNinth = (LinearLayout) inflate.findViewById(R.id.ninth);
        mNinth.setOnClickListener(this);
        Rotatable rotatable9 = new Rotatable.Builder(mNinth)
                .direction(Rotatable.ROTATE_BOTH)
                .build();
        rotatable9.setTouchEnable(false);
        rotatables.add(rotatable9);
        handler.sendEmptyMessage(4);
        /*timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int i = new Random().nextInt(8);
                        int j = new Random().nextInt(2);
                        if(j%2==0){
                            rotatables.get(i).rotate(Rotatable.ROTATE_Y,360,3000);
                        }else {
                            rotatables.get(i).rotate(Rotatable.ROTATE_X,360,3000);
                        }
                    }
                });
            }
        },0,1500);*/
    }
    private ArrayList<Rotatable> rotatables;
    Dialog loadingDialog;

    private void showDialog() {
        loadingDialog = DialogUtil.createLoadingDialog(getContext(), "加载中..");
        loadingDialog.show();
        //  loadingDialog = DialogUtil.showWaitDialog(getContext(), "加载中...");
    }

    private ArrayList<KaiJiangInfo> kaiJiangInfoArrayList;
    private RequestQueue mRequestQueue = null;

    private void getLotteryData() {
        if (!CheckUtil.isNetworkAvailable(getContext())) {
            //  Toast.makeText(getContext(), "没有检测到数据连接，请检查设备网络状态！", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(2);
            return;
        }
        kaiJiangInfoArrayList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                mRequestQueue = Volley.newRequestQueue(getContext());
                for (int k = 0; k < urlList.size(); k++) {
                    String name = urlList.get(k);
                    String url = "http://f.apiplus.net/" + name + "-1.json";
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d("lee", s);
                            List<KaiJiangInfo> kaiJiangInfos = ParseJsonUtil.ParseKaijiang(s);
                            if (kaiJiangInfos != null && kaiJiangInfos.size() > 0) {
                                kaiJiangInfoArrayList.add(kaiJiangInfos.get(0));
                            } else {
                                kaiJiangInfoArrayList.add(new KaiJiangInfo("06,10,21,28,29,31+12", "2018-04-15 21:18:20", "2018042", "ssq"));
                            }
                            Log.d("lee", "kaiJiangInfos:" + kaiJiangInfoArrayList.size());
                            if (kaiJiangInfoArrayList.size() == 9) {
                               /* for(KaiJiangInfo info:kaiJiangInfoArrayList){
                                    Log.d("lee",info.toString());
                                }*/
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("lee", volleyError.toString());
                            //   handler.sendEmptyMessage(2);
                            Toast.makeText(getContext(), "数据获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(3);
                        }
                    });
                    mRequestQueue.add(request);
                }

            }
        }.start();

    }

    private ArrayList<String> urlList;

    private void initCaipiao() {
        urlList = new ArrayList<>();
        urlList.add("cqssc");
        urlList.add("qxc");
        urlList.add("zcjqc");
        urlList.add("dlt");
        urlList.add("fc3d");
        urlList.add("pl3");
        urlList.add("pl5");
        urlList.add("qlc");
        urlList.add("ssq");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void OnBannerClick(int position) {
        int k = 0;
        k = position + 1;
        Log.d("lee", position + "");

        if (k == 1) {
            Intent intent = new Intent(getContext(), NewsEveryDay.class);
            //url 要跳转的URL
            //  intent.putExtra("url","http://finance.sina.cn/consume/bgt/2018-05-10/detail-ihaichqz5844325.d.html?cre=tianyi&mod=wfin&loc=10&r=25&doct=0&rfunc=100&tj=none&tr=25");
            startActivity(intent);
        } else if (k == 2) {
            Intent intent = new Intent(getContext(), SsqActivity.class);
            //url 要跳转的URL
            //  intent.putExtra("url","http://finance.sina.cn/consume/bgt/2018-05-10/detail-ihaichqz5844325.d.html?cre=tianyi&mod=wfin&loc=10&r=25&doct=0&rfunc=100&tj=none&tr=25");
            startActivity(intent);
        } else if (k == 3) {
            Intent intent = new Intent(getContext(), WeatherActivity.class);
            //url 要跳转的URL
            //  intent.putExtra("url","http://finance.sina.cn/consume/bgt/2018-05-10/detail-ihaichqz5844325.d.html?cre=tianyi&mod=wfin&loc=10&r=25&doct=0&rfunc=100&tj=none&tr=25");
            startActivity(intent);
        } else if (k == 4) {
            Uri uri = Uri.parse("http://nba.stats.qq.com/schedule/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                /*Intent intent=new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",playOff);*/
            startActivity(intent);
            startActivity(intent);
        }
        // Toast.makeText(getContext(),"你点击了："+k,Toast.LENGTH_SHORT).show();

    }
}
