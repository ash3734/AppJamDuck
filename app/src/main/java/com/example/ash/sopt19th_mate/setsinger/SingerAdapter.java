package com.example.ash.sopt19th_mate.setsinger;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.dialog.CustomDialogActivity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by dldud on 2016-11-10.
 */

public class SingerAdapter extends RecyclerView.Adapter<ViewHolderActivity>
{
    ArrayList<ItemData> itemDatas;
    ArrayList<ItemData> arSrc;
    ArrayList<ItemData> totalDatas;
    ImageView.OnClickListener clickEvent;
    RequestManager requestManager;
    //LinearLayoutManager linearLayoutManager;

    String temp_name;
    String temp_singer;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CustomDialogActivity customDialogActivity;
    Context context;
    SetSingerActivity setSingerActivity;

    TextView btn_left;
    TextView btn_right;


    boolean search = false;

    public SingerAdapter(ArrayList<ItemData> itemDatas, ArrayList<ItemData> itemdata2, RecyclerView recyclerView, RequestManager requestManager,
                         LinearLayoutManager linearLayoutManager, Context context, SetSingerActivity setSingerActivity)
    {
        this.itemDatas = itemDatas;
        // this.clickEvent = clickEvent;
        ///여기는 검색

        //이 밑은 추천목록
        this.arSrc = itemdata2;
        this.totalDatas = new ArrayList<ItemData>();
        this.totalDatas.addAll(this.arSrc);

        this.requestManager = requestManager;
        this.context = context;
        this.recyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.linearLayoutManager = linearLayoutManager;
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.setSingerActivity = setSingerActivity;



    }

    public void setAdaper(ArrayList<ItemData> itemDatas){
        arSrc = itemDatas;
        this.totalDatas = new ArrayList<ItemData>();
        this.totalDatas.addAll(arSrc);

        notifyDataSetChanged();
    }

    @Override
    public ViewHolderActivity onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_holder, parent,false);
        //화면상에 보여줄 레이아웃을 지정해주어야 함.
        ViewHolderActivity viewHolder = new ViewHolderActivity(itemView);
        //itemView.setOnClickListener(clickEvent);

        return viewHolder;
        //어댑터를 실행했을 때 뷰 홀더를 기억하게끔.
    }

    @Override
    public void onBindViewHolder(ViewHolderActivity holder, final int position) {
        // holder.textView.setText(itemDatas.get(position));
        //holder.contentView.setText(itemDatas.get(position).content);
        //뷰홀더를 가져왔기 때문에 뷰홀더에 있는 xml에 접근할 수 있음.
        if(search == false) {
            //holder.singer.setImageResource(itemDatas.get(position).singer);

            requestManager.load(itemDatas.get(position).getImageURL()).into(holder.singer);
            holder.name.setText(itemDatas.get(position).name);
            holder.most.setImageResource(itemDatas.get(position).most);
            holder.most.setOnClickListener(new View.OnClickListener() {
                @Override//검색 안 하고 있지만 클릭했을 때
                public void onClick(View v) {
                    Log.i("myTag",String.valueOf(position));
                    temp_singer = itemDatas.get(position).imageURL;
                    temp_name = itemDatas.get(position).name;
                    Log.v("name", temp_name);

                    itemDatas.remove(position);
                    itemDatas.add(position, new ItemData(temp_singer, temp_name, R.drawable.heart_pink));
                    recyclerView.setAdapter(SingerAdapter.this);
                    String dialogtitle = temp_name + "를 최애 가수로\n설정하시겠습니까?";
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
                        setSingerActivity.SetComplete(ApplicationController.getInstance().getNumberSingerSet().get(temp_name));

                    }
                };

                private View.OnClickListener rightListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        itemDatas.remove(position);
                        itemDatas.add(position, new ItemData(temp_singer, temp_name, R.drawable.popup_heart));

                        // SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent, dataSet2);
                        recyclerView.setAdapter(SingerAdapter.this);
                        customDialogActivity.dismiss();
                        // mCustomDialog.
                        //setSingerActivity.SetComplete(ApplicationController.getInstance().getNumberSingerSet().get(temp_name));
                    }
                };
            });
        }
        else {
            requestManager.load(arSrc.get(position).getImageURL()).into(holder.singer);
            holder.name.setText(arSrc.get(position).name);
            holder.most.setImageResource(arSrc.get(position).most);
            holder.most.setOnClickListener(new View.OnClickListener() {
                @Override//검색 안 하고 있지만 클릭했을 때
                public void onClick(View v) {
                    Log.i("myTag",String.valueOf(position));

                    temp_singer = arSrc.get(position).imageURL;
                    temp_name = arSrc.get(position).name;
                    Log.v("name", temp_name);

                    arSrc.remove(position);
                    arSrc.add(position, new ItemData(temp_singer, temp_name, R.drawable.heart_pink));
                    recyclerView.setAdapter(SingerAdapter.this);

                    String dialogtitle = temp_name + "를 최애 가수로\n설정하시겠습니까?";
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
                        setSingerActivity.SetComplete(ApplicationController.getInstance().getNumberSingerSet().get(temp_name));

                    }
                };

                private View.OnClickListener rightListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        arSrc.remove(position);
                        arSrc.add(position, new ItemData(temp_singer, temp_name, R.drawable.popup_heart));

                        // SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent, dataSet2);
                        recyclerView.setAdapter(SingerAdapter.this);
                        customDialogActivity.dismiss();
                        // mCustomDialog.
                    }
                };
            });
        }

        //holder.button.setText(itemDatas.get(position).button);

    }//실질적으로 리스트 항목을 뿌려주는 역할을 함.

    public int getItemCount() {
        if(search == false)
            return (itemDatas != null) ? itemDatas.size() : 0;
        else
            return (arSrc != null) ? arSrc.size() : 0;
    }

    public void filter(String charText) {
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
                    arSrc.add(itemDatas.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
