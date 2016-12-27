package com.example.ash.sopt19th_mate.application;

import android.app.Application;

import com.example.ash.sopt19th_mate.network.NetworkService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hyeon on 2016-12-26.
 */

public class ApplicationController extends Application {
    private static ApplicationController instance;

    private NetworkService networkService;
    private String baseUrl="";

    public NetworkService getNetworkService(){
        return networkService;
    }
    public static ApplicationController getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance=this;
        buildNetwork();
    }
    public void buildNetwork(){
        Retrofit.Builder builder=new Retrofit.Builder();
        Retrofit retrofit=builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        networkService=retrofit.create(NetworkService.class);
    }
}
