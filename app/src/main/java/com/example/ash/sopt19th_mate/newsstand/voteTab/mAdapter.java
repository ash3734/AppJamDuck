package com.example.ash.sopt19th_mate.newsstand.voteTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;

import java.util.ArrayList;

/**
 * Created by ash on 2017-01-01.
 */

/*
public class mAdapter extends BaseAdapter{
    private ArrayList<VoteListData> mListData;
    private RequestManager mRequestManager;
    View.OnClickListener clickEvent;

    public mAdapter(ArrayList<VoteListData> mListData, RequestManager mRequestManager,View.OnClickListener clickEvent) {
        this.mListData = mListData;
        this.clickEvent = clickEvent;
        this.mRequestManager = mRequestManager;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        VoteListData listViewItem = mListData.get(pos);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //데이터가 아닐경우 구분자
        if(mListData.get(pos).mp_name.equals("사전/선호도 투표")||mListData.get(pos).mp_name.equals("실시간 투표")){
            convertView = inflater.inflate(R.layout.vote_list_item_text, parent, false);
            TextView textViewProgramName = (TextView)convertView.findViewById(R.id.votefragment_textview_vote);
            textViewProgramName.setText(mListData.get(pos).mp_name);
        }
        else{
            convertView = inflater.inflate(R.layout.vote_list_itme_program, parent, false);
            TextView textViewProgramName = (TextView)convertView.findViewById(R.id.votelist_textview_programname);
            TextView textViewPeriodDetail= (TextView)convertView.findViewById(R.id.votelist_textview_voteperioddetail);
            ImageView imageViewProgramImg = (ImageView)convertView.findViewById(R.id.votelist_imageview_program);
            ImageView imageViewGoVote = (ImageView)convertView.findViewById(R.id.votelist_imageview_vote);
            Glide.with(convertView.getContext()).load(R.drawable.main_govote).into(imageViewGoVote);
            textViewProgramName.setText(listViewItem.mp_name);
            textViewPeriodDetail.setText(listViewItem.fav_period);
            mRequestManager.load(listViewItem.mp_img).into(imageViewProgramImg);
            convertView.setOnClickListener(clickEvent);
        }
        return convertView;
    }
}
*/
public class mAdapter extends BaseAdapter {
    private ArrayList<VoteListData> mListData;
    private RequestManager mRequestManager;
    View.OnClickListener clickEvent;

    public mAdapter(ArrayList<VoteListData> mListData, RequestManager mRequestManager,View.OnClickListener clickEvent) {
        this.mListData = mListData;
        this.clickEvent = clickEvent;
        this.mRequestManager = mRequestManager;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        VoteListData listViewItem = mListData.get(pos);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //데이터가 아닐경우 구분자
        if(mListData.get(pos).mp_name.equals("사전/선호도 투표")||mListData.get(pos).mp_name.equals("실시간 투표")){
            convertView = inflater.inflate(R.layout.vote_list_item_text, parent, false);
            TextView textViewProgramName = (TextView)convertView.findViewById(R.id.votefragment_textview_vote);
            textViewProgramName.setText(mListData.get(pos).mp_name);
        }
        else{
            convertView = inflater.inflate(R.layout.vote_list_itme_program, parent, false);
            TextView textViewProgramName = (TextView)convertView.findViewById(R.id.votelist_textview_programname);
            // TextView textViewPeriodDetail= (TextView)convertView.findViewById(R.id.votelist_textview_voteperioddetail);
            //
            TextView textViewPeriodFrag = (TextView)convertView.findViewById(R.id.votelist_textview_votedday);
            //

            ImageView imageViewProgramImg = (ImageView)convertView.findViewById(R.id.votelist_imageview_program);
            ImageView imageViewGoVote = (ImageView)convertView.findViewById(R.id.votelist_imageview_vote);
            textViewProgramName.setText(listViewItem.mp_name);
            // textViewPeriodDetail.setText(listViewItem.fav_period);

            //
            textViewPeriodFrag.setText(listViewItem.fav_period);

            mRequestManager.load(listViewItem.mp_img).into(imageViewProgramImg);
            convertView.setOnClickListener(clickEvent);



        }
        return convertView;
    }
}
