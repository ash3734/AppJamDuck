package com.example.ash.sopt19th_mate.homeTab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.ash.sopt19th_mate.R;

/**
 * Created by hyeon on 2016-12-26.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public ViewHolder(View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.home_ImageView_Id);
    }
}
