package com.example.ash.sopt19th_mate.board;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ash.sopt19th_mate.R;

/**
 * Created by ash on 2017-01-03.
 */

public class MViewHolder extends RecyclerView.ViewHolder{
    ImageView imageViewMyImage;
    TextView textViewNickName;
    TextView textViewTime;
    TextView textViewContent;
    ImageView imageViewGood;
    ImageView imageViewBad;
    TextView textViewLikeCount;
    TextView textViewComentCount;
    TextView textViewDisLikeCount;
    TextView textViewDelete;

    public MViewHolder(View itemView) {
        super(itemView);
        imageViewMyImage = (ImageView) itemView.findViewById(R.id.boadlist_imageview_userimg);
        textViewNickName = (TextView) itemView.findViewById(R.id.boadlist_textview_nickname);
        textViewTime = (TextView) itemView.findViewById(R.id.boadlist_textview_time);
        textViewContent = (TextView)itemView.findViewById(R.id.boadlist_textview_content);
        textViewLikeCount = (TextView)itemView.findViewById(R.id.boadlist_texview_good_count);
        textViewComentCount = (TextView)itemView.findViewById(R.id.boadlist_textview_coment_count);
        imageViewGood = (ImageView)itemView.findViewById(R.id.boadlist_imageview_good);
        imageViewBad = (ImageView)itemView.findViewById(R.id.boadlist_imageview_bad);
        textViewDisLikeCount = (TextView)itemView.findViewById(R.id.boadlis_texview_bad_count);
        textViewDelete = (TextView)itemView.findViewById(R.id.boardlist_texview_delete);
    }
}
