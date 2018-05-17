package com.cn.cqsscfx.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.cqsscfx.R;
import com.cn.cqsscfx.bean.KaiJiangInfo;

import java.util.List;


/**
 * Created by Administrator on 2018/1/25.
 */

public class KaijiangAdapter extends BaseAdapter{
    private List<KaiJiangInfo> kaijiangList;
    private Context mContext;
    SharedPreferences caipiaoName;
    SharedPreferences caipiaoIcon;
    public KaijiangAdapter(Context ctx, List<KaiJiangInfo>list){
        mContext=ctx;
        Log.d("lee","开奖list:"+list.size());
        kaijiangList=list;
        caipiaoName = mContext.getSharedPreferences("CAIPIAONAME", Context.MODE_PRIVATE);
        caipiaoIcon = mContext.getSharedPreferences("CAIPIAOICON", Context.MODE_PRIVATE);
    }
    @Override
    public int getCount() {
        return kaijiangList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHoder myHoder;
        if(convertView==null){
            myHoder=new MyHoder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lottery_info, null);
            //图片
            myHoder.kaijiangima=(ImageView)convertView.findViewById(R.id.caipiaoimage);
            //期号
            myHoder.kaijiangNum=convertView.findViewById(R.id.tv_odd_number);
            //号码
            myHoder.kaijiangCode=convertView.findViewById(R.id.tv_lottery_number);
            //时间
            myHoder.kaijiangDate=convertView.findViewById(R.id.tv_time);
            //名字
            myHoder.kaijiangName=convertView.findViewById(R.id.tv_name);
            //开奖组
            myHoder.group=convertView.findViewById(R.id.group);
           /* myHoder.kaijiangNum=convertView.findViewById(R.id.kaijiangdate);
            myHoder.kaijiangCode=convertView.findViewById(R.id.kaijiangnum);*/
           // myHoder.kaijiangDate=convertView.findViewById(R.id.kaijiangdate);
            convertView.setTag(myHoder);
        }else{
            myHoder=(MyHoder)convertView.getTag();
        }
        KaiJiangInfo kaiJiangInfo = kaijiangList.get(position);
        Log.d("lee","kaiJiangInfo.getKaijiangName()"+kaiJiangInfo.getKaijiangName());
        if(myHoder.kaijiangima==null){
            Log.d("lee","fuck");
        }else{
            myHoder.kaijiangima.setBackgroundResource(caipiaoIcon.getInt(kaiJiangInfo.getKaijiangName(),R.drawable.ic_launcher));
        }
        myHoder.kaijiangDate.setText("开奖日期："+kaiJiangInfo.getKaijiangDate());
        myHoder.kaijiangNum.setText("开奖期号："+kaiJiangInfo.getKaijiangNum());
       // myHoder.kaijiangCode.setText("开奖号码："+kaiJiangInfo.getKaijiangCode());
        myHoder.kaijiangName.setText(caipiaoName.getString(kaiJiangInfo.getKaijiangName(),"  "));
        /*list=new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("13");
        list.add("4");
        list.add("8");
        list.add("0");
        list.add("33");*/
        String kaijiangCode = kaiJiangInfo.getKaijiangCode();
        String replace="";
        if(kaijiangCode.contains("+")){
            replace= kaijiangCode.replace("+", "\t\t");
        }else{
            replace=kaijiangCode;
        }
        String[] split = replace.split("\t\t");
        Log.d("lee","split:"+split.length);
        final float scale = mContext.getResources().getDisplayMetrics().density;
        myHoder.group.removeAllViews();
        for (String num:split){
            Log.d("lee","num:::"+num);
            TextView textview = (TextView)LayoutInflater.from(mContext).inflate(R.layout.item_textview, null);
            textview.setText(num);
            textview.setHeight((int)(25 * scale + 0.5f));
            textview.setWidth((int)(25 * scale + 0.5f));
            myHoder.group.addView(textview);
        }
        return convertView;
    }

    class  MyHoder  {
        ImageView kaijiangima;
        TextView kaijiangNum;
        TextView kaijiangCode;
        TextView kaijiangDate;
        TextView kaijiangName;
        LinearLayout group;
    }
}
