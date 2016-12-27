package com.example.ash.sopt19th_mate.setsinger;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ash.sopt19th_mate.R;

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
    View.OnClickListener clickEvent;
    boolean search = false;

    public SingerAdapter(ArrayList<ItemData> itemDatas, View.OnClickListener clickEvent, ArrayList<ItemData> itemdata2)
    {
        this.itemDatas = itemDatas;
        this.clickEvent = clickEvent;
        ///여기는 검색

        this.arSrc = itemdata2;
        this.totalDatas = new ArrayList<ItemData>();
        this.totalDatas.addAll(this.arSrc);
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
        itemView.setOnClickListener(clickEvent);

        return viewHolder;
        //어댑터를 실행했을 때 뷰 홀더를 기억하게끔.
    }

    @Override
    public void onBindViewHolder(ViewHolderActivity holder, int position) {
        // holder.textView.setText(itemDatas.get(position));
        //holder.contentView.setText(itemDatas.get(position).content);
        //뷰홀더를 가져왔기 때문에 뷰홀더에 있는 xml에 접근할 수 있음.
        if(search == false) {
            holder.singer.setImageResource(itemDatas.get(position).singer);
            holder.name.setText(itemDatas.get(position).name);
            holder.most.setImageResource(itemDatas.get(position).most);
        }
        else {
            holder.singer.setImageResource(arSrc.get(position).singer);
            holder.name.setText(arSrc.get(position).name);
            holder.most.setImageResource(arSrc.get(position).most);
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
            for (int i = 0; i < totalDatas.size() ; i++)
            {
                String wp = totalDatas.get(i).getName();

                if (wp.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    arSrc.add(totalDatas.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
