package com.example.ash.sopt19th_mate.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ash.sopt19th_mate.R;

public class ListViewHolderActivity extends RecyclerView.ViewHolder {

    ImageView mainImage;
    TextView singerText;
    public ListViewHolderActivity(View itemView) {
        super(itemView);
        mainImage = (ImageView)itemView.findViewById(R.id.main_singer);
        singerText = (TextView)itemView.findViewById(R.id.my_singer);
    }
}
