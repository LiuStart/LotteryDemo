package com.cn.cqsscfx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cn.cqsscfx.R;
import com.cn.cqsscfx.bean.KaiJiangInfo;

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
        myHoder.kaijiangCode.setText(kaiJiangInfo.getKaijiangCode());
        return convertView;
    }
    class  MyHoder  {
        TextView kaijiangCode;
        TextView kaijiangDate;
    }
}
