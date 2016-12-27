package com.example.ash.sopt19th_mate.setsinger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ash.sopt19th_mate.R;

public class ViewHolderActivity extends RecyclerView.ViewHolder {

    ImageView singer;
    TextView name;
    ImageView most;
    public ViewHolderActivity(View itemView) {
        super(itemView);
        singer = (ImageView)itemView.findViewById(R.id.img_singer);
        name = (TextView)itemView.findViewById(R.id.name_singer);
        most = (ImageView)itemView.findViewById(R.id.img_most);
    }
}
