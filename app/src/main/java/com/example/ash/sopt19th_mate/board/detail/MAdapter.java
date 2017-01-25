package com.example.ash.sopt19th_mate.board.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ash.sopt19th_mate.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ash on 2017-01-03.
 */

public class MAdapter extends RecyclerView.Adapter<ComentViewHolder>{
    ArrayList<ComentListData> datas;


    public MAdapter(ArrayList<ComentListData> datas) {
        this.datas = datas;
    }
    public void setAdapter(ArrayList<ComentListData> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }
    @Override
    public ComentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coment_list_item, parent,false);
        ComentViewHolder viewHolder = new ComentViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ComentViewHolder holder, int position) {
        String str = datas.get(position).getC_content();
        holder.textViewContent.setText(datas.get(position).getC_content());
        holder.textViewNickName.setText(datas.get(position).getNickname());
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("dd:HH:mm");
        String formatDate = sdfNow.format(date);
        String commentTime = datas.get(position).getTime();
        holder.textViewtime.setText(processingData(formatDate,commentTime));
    }

    @Override
    public int getItemCount() {
        return (datas != null) ? datas.size() : 0;
    }
    String processingData(String commentTime,String currentTime){
        int total = 0;
        int curtotal = 0;
        int []curTime = new int[3];
        String []curTimeString = currentTime.split(":");
        for(int i=0;i<3;i++) {
            curTime[i] = Integer.parseInt(curTimeString[i]);
        }

        int []timeary = new int[3];;
        String []commentTimeString = commentTime.split(":");
        for(int i=0;i<3;i++) {
            timeary[i] = Integer.parseInt(commentTimeString[i]);
        }
        curtotal = (curTime[0])*1440 + (curTime[1])*60 + (curTime[2]);
        total = (timeary[0])*1440 + (timeary[1])*60 + (timeary[2]);
        if(curtotal - total < 1440){
            // 하루안

            int t1 = ( curtotal - total );
            // 분임
            if(t1 / 60 == 0){
                return String.valueOf(t1)+"분전";
            }else{
                return  String.valueOf(t1/60)+"시간전";
            }

        }else{
            int t = (curtotal - total) / 1440;
            return String.valueOf(t)+"일전";
        }
    }
}
