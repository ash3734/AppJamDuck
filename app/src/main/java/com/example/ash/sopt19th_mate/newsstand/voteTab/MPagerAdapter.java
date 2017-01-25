package com.example.ash.sopt19th_mate.newsstand.voteTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dev.sacot41.scviewpager.SCViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ash on 2017-01-01.
 */

public class MPagerAdapter extends SCViewPagerAdapter {

    ArrayList<VoteListData> datas;

    MPagerAdapter(FragmentManager fragmentManager, ArrayList<VoteListData> datas) {
        super(fragmentManager);
        datas.remove(0);
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.create(datas,position);
    }
}
