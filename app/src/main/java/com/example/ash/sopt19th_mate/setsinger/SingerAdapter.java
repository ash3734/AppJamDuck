package com.example.ash.sopt19th_mate.setsinger;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ash.sopt19th_mate.R;

import java.util.ArrayList;

/**
 * Created by dldud on 2016-11-10.
 */

public class SingerAdapter extends RecyclerView.Adapter<ViewHolderActivity>
{
    ArrayList<ItemData> itemDatas;
    View.OnClickListener clickEvent;

    public SingerAdapter(ArrayList<ItemData> itemDatas, View.OnClickListener clickEvent)
    {
        this.itemDatas = itemDatas;
        this.clickEvent = clickEvent;
    }

    @Override
    public ViewHolderActivity onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_holder, parent,false);
        //화면상에 보여줄 레이아웃을 지정해주어야 함.
        ViewHolderActivity viewHolder = new ViewHolderActivity(itemView);
        itemView.setOnClickListener(clickEvent);

        return viewHolder;
        //어댑터를 실행했을 때 뷰 홀더를 기억하게끔.
    }

    @Override
    public void onBindViewHolder(ViewHolderActivity holder, int position) {
        // holder.textView.setText(itemDatas.get(position));
        //holder.contentView.setText(itemDatas.get(position).content);
        //뷰홀더를 가져왔기 때문에 뷰홀더에 있는 xml에 접근할 수 있음.
        holder.singer.setImageResource(itemDatas.get(position).singer);
        holder.name.setText(itemDatas.get(position).name);
        holder.most.setImageResource(itemDatas.get(position).most);
        //holder.button.setText(itemDatas.get(position).button);

    }//실질적으로 리스트 항목을 뿌려주는 역할을 함.

    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}
