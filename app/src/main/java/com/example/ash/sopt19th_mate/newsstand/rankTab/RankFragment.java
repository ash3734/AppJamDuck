package com.example.ash.sopt19th_mate.newsstand.rankTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

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

public class RankFragment extends ScrollTabHolderFragment {

    private static final String ARG_POSITION = "position";

    private ListView mListView;
    private ArrayList<RankData> mListItems;
    mAdapter madapter;
    ArrayList<RankListData> melonRankResult;
    ArrayList<RankListData> mnetRankResult;
    ArrayList<RankListData> genieRankResult;
    ArrayList<RankListData> bugseRankResult;
    ArrayList<RankListData> naverRankResult;
    NetworkService service;
    private int mPosition;

    public static Fragment newInstance(int position) {
        RankFragment f = new RankFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
        Log.d("MyID", String.valueOf(ApplicationController.getInstance().getM_id()));
        mListItems = new ArrayList<RankData>();
        service = ApplicationController.getInstance().getNetworkService();
       // Call<VoteResult> requestVoteData = service.getVoteData("bigbang");
        Call<MelonRankResult> requestMelonData = service.getMelonData();
        requestMelonData.enqueue(new Callback<MelonRankResult>() {
            @Override
            public void onResponse(Call<MelonRankResult> call, Response<MelonRankResult> response) {
                String str = response.body().title.get(0).title;
                melonRankResult = response.body().title;
                int i=0;
                for (RankListData data: melonRankResult) {
                    i++;
                    if(data.title.equals("에라 모르겠다")){
                        break;
                    }
                }

                mListItems.add(new RankData(i,1));
                madapter = new mAdapter(mListItems);
                mListView.setAdapter(madapter);
            }

            @Override
            public void onFailure(Call<MelonRankResult> call, Throwable t) {

            }
        });
        /*Call<NaverRankResult> requestNaverData = service.getNaverData();
        requestNaverData.enqueue(new Callback<NaverRankResult>() {
            @Override
            public void onResponse(Call<NaverRankResult> call, Response<NaverRankResult> response) {
                naverRankResult = response.body().title;
            }

            @Override
            public void onFailure(Call<NaverRankResult> call, Throwable t) {

            }
        });
        Call<MnetRankResult> requsetMnet = service.getMnetnData();
        requsetMnet.enqueue(new Callback<MnetRankResult>() {
            @Override
            public void onResponse(Call<MnetRankResult> call, Response<MnetRankResult> response) {
                mnetRankResult = response.body().title;
                Log.d("ash3734", String.valueOf(mnetRankResult.size()));
                int i=0;
                for (RankListData data: melonRankResult) {
//                    i++;
//                    if(data.title.equals("에라 모르겠다")){
//                        break;
//                    }
//                }
//
//                mListItems.add(new RankData(i,1));
//                i=0;
//                for (RankListData data: naverRankResult) {
//                    i++;
//                    if(data.title.equals("에라 모르겠다")){
//                        break;
//                    }
//                }
//
//                mListItems.add(new RankData(i,1));
//      */       //   i=0;
//                for (RankListData data: mnetRankResult) {
//                    i++;
//                    if(data.title.equals("에라 모르겠다")){
//                        break;
//                    }
//                }
//                mListItems.add(new RankData(i,1));
//
//
//                madapter = new mAdapter(mListItems);
//                mListView.setAdapter(madapter);
//            }
//
//            @Override
//            public void onFailure(Call<MnetRankResult> call, Throwable t) {
//
//            }
//        });
      /*  Call<BugsRankResult> requestBugsData = service.getBugsData();
        requestBugsData.enqueue(new Callback<BugsRankResult>() {
            @Override
            public void onResponse(Call<BugsRankResult> call, Response<BugsRankResult> response) {
                bugseRankResult = response.body().title;
            }

            @Override
            public void onFailure(Call<BugsRankResult> call, Throwable t) {

            }
        });
        Call<NaverRankResult> requestNaverData = service.getNaverData();
        requestNaverData.enqueue(new Callback<NaverRankResult>() {
            @Override
            public void onResponse(Call<NaverRankResult> call, Response<NaverRankResult> response) {
                naverRankResult = response.body().title;
            }

            @Override
            public void onFailure(Call<NaverRankResult> call, Throwable t) {

            }
        });
        String SongName=null;
*//*        for (SingerList data :
             ApplicationController.singerList) {
          //  Log.d("mainSinger",ApplicationController.myMainSinger);
           // Log.d("mainSinger",data.getTitle_song());
           // Log.d("mainSinger",SongName);
//             Log.d("mainSinger",ApplicationController.myMainSinger);
           // Log.d("mainSinger",data.getTitle_song());

            if(data.getName().equals(ApplicationController.myMainSinger)){
                SongName = data.getTitle_song();
            }
        }*//*
        final String song = SongName;
        Call<GenieRankResult> requestGenieData = service.getGenieData();
        requestGenieData.enqueue(new Callback<GenieRankResult>() {
            @Override
            public void onResponse(Call<GenieRankResult> call, Response<GenieRankResult> response) {
                genieRankResult = response.body().title;
                int i=0;
                for (RankListData data: melonRankResult) {
                    i++;
                    if(data.title.equals("에라 모르겠다")){
                        break;
                    }
                }
                mListItems.add(new RankData(i,1));
                i=0;
                String str = mnetRankResult.get(0).title;
                for (RankListData data: mnetRankResult) {
                    i++;
                    if(data.title.equals("에라 모르겠다")){
                        break;
                    }
                }

                mListItems.add(new RankData(i,1));
                i=0;
                for (RankListData data: genieRankResult) {
                    i++;
                    if(data.title.equals("에라 모르겠다")){
                        break;
                    }
                }
                mListItems.add(new RankData(i,1));
                i=0;
                for (RankListData data: bugseRankResult) {
                    i++;
                    if(data.title.equals("에라 모르겠다")){
                        break;
                    }
                }
                mListItems.add(new RankData(i,1));
                i=0;
                for (RankListData data: naverRankResult) {
                    i++;
                    if(data.title.equals("에라 모르겠다")){
                        break;
                    }
                }
                mListItems.add(new RankData(i,1));
                madapter = new mAdapter(mListItems);
                mListView.setAdapter(madapter);
            }

            @Override
            public void onFailure(Call<GenieRankResult> call, Throwable t) {

            }
        });
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rank_fragment_list, null);

        mListView = (ListView) v.findViewById(R.id.rankfragment_listView);

        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
        placeHolderView.setBackgroundColor(0xFFFFFFFF);
        mListView.addHeaderView(placeHolderView);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView.setOnScrollListener(new OnScroll());
       // mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, android.R.id.text1, mListItems));

        if(HomeDrawerActivity.NEEDS_PROXY){//in my moto phone(android 2.1),setOnScrollListener do not work well
            mListView.setOnTouchListener(new View.OnTouchListener() {
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

    public class OnScroll implements AbsListView.OnScrollListener {

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

}