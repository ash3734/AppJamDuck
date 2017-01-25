package com.example.ash.sopt19th_mate.homeTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.board.detail.DetailActivity;

import java.util.ArrayList;

/**
 * Created by ash on 2016-12-26.
 */

public class HomeFragment extends android.support.v4.app.Fragment{

    public RecyclerView recyclerViewPre;
    public RecyclerView recyclerViewCur;
    public RecyclerView recyclerViewFinal;
    public ArrayList<ImageData> datasPre;
    public ArrayList<ImageData> datasCur;
    public ArrayList<ImageData> datasFinal;
    public Adapter adapterPre;
    public Adapter adapterCur;
    public Adapter adapterFinal;

    public LinearLayoutManager mLayoutManager;
    public  RequestManager GliderequestManager;

    @Override
    public void onStart() {
        super.onStart();
        datasPre=new ArrayList<ImageData>();
        recyclerViewPre.setHasFixedSize(true);
        datasCur=new ArrayList<ImageData>();
        recyclerViewCur.setHasFixedSize(true);
        datasFinal=new ArrayList<ImageData>();
        recyclerViewFinal.setHasFixedSize(true);

        recyclerViewPre.setHasFixedSize(true);
        recyclerViewCur.setHasFixedSize(true);
        recyclerViewFinal.setHasFixedSize(true);

        GliderequestManager= Glide.with(this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewPre.setLayoutManager(mLayoutManager);
        recyclerViewCur.setLayoutManager(mLayoutManager);
        recyclerViewFinal.setLayoutManager(mLayoutManager);

        adapterCur=new Adapter(datasCur,clickEvent,GliderequestManager);
        adapterPre=new Adapter(datasPre,clickEvent,GliderequestManager);
        adapterFinal=new Adapter(datasFinal,clickEvent,GliderequestManager);

        recyclerViewPre.setAdapter(adapterPre);
        recyclerViewCur.setAdapter(adapterCur);
        recyclerViewFinal.setAdapter(adapterFinal);

      //  service=  ApplicationController.getInstance().getNetworkService();
    }
    public View.OnClickListener clickEvent=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int itemPosition=recyclerViewPre.getChildPosition(view);

            Intent intent= new Intent(getActivity(), DetailActivity.class);
            /*
            intent.putExtra();
            startActivity(intent);
            */
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewPre=(RecyclerView)(frag.findViewById(R.id.home_RecyclerViewPre_Id));
        recyclerViewCur=(RecyclerView)(frag.findViewById(R.id.home_RecyclerViewCur_Id));
        recyclerViewFinal=(RecyclerView)(frag.findViewById(R.id.home_RecyclerViewFinal_Id));
        return frag;

    }
}
