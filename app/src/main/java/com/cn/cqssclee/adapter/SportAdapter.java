package com.cn.cqssclee.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.cqssclee.R;
import com.cn.cqssclee.bean.SportBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/26.
 */

public class SportAdapter extends BaseAdapter{
    private ArrayList<SportBean> sportList;
    private Context mContext;
    private SharedPreferences sp;
    public SportAdapter(Context context,ArrayList<SportBean> list){
        this.mContext=context;
        this.sportList=list;
        sp=mContext.getSharedPreferences("SPORT",Context.MODE_PRIVATE);
    }
    @Override
    public int getCount() {
        return sportList.size();
    }

    @Override
    public Object getItem(int position) {
        return sportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(null==convertView){
            holder=new Holder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.sport_item,null);
            holder.team1=convertView.findViewById(R.id.team1);
            holder.team1Name=convertView.findViewById(R.id.team1_name);
            holder.team1Socre=convertView.findViewById(R.id.team1_score);
            holder.team2=convertView.findViewById(R.id.team2);
            holder.team2Name=convertView.findViewById(R.id.team2_name);
            holder.team2Score=convertView.findViewById(R.id.team2_score);
            holder.sportTime=convertView.findViewById(R.id.sport_time);
            holder.videoType=convertView.findViewById(R.id.video_type);
            holder.playType=convertView.findViewById(R.id.play_type);
            convertView.setTag(holder);
        }else {
            holder=(Holder)convertView.getTag();
        }
        SportBean sportBean = sportList.get(position);
        holder.team1.setBackgroundResource(sp.getInt(sportBean.getTeam1Name(),R.drawable.ball));
        holder.team1Name.setText(sportBean.getTeam1Name());
        holder.team1Socre.setText(sportBean.getTeam1Score());
        holder.team2.setBackgroundResource(sp.getInt(sportBean.getTeam2Name(),R.drawable.ball));
        holder.team2Name.setText(sportBean.getTeam2Name());
        holder.team2Score.setText(sportBean.getTeam2Score());
        holder.sportTime.setText(sportBean.getSportTime());
        if(sportBean.getStatus().equals("1")){
            holder.playType.setBackgroundResource(R.drawable.playnba);
            holder.team1Socre.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.team2Score.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.videoType.setTextColor(mContext.getResources().getColor(R.color.red));
        }else{
            holder.playType.setBackgroundResource(R.drawable.play);
        }
        holder.videoType.setText(sportBean.getVideoType());
        return convertView;
    }

    class Holder{
        TextView team1Name;
        TextView team1Socre;
        TextView team2Name;
        TextView team2Score;
        TextView videoType;
        TextView sportTime;
        ImageView team1;
        ImageView team2;
        ImageView playType;
    }
}
