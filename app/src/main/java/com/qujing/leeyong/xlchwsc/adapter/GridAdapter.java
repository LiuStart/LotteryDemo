package com.qujing.leeyong.xlchwsc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qujing.leeyong.xlchwsc.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GridAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<String> list;
    public GridAdapter(Context context, ArrayList<String> list){
        this.mContext=context;
        this.list=list;
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
        MyHolder holder;
        if (convertView==null){
            holder=new MyHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_xuanhao,null);
            holder.textView=convertView.findViewById(R.id.tv_lottery_number);
            holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_xuanhao));
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.black));
            convertView.setTag(holder);
        } else {
            holder= (MyHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }

    class MyHolder{
        TextView textView;
    }
}
