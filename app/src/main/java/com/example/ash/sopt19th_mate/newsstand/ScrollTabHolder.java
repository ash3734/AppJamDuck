package com.example.ash.sopt19th_mate.newsstand;

import android.widget.AbsListView;

/**
 * Created by ash on 2017-01-01.
 */

public interface ScrollTabHolder {

    void adjustScroll(int scrollHeight);

    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}