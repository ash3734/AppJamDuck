package com.example.ash.sopt19th_mate.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.network.NetworkService;
import com.example.ash.sopt19th_mate.setsinger.SingerList;
import com.example.ash.sopt19th_mate.setsinger.SingerResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {
    NetworkService service;
    ArrayList<SingerList> singerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        service = ApplicationController.getInstance().getNetworkService();
        final Call<SingerResult> singerResult = service.singerResult();
        singerResult.enqueue(new Callback<SingerResult>() {
            @Override
            public void onResponse(Call<SingerResult> call, Response<SingerResult> response) {
                if(response.isSuccessful())
                {

                    singerList = response.body().result;
                    ApplicationController.singerList=singerList;
                    Log.v("길이", String.valueOf(singerList.size()));
                    for(int i = 0; i<singerList.size(); i++)
                    {
                        ApplicationController.getInstance().getNumberSingerSet().put(singerList.get(i).getName(), singerList.get(i).getS_id());
                        Log.v(singerList.get(i).getName(), String.valueOf(singerList.get(i).getS_id()));
                       // Log.d("MyID",singerList.get(i++).getTitle_song());

                    }
                }

            }
            @Override
            public void onFailure(Call<SingerResult> call, Throwable t) {
            }
        });




        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                startActivity(new Intent(getApplicationContext(), AndroidTwitterExampleInit.class));
                finish();
            }
        }, 5000);




    }
}
