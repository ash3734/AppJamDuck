package com.example.ash.sopt19th_mate.board.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.board.BoardListData;
import com.example.ash.sopt19th_mate.network.NetworkService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    ArrayList<ComentListData> datas;
    MAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    BoardListData boardListData;
    ImageView imageViewUserImage;
    TextView textViewNickName;
    TextView textViewTime;
    TextView textViewContent;
    ImageView imageViewContentImage;
    ImageView imageViewGood;
    TextView textViewGoodCount;
    ImageView imageViewBad;
    TextView textViewBadCount;
    TextView textViewComentCount;
    RecyclerView recyclerView;
    EditText editTextComent;
    ImageView button;
    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        imageViewUserImage = (ImageView)findViewById(R.id.boadDetail_imageview_userimg);
        textViewNickName = (TextView)findViewById(R.id.boadDetail_textview_nickname);
        textViewTime = (TextView)findViewById(R.id.boadDetail_textview_time);
        textViewContent = (TextView)findViewById(R.id.boadDetail_textview_content);
        imageViewContentImage = (ImageView)findViewById(R.id.boadDetail_imageview_contentimg);
        imageViewGood = (ImageView)findViewById(R.id.boadDetail_imageview_good);
        textViewGoodCount  = (TextView)findViewById(R.id.boadDetail_texview_good_count);
        imageViewBad = (ImageView)findViewById(R.id.boadDetail_imageview_bad);
        textViewBadCount = (TextView)findViewById(R.id.boadDetail_texview_bad_count);
        textViewComentCount  = (TextView)findViewById(R.id.boadDetail_textview_coment_count);
        recyclerView = (RecyclerView)findViewById(R.id.boadDetail_recyclerview);
        editTextComent = (EditText)findViewById(R.id.boaddetail_edittext_coment);
        button = (ImageView) findViewById(R.id.boaddetail_button);

        Intent intent = getIntent();
        boardListData = (BoardListData)intent.getSerializableExtra("data");
        Glide.with(this).load(boardListData.profile_img).into(imageViewUserImage);
        textViewNickName.setText(boardListData.nickname);
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("dd:HH:mm");
        String formatDate = sdfNow.format(date);
        String commentTime = boardListData.b_time;
        textViewTime.setText(processingData(formatDate,commentTime));

        textViewContent.setText(boardListData.b_content);
        Glide.with(this).load(boardListData.img).into(imageViewContentImage);
        textViewComentCount.setText(String.valueOf(boardListData.c_count));
        recyclerView.setHasFixedSize(true);
        textViewGoodCount.setText(String.valueOf(boardListData.like_count));
        textViewBadCount.setText(String.valueOf(boardListData.dislike_count));
        if(TextUtils.isEmpty(boardListData.is_like)){

        }
        else if(boardListData.is_like.equals("t")){
            imageViewGood.setImageResource(R.drawable.good_click);
        }
        else{
            imageViewBad.setImageResource(R.drawable.bad_click);
        }

        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        service = ApplicationController.getInstance().getNetworkService();
        int i = boardListData.b_id;

        final Call<ComentResult> requestComentData = service.getComentData(boardListData.b_id);
        requestComentData.enqueue(new Callback<ComentResult>() {
            @Override
            public void onResponse(Call<ComentResult> call, Response<ComentResult> response) {
                mAdapter.setAdapter(response.body().board);
                datas = response.body().board;

            }

            @Override
            public void onFailure(Call<ComentResult> call, Throwable t) {

            }
        });
        mAdapter = new MAdapter(datas);
        recyclerView.setAdapter(mAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //c_content = RequestBody.create(MediaType.parse("multipart/form-data"), editTextComent.getText().toString());
                //b_time = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(System.currentTimeMillis()));
                int i = boardListData.b_id;
                long now = System.currentTimeMillis();
                // 현재시간을 date 변수에 저장한다.
                Date date = new Date(now);
                // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
                SimpleDateFormat sdfNow = new SimpleDateFormat("dd:HH:mm");
                String formatDate = sdfNow.format(date);
                //-----------------------------------------------------아이디 바꺼야대--------------------------------------------------------
                int m_id = ApplicationController.getInstance().getM_id();
                Call<CommentRegisterResult> requestCommentRegiseter = service.requestCommentRegisterBoard(
                        new CommentRegisterData(boardListData.b_id,m_id,editTextComent.getText().toString(),formatDate));
                requestCommentRegiseter.enqueue(new Callback<CommentRegisterResult>() {
                    @Override
                    public void onResponse(Call<CommentRegisterResult> call, Response<CommentRegisterResult> response) {
                        if(response.body().result.equals("success")) {
                            int i = boardListData.b_id;
                            editTextComent.setText("");
                            InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                            imm.hideSoftInputFromWindow(editTextComent.getWindowToken(), 0);


                            final Call<ComentResult> requestComentData = service.getComentData(boardListData.b_id);
                            requestComentData.enqueue(new Callback<ComentResult>() {
                                @Override
                                public void onResponse(Call<ComentResult> call, Response<ComentResult> response) {
                                    String str = response.body().board.get(0).c_content;
                                    mAdapter.setAdapter(response.body().board);
                                    datas = response.body().board;
                                    textViewComentCount.setText(String.valueOf(++boardListData.c_count));

                                }

                                @Override
                                public void onFailure(Call<ComentResult> call, Throwable t) {

                                }
                            });
                        }
                        else{
                            Log.d("sibal","??");
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentRegisterResult> call, Throwable t) {

                    }
                });

            }
        });
    }
    String processingData(String commentTime,String currentTime){
        int total = 0;
        int curtotal = 0;
        int []curTime = new int[3];
        String []curTimeString = currentTime.split(":");
        for(int i=0;i<3;i++) {
            curTime[i] = Integer.parseInt(curTimeString[i]);
        }

        int []timeary = new int[3];;
        String []commentTimeString = commentTime.split(":");
        for(int i=0;i<3;i++) {
            timeary[i] = Integer.parseInt(commentTimeString[i]);
        }
        curtotal = (curTime[0])*1440 + (curTime[1])*60 + (curTime[2]);
        total = (timeary[0])*1440 + (timeary[1])*60 + (timeary[2]);
        if(curtotal - total < 1440){
            // 하루안

            int t1 = ( curtotal - total );
            // 분임
            if(t1 / 60 == 0){
                return String.valueOf(t1)+"분전";
            }else{
                return  String.valueOf(t1/60)+"시간전";
            }

        }else{
            int t = (curtotal - total) / 1440;
            return String.valueOf(t)+"일전";
        }
    }
}
