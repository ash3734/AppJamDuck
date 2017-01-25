package com.example.ash.sopt19th_mate.newsstand;

import android.support.v4.app.Fragment;

/**
 * Created by ash on 2017-01-01.
 */

public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }
}