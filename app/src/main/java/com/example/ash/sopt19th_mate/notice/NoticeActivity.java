package com.example.ash.sopt19th_mate.notice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.home.HomeDrawerActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity implements MainView {

    private ArrayList<String> reGroupList = null;
    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<ChildStateObject>> mChildList = null;
    private ArrayList<ChildStateObject> mChildListContent = null;
    private ArrayList<ChildStateObject> mChildListContent2 = null;
    private ArrayList<ChildStateObject> mChildListContent3 = null;


    private BaseExpandableAdapter mBaseExpandableAdapter = null;
    private ExpandableListView mListView;

    SharedPreferences noticeInfo;
    SharedPreferences.Editor editor;

    private Switch aSwitch;
    CompoundButton childButton;
    NetworkService service;
    NetworkService noticeService;
    int m_id;
    int s_id;
    String s_name;
    String mp_name;
    String notice;
    TextView childText;
    MainView view;
    ActionBar actionBar;
    ImageView backImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.layout_alarm_actionbar, null);
        actionBar.setCustomView(mCustomView);



        // 액션바에 백그라운드 이미지를 아래처럼 입힐 수 있습니다. (drawable 폴더에 img_action_background.png 파일이 있어야 겠죠?)
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_topbar));
        //actionBar.

        backImage = (ImageView)actionBar.getCustomView().findViewById(R.id.notice_back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeDrawerActivity.class));
            }
        });

        service = ApplicationController.getInstance().getNetworkService();


        mListView = (ExpandableListView)findViewById(R.id.elv_list);
        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<ChildStateObject>>();


        Log.d("ash3734", String.valueOf(ApplicationController.getInstance().getM_id()));

        Call<NoticeResult> getDetailData = service.getNoticeData(ApplicationController.getInstance().getM_id());
        getDetailData.enqueue(new Callback<NoticeResult>() {

            boolean state;

            @Override
            public void onResponse(Call<NoticeResult> call, Response<NoticeResult> response) {

                if(response.isSuccessful()){

                    Log.d("???","???");
                    for (NoticeData data:response.body().result) {
                        if (!mGroupList.contains(data.name)) {
                            mGroupList.add(data.name);
                        }
                    }


                    for(int i=0;i<mGroupList.size();i++){
                        ArrayList<ChildStateObject> mChildListContent = new ArrayList<ChildStateObject>();
                        for(int j=0;j<response.body().result.size();j++){
                            if(mGroupList.get(i).equals(response.body().result.get(j).name)){
                                if(response.body().result.get(j).mp_name.equals("쇼! 챔피언")&&response.body().result.get(j).notice.equals("t")){
                                    mChildListContent.add(new ChildStateObject("쇼! 챔피언",true));
                                }
                                else if(response.body().result.get(j).mp_name.equals("쇼! 챔피언")&&response.body().result.get(j).notice.equals("f")){
                                    mChildListContent.add(new ChildStateObject("쇼! 챔피언",false));
                                }
                                else if(response.body().result.get(j).mp_name.equals("엠카운트다운")&&response.body().result.get(j).notice.equals("t")){
                                    mChildListContent.add(new ChildStateObject("엠카운트다운",true));
                                }
                                else if(response.body().result.get(j).mp_name.equals("엠카운트다운")&&response.body().result.get(j).notice.equals("f")){
                                    mChildListContent.add(new ChildStateObject("엠카운트다운",false));
                                }
                                else if(response.body().result.get(j).mp_name.equals("인기가요")&&response.body().result.get(j).notice.equals("t")){
                                    mChildListContent.add(new ChildStateObject("인기가요",true));
                                }
                                else if(response.body().result.get(j).mp_name.equals("인기가요")&&response.body().result.get(j).notice.equals("f")){
                                    mChildListContent.add(new ChildStateObject("인기가요",false));
                                }
                            }
                        }
                        mChildList.add(mChildListContent);
                    }
                }
                mBaseExpandableAdapter.setAdapter(mGroupList,mChildList);
            }
            @Override
            public void onFailure(Call<NoticeResult> call, Throwable t) {

            }
        });

        Log.v("GroupCount", String.valueOf(mChildList.size()));


        mBaseExpandableAdapter = new BaseExpandableAdapter(this, mGroupList, mChildList,this);



        mListView.setAdapter(mBaseExpandableAdapter);



        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });


        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {


            }


        });


    }


    @Override
    public void updateStateCheck(String sname, String voteName , boolean state) {

        int groupIndex = 0;
        int childIndex = 0;

        for(int i = 0; i< mGroupList.size();i++)
        {
            if(mGroupList.get(i).equals(sname)){
                groupIndex = i;
                break;
            }
        }

        for(int j = 0 ; j<mChildList.get(groupIndex).size();j++){

            if(mChildList.get(groupIndex).get(j).name.equals(voteName))
            {
                childIndex = j;
                mChildList.get(groupIndex).get(j).state = state;
                mBaseExpandableAdapter.notifyDataSetChanged();
                break;
            }
        }


        if(state)
            requestName(sname,voteName, "t");
        else
            requestName(sname,voteName, "f");

    }

    @Override
    public void requestName(String sname,String name,String notice) {

        Log.i("myTag", String.valueOf(m_id));
        Log.i("myTag", String.valueOf(sname));
        Log.i("myTag", String.valueOf(name));
        Log.i("myTag", String.valueOf(notice));

        retrofit2.Call<RegisterResult3> requestRegister = service.requestRegister2(new GetObject(ApplicationController.getInstance().getM_id(),sname,name,notice));
        requestRegister.enqueue(new Callback<RegisterResult3>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterResult3> call, Response<RegisterResult3> response) {
                if(response.isSuccessful()){
                    if(response.body().result.equals("create")){
                        Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(retrofit2.Call<RegisterResult3> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
