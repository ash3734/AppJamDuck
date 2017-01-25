package com.example.ash.sopt19th_mate.mypage;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.dialog.CustomDialogActivity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by dldud on 2016-12-30.
 */

public class MyPageAdapter extends RecyclerView.Adapter<MyPageViewHolderActivity>{

    ArrayList<MyPageItemData> itemDatas;
    ArrayList<MyPageItemData> arSrc;
    ArrayList<MyPageItemData> totalDatas;
    ImageView.OnClickListener clickEvent;
    RequestManager requestManager;


    String temp_name;
    String temp_singer;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CustomDialogActivity customDialogActivity;
    Context context;
    MypageActivity mypageActivity;
    //MypageActivity mypageActivity;
    boolean search = false;
    String MainSinger=ApplicationController.getInstance().getName(); //Application에서 메인 가수 받아오기
    String TempSinger;
    public MyPageAdapter(ArrayList<MyPageItemData> itemdata, RecyclerView recyclerView, RequestManager requestManager, LinearLayoutManager linearLayoutManager,
                         Context context, MypageActivity mypageActivity, ArrayList<MyPageItemData> itemdata2)
    {

        this.itemDatas = itemdata;
        ///여기는 검색
        this.arSrc = itemdata2;
        this.totalDatas = new ArrayList<MyPageItemData>();
        this.totalDatas.addAll(this.arSrc);

        this.requestManager = requestManager;
        this.context = context;
        this.recyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.linearLayoutManager = linearLayoutManager;
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.mypageActivity = mypageActivity;
    }


    public void setAdaper(ArrayList<MyPageItemData> itemDatas){
        arSrc = itemDatas;
        this.totalDatas = new ArrayList<MyPageItemData>();
        this.totalDatas.addAll(arSrc);
        notifyDataSetChanged();
    }


    @Override
    public MyPageViewHolderActivity onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_page_view_holder, parent,false);
        //화면상에 보여줄 레이아웃을 지정해주어야 함.
        MyPageViewHolderActivity viewHolder = new MyPageViewHolderActivity(itemView);
        //itemView.setOnClickListener(clickEvent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyPageViewHolderActivity holder, final int position) {
            if(search) {//검색 중 아닐 때
                Log.v("SearchTrue", "SearchTrue");
                holder.itemView.setVisibility(View.VISIBLE);
                requestManager.load(arSrc.get(position).getImageURL()).into(holder.singer);
                holder.name.setText(arSrc.get(position).name);
                holder.most.setImageResource(arSrc.get(position).most);
                holder.add.setImageResource(arSrc.get(position).add);
                holder.most.setOnClickListener(new View.OnClickListener() {
                    @Override//검색 안 하고 있지만 클릭했을 때
                    public void onClick(View v) {
                        Log.i("myTag", String.valueOf(position));
                        temp_singer = arSrc.get(position).imageURL;
                        temp_name = arSrc.get(position).name;
                        TempSinger=arSrc.get(position).name;
                        Log.v("name", temp_name);

                        arSrc.remove(position);
                        arSrc.add(position, new MyPageItemData(temp_singer, temp_name, R.drawable.gasuplus__heart, R.drawable.gasuplus_plus));
                        arSrc.get(position).setAdd(0);
                        arSrc.get(position).setMost(0);
                        recyclerView.setAdapter(MyPageAdapter.this);
                        String dialogtitle = temp_name + "를 최애 가수로\n변경하시겠습니까?";
                        String dialogcontext = "앱을 켤 때 가장 먼저 최대 가수의\n투표 목록이 보이게 됩니다.";

                        customDialogActivity = new CustomDialogActivity(context,
                                dialogtitle, // 제목
                                dialogcontext, // 내용
                                leftListener, // 왼쪽 버튼 이벤트
                                rightListener); // 오른쪽 버튼 이벤트
                        customDialogActivity.show();
                    }

                    private View.OnClickListener leftListener = new View.OnClickListener() {
                        public void onClick(View v) {
                            //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            customDialogActivity.dismiss();
                            ApplicationController.getInstance().setName(temp_name);
                            mypageActivity.ChangeComplete(ApplicationController.getInstance().getNumberSingerSet().get(temp_name));

                        }
                    };

                    private View.OnClickListener rightListener = new View.OnClickListener() {
                        public void onClick(View v) {
                            arSrc.remove(position);
                            arSrc.add(position, new MyPageItemData(temp_singer, temp_name, R.drawable.gasuplus__heart, R.drawable.gasuplus_plus));
                            arSrc.get(position).setAdd(R.drawable.gasuplus_plus);
                            arSrc.get(position).setMost(R.drawable.gasuplus__heart);
                            // SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent, dataSet2);
                            recyclerView.setAdapter(MyPageAdapter.this);
                            customDialogActivity.dismiss();
                            // mCustomDialog.
                        }
                    };
                });
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp_name = arSrc.get(position).name;
                        Log.v("서브" , String.valueOf(ApplicationController.getInstance().getNumberSingerSet().get(temp_name)));
                        mypageActivity.AddSubComplete(ApplicationController.getInstance().getNumberSingerSet().get(temp_name));
                    }
                });
            }
        else {//검색중일때//
                //requestManager.load(arSrc.get(position).getImageURL()).into(holder.singer);
//                holder.add.setVisibility(View.INVISIBLE);
//                holder.most.setVisibility(View.INVISIBLE);
//                holder.name.setVisibility(View.INVISIBLE);
                holder.itemView.setVisibility(View.INVISIBLE);

            }
    }

    @Override
    public int getItemCount() {
        return (arSrc != null) ? arSrc.size() : 0;
    }

    public void filter(String charText) {
        int j=0;
        charText = charText.toLowerCase(Locale.getDefault());

        //먼저 arSrc객체를 비워줍니다.
        arSrc.clear();

        //입력한 데이터가 없을 경우에는 모든 데이터항목을 출력해줍니다.
        if (charText.length() == 0) {
            arSrc.addAll(itemDatas);
        }
        //입력한 데이터가 있을 경우에는 일치하는 항목들만 찾아 출력해줍니다.
        else
        {
            for (int i = 0; i < itemDatas.size() ; i++)
            {
                String wp = itemDatas.get(i).getName();

                if (wp.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    arSrc.add(j,itemDatas.get(i));
                    if(wp.compareTo(MainSinger)==0){
                        arSrc.get(j).setMost(0);
                        arSrc.get(j).setAdd(0);
                    }
                    else
                    {
                        arSrc.get(j).setMost(R.drawable.gasuplus__heart);
                        arSrc.get(j).setAdd(R.drawable.gasuplus_plus);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
