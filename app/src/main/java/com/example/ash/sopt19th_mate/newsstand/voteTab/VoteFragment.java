package com.example.ash.sopt19th_mate.newsstand.voteTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.home.HomeDrawerActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;
import com.example.ash.sopt19th_mate.newsstand.ScrollTabHolderFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ash on 2017-01-01.
 */

public class VoteFragment extends ScrollTabHolderFragment {

    private static final String ARG_POSITION = "position";

    private ListView mListView;
    private ArrayList<VoteListData> mListItems;
    public RequestManager mGlideRequestManager;
    NetworkService service;
    private int mPosition;
    private mAdapter mAdapter;
    ArrayList<VoteListData> preData;
    ArrayList<VoteListData> curData;

    public static Fragment newInstance(int position) {
        VoteFragment f = new VoteFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

        mListItems = new ArrayList<VoteListData>();

        service = ApplicationController.getInstance().getNetworkService();

//ApplicationController.getInstance().getS_id(), ApplicationController.getInstance().getM_id()
        //이 밑은 음방 정보.
        Call<VoteResult> requestVoteData = service.getVoteData(14,4);
        mGlideRequestManager = Glide.with(this);
        Log.v("내 최애", String.valueOf(ApplicationController.getInstance().getS_id()));
        //이거 왜 0??
        requestVoteData.enqueue(new Callback<VoteResult>() {
            @Override
            public void onResponse(Call<VoteResult> call, Response<VoteResult> response) {
                mListItems = response.body().result;
                processingData();
                mAdapter = new mAdapter(mListItems,mGlideRequestManager,clickEvent);
                Log.d("sibal",mListItems.get(0).mp_name);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<VoteResult> call, Throwable t) {
                Log.d("sibal","서버실패");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vote_fragment_list, null);

        mListView = (ListView) v.findViewById(R.id.votefragment_listView);

        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
        placeHolderView.setBackgroundColor(0xFFFFFFFF);
        mListView.addHeaderView(placeHolderView);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView.setOnScrollListener(new OnScroll());

        if(HomeDrawerActivity.NEEDS_PROXY){//in my moto phone(android 2.1),setOnScrollListener do not work well
            mListView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mScrollTabHolder != null)
                        mScrollTabHolder.onScroll(mListView, 0, 0, 0, mPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
            return;
        }

        mListView.setSelectionFromTop(1, scrollHeight);

    }

    public class OnScroll implements OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        }

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount, int pagePosition) {
    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = mListView.getPositionForView(v);
            ImageView goVote = (ImageView)v.findViewById(R.id.votelist_imageview_vote);
            goVote.setImageResource(R.drawable.main_greyheart);
            Intent intent = new Intent(v.getContext(), DialogActivity.class);
            intent.putExtra("preDatas",preData);
            intent.putExtra("curDatas",curData);
            intent.putExtra("position",itemPosition);
            startActivity(intent);
        }
    };
    public void processingData(){
        VoteListData temp1 = new VoteListData();
        temp1.mp_name = "사전/선호도 투표";
        preData = new ArrayList<VoteListData>();
        preData.add(temp1);
        VoteListData temp2 = new VoteListData();
        temp2.mp_name = "실시간 투표";
        curData = new ArrayList<VoteListData>();
        curData.add(temp2);
        for(VoteListData data : mListItems){
            //사전투표가 null이 아니면 사전투표 리스트에 추가
            if(data.favorite_vote!=null){
                preData.add(data);
            }
            //문자투표가 null이 아니면 실시간투표 리스트에 추가
            if(data.sms_vote!=null){
                curData.add(data);
            }
        }
        mListItems.clear();
        mListItems.addAll(preData);
        mListItems.addAll(curData);
    }

}