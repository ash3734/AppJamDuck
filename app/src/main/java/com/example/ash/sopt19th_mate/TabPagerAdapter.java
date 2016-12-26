package com.example.ash.sopt19th_mate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ash.sopt19th_mate.homeTab.HomeFragment;

/**
 * Created by ash on 2016-12-26.
 */

public class TabPagerAdapter  extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    //int count 잠시 삭제 (파라메터)
    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                HomeFragment tabFragment1 = new HomeFragment();
                return tabFragment1;
            case 1:
                RankFragment tabFragment2 = new RankFragment();
                return tabFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}