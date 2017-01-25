package com.example.ash.sopt19th_mate.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ash.sopt19th_mate.R;


/**
 * Created by hyeon on 2016-12-30.
 */

public class MypageViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    ImageView imageView2;
    TextView textView;
    TextView textView2;
    View nextBulView;

    public MypageViewHolder(View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.mypage_Sub_Background);
        imageView2=(ImageView)itemView.findViewById(R.id.mypage_Sub_CImageView_Id);
        textView=(TextView)itemView.findViewById(R.id.mypage_Sub_TextView_Id);
        textView2=(TextView)itemView.findViewById(R.id.mypage_Main_TextView_Id);
        nextBulView=(View)itemView.findViewById(R.id.mypage_Sub_Bul_Id);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public View getNextBulView() {
        return nextBulView;
    }
}
