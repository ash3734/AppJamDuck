package com.example.ash.sopt19th_mate.board;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.board.detail.DetailActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BoardActivity extends AppCompatActivity {
    ArrayList<BoardListData> datas;
    ImageView imageViewSinger;
    TextView textViewSinger;
    TextView textViewRegister;
    RecyclerView recyclerView;
    MAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    NetworkService service;
    RequestManager mGlideRequestManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        imageViewSinger = (ImageView)findViewById(R.id.boad_imageview_singer_img);
        textViewSinger = (TextView)findViewById(R.id.boad_textview_singer_name);
        textViewRegister = (TextView)findViewById(R.id.boad_texview_regiseter);
        recyclerView = (RecyclerView)findViewById(R.id.boad_recyclerview);
        mGlideRequestManager = Glide.with(this);
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        service = ApplicationController.getInstance().getNetworkService();


        Call<BoardResult> requestBoardData = service.getMainData(ApplicationController.getInstance().getBoardS_id(),ApplicationController.getInstance().getM_id()); //빅뱅,멤버아이디
        requestBoardData.enqueue(new Callback<BoardResult>() {
            @Override
            public void onResponse(Call<BoardResult> call, Response<BoardResult> response) {
                if(response.isSuccessful()){
                    mAdapter.setMyAdapter(response.body().board);
                    datas = response.body().board;
                    if (datas.get(0)!=null)
                        Glide.with(getApplicationContext()).load(datas.get(0).bg_img1).into(imageViewSinger);
                    //여기 변수로 바꿔야대!!!!! ----------------------------------------------------------------
                    //textViewSinger.setText(ApplicationController.myMainSinger);
                }
            }

            @Override
            public void onFailure(Call<BoardResult> call, Throwable t) {

            }
        });
        mAdapter = new MAdapter(datas,clickEvent,mGlideRequestManager);
        recyclerView.setAdapter(mAdapter);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Call<BoardResult> requestBoardData = service.getMainData(14,4);
        requestBoardData.enqueue(new Callback<BoardResult>() {
            @Override
            public void onResponse(Call<BoardResult> call, Response<BoardResult> response) {
                if(response.isSuccessful()){
                    mAdapter.setMyAdapter(response.body().board);
                    datas = response.body().board;
                }
            }

            @Override
            public void onFailure(Call<BoardResult> call, Throwable t) {

            }
        });
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            BoardListData tempId = datas.get(itemPosition);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("data",tempId);
            startActivity(intent);
        }
    };
}
