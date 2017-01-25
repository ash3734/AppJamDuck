package com.example.ash.sopt19th_mate.application;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.example.ash.sopt19th_mate.allsingerinfo.InfoList;
import com.example.ash.sopt19th_mate.home.SingerNumberList;
import com.example.ash.sopt19th_mate.mypage.MypageSSingerData;
import com.example.ash.sopt19th_mate.network.NetworkService;
import com.example.ash.sopt19th_mate.notice.NoticeData;
import com.example.ash.sopt19th_mate.setsinger.SingerList;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hyeon on 2016-12-26.
 */


public class ApplicationController extends Application {
    private static ApplicationController instance;

    private NetworkService networkService;
    private String baseUrl = "http://52.78.100.183:5000/";

    public NetworkService getNetworkService() {
        return networkService;
    }
    //public
    public static ApplicationController getInstance() {
        return instance;
    }

    public static ArrayList<SingerList> singerList;
    public static String myMainSinger;
    private String nickname;
    private String name;
    private int m_id;
    private String profile_name;
    private String email;
    private ArrayList<NoticeData> NoticeTest = new ArrayList<NoticeData>();
    private int totalSingerCount;
    private int subSingerCount;
    private ArrayList<InfoList> infoData = new ArrayList<InfoList>();
    private ArrayList<MypageSSingerData> mypageSSingerDatas;
    private Context context;
    private int s_id;//메인에서 보여 줄 가수의 아이디(최애랑은 좀 다름)
    private int main_id;
    private ArrayList<SingerNumberList> singerNumberList;
    private HashMap<String, Integer> numberSingerSet = new HashMap<>();
    private String s_name;//화면에 나타날 이름(메인이랑 좀 다름)

    public int getBoardS_id() {
        return boardS_id;
    }

    public void setBoardS_id(int boardS_id) {
        this.boardS_id = boardS_id;
    }

    private int boardS_id;

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }



    public HashMap<String, Integer> getNumberSingerSet() {
        return numberSingerSet;
    }

    public void setNumberSingerSet(HashMap<String, Integer> numberSingerSet) {
        this.numberSingerSet = numberSingerSet;
    }


    public ArrayList<SingerNumberList> getSingerNumberList() {
        return singerNumberList;
    }

    public void setSingerNumberList(ArrayList<SingerNumberList> singerNumberList) {
        this.singerNumberList = singerNumberList;
    }



    public int getMain_id() {
        return main_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }




    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public ArrayList<MypageSSingerData> getMypageSSingerDatas() {

        return mypageSSingerDatas;
    }

    public void setMypageSSingerDatas(ArrayList<MypageSSingerData> mypageSSingerDatas) {
        this.mypageSSingerDatas = mypageSSingerDatas;
    }

    public ArrayList<InfoList> getInfoData() {
        return infoData;
    }

    public void setInfoData(ArrayList<InfoList> infoData) {
        this.infoData = infoData;
    }





    public int getSubSingerCount() {
        return subSingerCount;
    }

    public void setSubSingerCount(int subSingerCount) {
        this.subSingerCount = subSingerCount;
    }



    public int getTotalSingerCount() {
        return totalSingerCount;
    }

    public void setTotalSingerCount(int totalSingerCount) {
        this.totalSingerCount = totalSingerCount;
    }


    public ArrayList<NoticeData> getNoticeTest() {
        return NoticeTest;
    }

    public void setNoticeTest(ArrayList<NoticeData> noticeTest) {
        NoticeTest = noticeTest;
    }




    private Uri data;//프로필 이미지.

    public String getKind_sns() {
        return kind_sns;
    }

    public void setKind_sns(String kind_sns) {
        this.kind_sns = kind_sns;
    }

    private  String kind_sns;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        buildNetwork();
    }

    public void buildNetwork() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        networkService = retrofit.create(NetworkService.class);

    }

    public String getEmail() {
        return email;
    }

    public int getM_id() {
        return m_id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }


}
