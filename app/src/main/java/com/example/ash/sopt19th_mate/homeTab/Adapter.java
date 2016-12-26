package com.example.ash.sopt19th_mate.homeTab;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;

import java.util.ArrayList;

/**
 * Created by hyeon on 2016-12-26.
 */

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    ArrayList<ImageData> itemDatas;
    View.OnClickListener clickEvent;
    RequestManager requestManager;

    public Adapter(ArrayList<ImageData> itemDatas, View.OnClickListener clickEvent, RequestManager requestManager) {
        this.itemDatas = itemDatas;
        this.clickEvent = clickEvent;
        this.requestManager = requestManager;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        ViewHolder viewholder=new ViewHolder(itemView);

        itemView.setOnClickListener(clickEvent);
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      requestManager.load(itemDatas.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

}
