package com.example.ash.sopt19th_mate.board.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ash.sopt19th_mate.R;


/**
 * Created by ash on 2017-01-03.
 */

public class ComentViewHolder extends RecyclerView.ViewHolder{
    TextView textViewNickName;
    TextView textViewtime;
    TextView textViewContent;

    public ComentViewHolder(View itemView) {
        super(itemView);
        textViewNickName = (TextView) itemView.findViewById(R.id.comentlist_textview_nickname);
        textViewtime = (TextView)itemView.findViewById(R.id.comentlist_textview_time);
        textViewContent = (TextView)itemView.findViewById(R.id.comentlist_textview_content);
    }
}
