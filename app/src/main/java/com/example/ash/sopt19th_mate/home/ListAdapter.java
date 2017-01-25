package com.example.ash.sopt19th_mate.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ash.sopt19th_mate.R;

import java.util.ArrayList;

/**
 * Created by dldud on 2017-01-04.
 */

public class ListAdapter extends RecyclerView.Adapter<ListViewHolderActivity>{

    ArrayList<ListData> itemDatas;
    View.OnClickListener clickEvent;
    LinearLayoutManager linearLayoutManager;

    public ListAdapter(ArrayList<ListData> itemDatas, View.OnClickListener clickEvent, LinearLayoutManager linearLayoutManager) {
        Log.v("어댑터 들어옴", "어댑터 들어옴");
        this.itemDatas = itemDatas;
        this.clickEvent = clickEvent;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public ListViewHolderActivity onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("create 들어옴1", "create 들어옴");

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_view_holder, parent,false);
        //화면상에 보여줄 레이아웃을 지정해주어야 함.
        ListViewHolderActivity viewHolder = new ListViewHolderActivity(itemView);
        //itemView.setOnClickListener(clickEvent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolderActivity holder, int position) {
        Log.v("create 들어옴2", "create 들어옴2");
        if(position == 0) {
            holder.singerText.setText(itemDatas.get(position).singer);
            Log.v(holder.singerText.getText().toString(), String.valueOf(position));
            holder.mainImage.setImageResource(itemDatas.get(position).mainImg);
        }
        else
            holder.singerText.setText(itemDatas.get(position).singer);
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}
