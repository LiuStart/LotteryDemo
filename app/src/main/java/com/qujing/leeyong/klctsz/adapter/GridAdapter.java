package com.qujing.leeyong.klctsz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qujing.leeyong.klctsz.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> list;
    private ArrayList<String> contentList;
    private int type;

    public GridAdapter(Context context, ArrayList<String> list, ArrayList<String> cList, int type) {
        this.mContext = context;
        this.list = list;
        this.contentList = cList;
        this.type = type;
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
        //  MyLogger.Log("getView::" + contentList.size());
        // MyLogger.Log("position::" + position+"");
        MyHolder holder;
        if (convertView == null) {
            holder = new MyHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_xuanhao, null);
            holder.textView = convertView.findViewById(R.id.tv_lottery_number);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        if (type == 0) {
            if (contentList != null && contentList.contains(position + "")) {
                //  MyLogger.Log("contentList::" + position);

                holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.shapetextview));
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.textView.setTag(1);
            } else {
                holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_xuanhao));
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.textView.setTag(0);
            }
        } else {
            if (contentList != null && contentList.contains(position + "")) {
                //  MyLogger.Log("contentList::" + position);

                holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.shapetextview_h));
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.textView.setTag(1);
            } else {
                holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_xuanhao_h));
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.textView.setTag(0);
            }
        }

        holder.textView.setText(list.get(position));
        return convertView;
    }

    class MyHolder {
        TextView textView;
    }
}
