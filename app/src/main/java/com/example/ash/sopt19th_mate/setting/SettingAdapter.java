package com.example.ash.sopt19th_mate.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ash.sopt19th_mate.R;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2016-12-30.
 */

public class SettingAdapter extends BaseAdapter{
    ArrayList<ListViewItem> datas;
    LayoutInflater inflater;

    public SettingAdapter(LayoutInflater inflater, ArrayList<ListViewItem> datas) {
        this.datas= datas;
        this.inflater= inflater;
    }



    @Override
    public int getCount() {
        return datas.size(); //datas의 개수를 리턴
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);//datas의 특정 인덱스 위치 객체 리턴.
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView==null){
            convertView= inflater.inflate(R.layout.listview_item, null);
        }

        TextView title= (TextView)convertView.findViewById(R.id.textView);
        ImageView icon = (ImageView) convertView.findViewById(R.id.button);

        title.setText(datas.get(position).getTitle() );
        icon.setImageResource(datas.get(position).getIcon());

        return convertView;
    }


}
