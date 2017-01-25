package com.example.ash.sopt19th_mate.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ash.sopt19th_mate.R;

/**
 * Created by ash on 2016-12-26.
 */

public class RankFragment extends android.support.v4.app.Fragment {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.fragment_rank, container, false);
        return frag;

    }
}
