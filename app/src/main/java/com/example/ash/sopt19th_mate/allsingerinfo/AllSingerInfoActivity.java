package com.example.ash.sopt19th_mate.allsingerinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.ash.sopt19th_mate.R;

import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSingerInfoActivity extends AppCompatActivity {

    Button button;
    NetworkService service;
    ArrayList<InfoList> InfoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_singer_info);
        service = ApplicationController.getInstance().getNetworkService();


        button = (Button)findViewById(R.id.GetAll);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<InfoResult> infoResult = service.infoResult(4);
                infoResult.enqueue(new Callback<InfoResult>() {
                    @Override
                    public void onResponse(Call<InfoResult> call, Response<InfoResult> response) {
                        if(response.isSuccessful())
                            InfoData = response.body().result;

                        for(int i = 0; i<InfoData.size(); i++)
                        {
                            if(InfoData.get(i).is_most.equals("t"))
                            {
                                Log.v("최애", InfoData.get(i).name);
                            }
                            else
                                Log.v("차애", InfoData.get(i).name);
                        }
                        Log.v("성공", "성공");
                    }
                    @Override
                    public void onFailure(Call<InfoResult> call, Throwable t) {

                    }

                });
            }
        });
    }
}