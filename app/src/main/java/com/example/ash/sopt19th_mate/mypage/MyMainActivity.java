package com.example.ash.sopt19th_mate.mypage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.board.BoardActivity;
import com.example.ash.sopt19th_mate.home.HomeDrawerActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;
import com.example.ash.sopt19th_mate.setsinger.SingerList;
import com.example.ash.sopt19th_mate.setsinger.SingerResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMainActivity extends AppCompatActivity {

    ImageView myImageView;
    TextView NickTextView;
    ImageView MainSingerImageView;
    TextView MainSingerTextView;

    ImageView backImageView;
    ImageView backGoundImg;
    View MainBulView;
    View SubPlusSingerView;
    RecyclerView recyclerView;

    LinearLayoutManager mlayoutManager;

    ArrayList<MypageSSingerData> subSet;
    ArrayList<MypageSSingerData> mItemDatas;
    MyAdapter adapter;
    NetworkService service;

    RequestManager requestManager;
    RequestManager requestManager2;


    String name;
    ArrayList<MyPageItemData> dataSet;
    ArrayList<MyPageItemData> dataSet2;
    ArrayList<MyPageSearchList> dataSet3;
    ArrayList<SingerList> singerLists;
    String img;

    Uri data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);
        requestManager = Glide.with(this);
        requestManager2 = Glide.with(this);
        init();
        data = ApplicationController.getInstance().getData();
        Glide.with(getApplicationContext()).load(data).into(myImageView);
        NickTextView.setText(ApplicationController.getInstance().getNickname());

        Log.v("create", "create");

        dataSet = new ArrayList<MyPageItemData>();
        dataSet2 = new ArrayList<MyPageItemData>();
        singerLists = new ArrayList<SingerList>();

        service = ApplicationController.getInstance().getNetworkService();
        mItemDatas = new ArrayList<MypageSSingerData>();


        backGoundImg = (ImageView) findViewById(R.id.mymain_main_ImageView);

        backImageView = (ImageView) findViewById(R.id.mymain_BackImage_Id);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeDrawerActivity.class);
                startActivity(intent);
            }
        });
        MainBulView = (View) findViewById(R.id.mypage_Main_Bul_Id);
        MainBulView.setOnClickListener(new View.OnClickListener() { //메인 가수 게시판 클릭시
            @Override
            public void onClick(View view) {
                ApplicationController.getInstance().setBoardS_id(ApplicationController.getInstance().getMain_id());
                Log.v("메인", String.valueOf(ApplicationController.getInstance().getMain_id()));
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });


        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollview));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.mypage_RecyclerView_Id);
        mlayoutManager = new LinearLayoutManager(this);
        mlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mlayoutManager);

        //TODO: applicationController에서 저장된 서브 가수 이미지랑 이름 저장
//        adapter = new MyAdapter(null, null, null, null);
        Call<SingerResult> singerResult = service.singerResult();
        singerResult.enqueue(new Callback<SingerResult>() {
            @Override
            public void onResponse(Call<SingerResult> call, Response<SingerResult> response) {
                if(response.isSuccessful())
                {
                    //dataSet3 = response.body().result;
                    singerLists = response.body().result;
                    for(int i = 0; i<ApplicationController.getInstance().getTotalSingerCount();i++) {
                        name = ApplicationController.getInstance().getInfoData().get(i).name;
                            for (int j = 0; j < singerLists.size(); j++) {
                                if(ApplicationController.getInstance().getInfoData().get(i).is_most.equals("t")) {//최애 배경 설정.
                                    if (name.equals(singerLists.get(j).getName())) {
                                        Log.v("메인은", name);
                                        requestManager.load(singerLists.get(j).getMain_img()).into(MainSingerImageView);
                                        requestManager2.load(singerLists.get(j).getBg_img1()).into(backGoundImg);
                                        MainSingerTextView.setText(singerLists.get(j).getName());
                                    }
                                }
                                else
                                {
                                Log.v("name2", singerLists.get(j).getName());
                                if (name.equals(singerLists.get(j).getName())) {
                                    mItemDatas.add(new MypageSSingerData(singerLists.get(j).getName(), singerLists.get(j).getMain_img(), singerLists.get(j).getBg_img1()));
                                    Log.v("통신 됨 이름은?", name);
                                    break;
                                }
                            }
                        }
                        ApplicationController.getInstance().setMypageSSingerDatas(mItemDatas);
                        adapter = new MyAdapter(mItemDatas, requestManager, requestManager2, MyMainActivity.this);
                        recyclerView.setAdapter(adapter);
                        Log.v("name", name);
                    }
                }
            }
            @Override
            public void onFailure(Call<SingerResult> call, Throwable t) {

            }
        });


        SubPlusSingerView = (View) findViewById(R.id.mypage_plusSinger_View_Id);
        SubPlusSingerView.setOnClickListener(new View.OnClickListener() {       //마이페이지에서 가수 추가
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                    startActivity(intent);
            }
        });
        //service= ApplicationController.getInstance().getNetworkService();
    }


    public void ToBoard()
    {
        Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
        startActivity(intent);
    }
    public void init(){
        myImageView=(ImageView)findViewById(R.id.mypage_imageview_myimg);
        NickTextView=(TextView)findViewById(R.id.mypage_My_TextView_Id);
        MainSingerImageView=(ImageView)findViewById(R.id.mypage_Main_CImageView_Id);
        MainSingerTextView=(TextView)findViewById(R.id.mypage_Main_TextView_Id);

        myImageView.setImageURI(ApplicationController.getInstance().getData());
        NickTextView.setText(ApplicationController.getInstance().getNickname());
        //MainSingerImageView.setImageBitmap();
        //MainSingerTextView.setText("jin");
    }
}
