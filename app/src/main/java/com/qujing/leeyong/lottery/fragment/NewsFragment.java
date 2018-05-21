package com.qujing.leeyong.lottery.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qujing.leeyong.lottery.R;
import com.qujing.leeyong.lottery.ToastActivity;
import com.stonesun.newssdk.NewsAgent;
import com.stonesun.newssdk.fragment.NewsAFragment;
import com.stonesun.newssdk.sharesdk.NewsSDKShare;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment{


    public NewsFragment() {
        // Required empty public constructor
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            Toast.makeText(getContext(),"数据加载完成",Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_news, container, false);
        // Inflate the layout for this fragment
        return inflate;
    }

    @Override
    public void onResume() {
        Log.d("lee","onResume");
        super.onResume();
    }
    private boolean isViewVisible;//view是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        Log.d("lee","新闻界面是否可见："+isViewVisible);
        if(isViewVisible){
            getNewsData();
        }
    }
    FragmentManager fm;
    FragmentTransaction transaction;
    NewsAFragment recomFragment;
    private boolean isLoadView=false;//view是否可见
    private void getNewsData(){
        if (!isLoadView){
            isLoadView=true;
                    Log.d("lee","加载新闻");
                    NewsAgent.init(getContext());
                    NewsAgent.createShareCommentViewActivity("详情页");
                    NewsAgent.setLoading(false, true, true, true);
                    NewsAgent.SetUserImpForSDK(null,ToastActivity.class);
                    NewsAgent.setShowShare(new NewsSDKShare(),"详情页");
                    NewsAgent.createDefaultRecomFragment("导航栏", "SPOTS-414592", "详情页");
                    fm = getActivity().getSupportFragmentManager();
                    transaction = fm.beginTransaction();
                    recomFragment = NewsAgent.getDefaultRecomFragment("导航栏");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            transaction.add(R.id.fl_main, recomFragment).commit();
                        }
                    });

        } else {
            Log.d("lee","已经加载过");
        }
    }

    @Override
    public void onDestroyView() {
        Log.d("lee","onDestroyView");
        isLoadView=false;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("lee","onDestroy");
        isLoadView=false;
        super.onDestroy();
    }
}
