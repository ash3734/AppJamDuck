package com.example.ash.sopt19th_mate.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ash.sopt19th_mate.R;

public class MyPageViewHolderActivity extends RecyclerView.ViewHolder {
    ImageView singer;
    TextView name;
    ImageView most;
    ImageView add;

    public MyPageViewHolderActivity(View itemView) {
        super(itemView);
        singer = (ImageView)itemView.findViewById(R.id.img_singer2);
        name = (TextView)itemView.findViewById(R.id.name_singer2);
        most = (ImageView)itemView.findViewById(R.id.img_most2);
        add = (ImageView)itemView.findViewById(R.id.img_add2);
    }
}
