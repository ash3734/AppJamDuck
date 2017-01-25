package com.example.ash.sopt19th_mate.mypage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;

import java.util.ArrayList;

/**
 * Created by hyeon on 2016-12-30.
 */

public class MyAdapter extends RecyclerView.Adapter<MypageViewHolder> {
    ArrayList<MypageSSingerData> itemDatas;
    View.OnClickListener clickEvent;
    RequestManager requestManager;
    RequestManager requestManager2;
    MyMainActivity myMainActivity;


    public MyAdapter(ArrayList<MypageSSingerData> itemDatas, RequestManager requestManager, RequestManager requestManager2, MyMainActivity myMainActivity) {
        Log.v("초기화 들어옴", "초기화 들어옴");
        this.itemDatas = itemDatas;
        this.requestManager = requestManager;
        this.requestManager2 = requestManager2;
        this.myMainActivity = myMainActivity;
        notifyDataSetChanged();
    }

    @Override
    public MypageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("크리에이트 들어옴", "크리에이트 들어옴");
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_singer_item,parent,false);
        MypageViewHolder mypageViewHolder=new MypageViewHolder(itemView);
        //mypageViewHolder.nextBulView.setOnClickListener(clickEvent);
        return mypageViewHolder;
    }

    @Override
    public void onBindViewHolder(MypageViewHolder holder, final int position) {
        //holder.textView.setText(itemDatas.get(position).getName());
        Log.v("홀덩", "홀더");
        //notifyDataSetChanged();
        //이거 서브들.
        requestManager.load(itemDatas.get(position).getMain_img()).into(holder.imageView2);
        holder.textView.setText(itemDatas.get(position).name);
        requestManager2.load(itemDatas.get(position).getBg_img1()).into(holder.imageView);
        holder.textView2.setText("게시판");
        holder.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationController.getInstance().setBoardS_id(ApplicationController.getInstance().getNumberSingerSet().get(itemDatas.get(position).name));
                myMainActivity.ToBoard();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}
