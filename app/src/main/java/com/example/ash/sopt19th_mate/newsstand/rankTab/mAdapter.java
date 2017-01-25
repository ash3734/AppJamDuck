package com.example.ash.sopt19th_mate.newsstand.rankTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ash.sopt19th_mate.R;

import java.util.ArrayList;

/**
 * Created by ash on 2017-01-01.
 */

public class mAdapter extends BaseAdapter{
    private ArrayList<RankData> datas;

    public mAdapter(ArrayList<RankData> mListData) {
        this.datas = mListData;
    }
    public int[] images ={
      R.drawable.melon,
            R.drawable.naver,
        R.drawable.mnet
           // R.drawable.bugs,

           // R.drawable.genie,
           //
    };

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        RankData listViewItem = datas.get(pos);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.rank_list_item, parent, false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ranklist_imageview_chart);
        TextView textView = (TextView)convertView.findViewById(R.id.ranklist_textview_rank);
        TextView textView1 = (TextView)convertView.findViewById(R.id.ranklist_textview_changeRank);
        Glide.with(convertView.getContext()).load(images[pos]).into(imageView);
        textView.setText(listViewItem.rank+"위");
//        textView.setText(listViewItem.change);

        return convertView;
    }
}
