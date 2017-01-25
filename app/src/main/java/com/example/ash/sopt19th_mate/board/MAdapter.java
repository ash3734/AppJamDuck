package com.example.ash.sopt19th_mate.board;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.network.NetworkService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ash on 2017-01-03.
 */

public class MAdapter  extends RecyclerView.Adapter<MViewHolder>{
    ArrayList<BoardListData> datas;
    View.OnClickListener clickEvent;
    RequestManager requestManager;
    NetworkService service;


    public MAdapter(ArrayList<BoardListData> datas, View.OnClickListener clickEvent, RequestManager requestManager) {
        this.datas = datas;
        this.clickEvent = clickEvent;
        this.requestManager = requestManager;
        service = ApplicationController.getInstance().getNetworkService();
    }
    public void setMyAdapter(ArrayList<BoardListData> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.boad_list_item, parent,false);
        MViewHolder viewHolder = new MViewHolder(itemView);
        itemView.setOnClickListener(clickEvent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        final int pos = position;
        requestManager.load(datas.get(position).profile_img).into(holder.imageViewMyImage);
        holder.textViewContent.setText(datas.get(position).b_content);
        holder.textViewComentCount.setText(String.valueOf(datas.get(position).c_count));
        holder.textViewLikeCount.setText(String.valueOf(datas.get(position).like_count));
        holder.textViewNickName.setText(datas.get(position).nickname);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("dd:HH:mm");
        String formatDate = sdfNow.format(date);
        holder.textViewTime.setText(processingData(datas.get(position).b_time,formatDate));
        holder.textViewDisLikeCount.setText(String.valueOf(datas.get(position).dislike_count));
        int i=datas.get(position).m_id;
        Log.d("??",String.valueOf(i));
        final int  m_id = ApplicationController.getInstance().getM_id();
        if(i==m_id){ //자기 닉네임으로 세팅-------------------------------------------------------------
            holder.textViewDelete.setText("삭제");
            holder.textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alt_bld = new AlertDialog.Builder(v.getContext());
                    alt_bld.setMessage("정말 삭제하시겠습니까??").setCancelable(
                            false).setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Call<DeleteBoardResult> getDeleteBoard = service.requestDeleteBoard(datas.get(pos).b_id);
                                    getDeleteBoard.enqueue(new Callback<DeleteBoardResult>() {
                                        @Override
                                        public void onResponse(Call<DeleteBoardResult> call, Response<DeleteBoardResult> response) {
                                            datas.remove(pos);
                                            notifyDataSetChanged();
                                        }
                                        @Override
                                        public void onFailure(Call<DeleteBoardResult> call, Throwable t) {

                                        }
                                    });
                                }
                            }).setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = alt_bld.create();
                    // Title for AlertDialog
                    // Icon for AlertDialog
                    alert.show();
                }
            });
        }
        if(TextUtils.isEmpty(datas.get(position).is_like)){
            holder.imageViewBad.setImageResource(R.drawable.bad);
            holder.imageViewGood.setImageResource(R.drawable.good);
        }
        else if(datas.get(position).is_like.equals("t")){
            holder.imageViewBad.setImageResource(R.drawable.bad);
            holder.imageViewGood.setImageResource(R.drawable.good_click);
        }
        else{
            holder.imageViewBad.setImageResource(R.drawable.bad_click);
            holder.imageViewGood.setImageResource(R.drawable.good);
        }
        //좋아요 눌렀을때
        holder.imageViewGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버 업데이트
                Call<LikeResult> requestLike = service.requestLike(
                        new LikeData(datas.get(pos).b_id,m_id,"t"));
                requestLike.enqueue(new Callback<LikeResult>() {
                    @Override
                    public void onResponse(Call<LikeResult> call, Response<LikeResult> response) {


                        //아무것도 아닌 경우
                        if(TextUtils.isEmpty(datas.get(pos).is_like)){
                            //좋아요 상태로 바꾸고
                            Log.d("siballll","???");
                            datas.get(pos).is_like = "t";
                            datas.get(pos).like_count++;
                        }
                        //싫어요 눌러져 잇을 경우
                        else if(datas.get(pos).is_like.equals("f")){

                        }
                        //좋아요가 눌러져 있을 경우
                        else{
                            //아무것도 아닌상태로 하고 하나깍는다.
                            datas.get(pos).is_like = null;
                            datas.get(pos).like_count--;
                        }
                        notifyDataSetChanged();;
                    }

                    @Override
                    public void onFailure(Call<LikeResult> call, Throwable t) {

                    }
                });
            }
        });
        //싫어요 눌렀을때
        holder.imageViewBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버 업데이트
                Call<LikeResult> requestLike = service.requestLike(
                        new LikeData(datas.get(pos).b_id,m_id,"f"));
                requestLike.enqueue(new Callback<LikeResult>() {
                    @Override
                    public void onResponse(Call<LikeResult> call, Response<LikeResult> response) {
                        if(TextUtils.isEmpty(datas.get(pos).is_like)){
                            //싫어요 상태로 바꾸고

                            datas.get(pos).is_like = "f";
                            datas.get(pos).dislike_count++;
                        }

                        //싫어요 눌러져 잇을 경우
                        else if(datas.get(pos).is_like.equals("f")){
                            datas.get(pos).is_like=null;
                            datas.get(pos).dislike_count--;
                        }
                        //아무것도 아닌 경우
                        else{

                        }

                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<LikeResult> call, Throwable t) {

                    }
                });
            }
        });

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
