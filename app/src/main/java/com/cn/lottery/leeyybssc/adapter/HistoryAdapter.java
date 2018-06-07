package com.cn.lottery.leeyybssc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.lottery.leeyybssc.R;
import com.cn.lottery.leeyybssc.bean.KaiJiangInfo;
import com.cn.lottery.leeyybssc.util.MyLogger;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class HistoryAdapter extends BaseAdapter{
    private List<KaiJiangInfo> list;
    private Context mContext;
    public HistoryAdapter(List list, Context context){
        this.list=list;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHoder myHoder;
        final float scale = mContext.getResources().getDisplayMetrics().density;
        if(convertView==null){
            myHoder=new MyHoder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.kaijiang_item,null);
            myHoder.kaijiangDate=convertView.findViewById(R.id.date);
            myHoder.kaijiangCode=convertView.findViewById(R.id.code);
            convertView.setTag(myHoder);
        }else {
            myHoder= (MyHoder) convertView.getTag();
        }
        KaiJiangInfo kaiJiangInfo = list.get(position);
        myHoder.kaijiangDate.setText("第："+kaiJiangInfo.getKaijiangNum()+"期\t\t"+kaiJiangInfo.getKaijiangDate());
        MyLogger.Log(kaiJiangInfo.getKaijiangCode());
        String replace = kaiJiangInfo.getKaijiangCode().replace("+", ",");
        String[] split = replace.split(",");
        myHoder.kaijiangCode.removeAllViews();
        for (String num: split) {
            TextView textview = (TextView)LayoutInflater.from(mContext).inflate(R.layout.item_textview, null);
            textview.setText(num);
            textview.setHeight((int)(25 * scale + 0.5f));
            textview.setWidth((int)(25 * scale + 0.5f));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 10, 0);
            textview.setLayoutParams(layoutParams);
            myHoder.kaijiangCode.addView(textview);
        }

        return convertView;
    }
    class  MyHoder  {
        LinearLayout kaijiangCode;
        TextView kaijiangDate;
    }
}
