package com.example.ash.sopt19th_mate.newsstand.voteTab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.dev.sacot41.scviewpager.DotsView;
import com.dev.sacot41.scviewpager.SCViewAnimationUtil;
import com.dev.sacot41.scviewpager.SCViewPager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;

import java.util.ArrayList;

public class DialogActivity extends AppCompatActivity {

    private int NUM_PAGES = 3;

    private SCViewPager mViewPager;
    private MPagerAdapter mPageAdapter;
    private DotsView mDotsView;
    private ArrayList<VoteListData> preData;
    private ArrayList<VoteListData> curData;
    private ArrayList<VoteListData> datas;
    private int position;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS},1);
        context = this;
        ApplicationController.getInstance().setContext(context);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        Intent intent = getIntent();
        preData = (ArrayList<VoteListData>)intent.getSerializableExtra("preDatas");
        curData = (ArrayList<VoteListData>)intent.getSerializableExtra("curDatas");
        position = intent.getExtras().getInt("position");
        // 실시간 투표일경우
        if(preData.size()>=position){
            NUM_PAGES = preData.size()-1;
            datas = preData;
        }
        else{
            NUM_PAGES = curData.size()-1;
            datas = curData;
        }
        mViewPager = (SCViewPager) findViewById(R.id.viewpager_main_activity);
        mDotsView = (DotsView) findViewById(R.id.dotsview_main);
        mDotsView.setDotRessource(R.drawable.dot_selected, R.drawable.dot_unselected);
        mDotsView.setNumberOfPage(NUM_PAGES);

        mPageAdapter = new MPagerAdapter(getSupportFragmentManager(),datas);
        mPageAdapter.setNumberOfPage(NUM_PAGES);
        mPageAdapter.setFragmentBackgroundColor(R.color.theme_100);
        mViewPager.setAdapter(mPageAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mDotsView.selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }


        });

        final Point size = SCViewAnimationUtil.getDisplaySize(this);
    }
}
