package com.example.ash.sopt19th_mate.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.allsingerinfo.InfoList;
import com.example.ash.sopt19th_mate.allsingerinfo.InfoResult;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.dialog.CustomDialogActivity;
import com.example.ash.sopt19th_mate.home.HomeDrawerActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageActivity extends AppCompatActivity {

    NetworkService service;
    RequestManager requestManager;
    ArrayList<MyPageItemData> dataSet;
    ArrayList<MyPageItemData> dataSet2;
    ArrayList<MyPageSearchList> dataSet3;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MyPageAdapter adapter;
    Activity activity;
    Context context;
    boolean onSearch = false;
    EditText editText;
    int itemPosition;
    String tempSinger;
    String tempName;
    CustomDialogActivity customDialogActivity;
    ImageView backImg;
    ArrayList<InfoList> InfoData;
    int subCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ImageView topbar=(ImageView)findViewById(R.id.mypage_SetSinger_Topbar);
        Glide.with(this).load(R.drawable.main_topbar).into(topbar);
        service = ApplicationController.getInstance().getNetworkService();

        requestManager = Glide.with(this);

        dataSet = new ArrayList<MyPageItemData>();
        dataSet2 = new ArrayList<MyPageItemData>();

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        Call<MyPageSearchResult> searchResult = service.searchResult();
        searchResult.enqueue(new Callback<MyPageSearchResult>() {
            @Override
            public void onResponse(Call<MyPageSearchResult> call, Response<MyPageSearchResult> response) {
                if(response.isSuccessful())
                {
                    dataSet3 = response.body().result;
                    for(int i = 0; i<dataSet3.size(); i++)
                    {
                        dataSet.add(i, new MyPageItemData(dataSet3.get(i).main_img, dataSet3.get(i).name, R.drawable.pikapika, R.drawable.ic_hobby_mypage));
                        dataSet2.add(i, new MyPageItemData(dataSet3.get(i).main_img, dataSet3.get(i).name, R.drawable.pikapika, R.drawable.ic_hobby_mypage));
                        //이건 검색.
                        //추천 가수 목록은 아직 없음.
                    }
                }
            }
            @Override
            public void onFailure(Call<MyPageSearchResult> call, Throwable t) {
            }
        });
        //requestManager.
        //adapter = new MyPageAdapter(clickEvent, dataSet, requestManager);

        activity = this;
        context = this;
        adapter = new MyPageAdapter(dataSet, recyclerView, requestManager, linearLayoutManager, context, MypageActivity.this, dataSet2);

        recyclerView.setAdapter(adapter);
        editText = (EditText)findViewById(R.id.search2);
        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // adapter.search = false;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.search = true;
                onSearch = true;
                String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                if(text.length() == 0) {
                    adapter.search = false;
                    onSearch = false;
                    Log.v("none", "none");
                }
                adapter.filter(text);
            }
            public void afterTextChanged(Editable s) {
            }
        });

        backImg = (ImageView)findViewById(R.id.mypage_BackImage_Id);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chageState();

                Intent intent = new Intent(getApplicationContext(), HomeDrawerActivity.class);
                startActivity(intent);
            }
        });
    }
    public void chageState()
    {
        Call<InfoResult> infoResult = service.infoResult(4);
        infoResult.enqueue(new Callback<InfoResult>() {
            @Override
            public void onResponse(Call<InfoResult> call, Response<InfoResult> response) {
                if(response.isSuccessful())
                    InfoData = response.body().result;
                ApplicationController.getInstance().setTotalSingerCount(InfoData.size());
                for(int i = 0; i<InfoData.size(); i++)
                {
                    if(InfoData.get(i).is_most.equals("t"))
                    {
                        Log.v("최애", InfoData.get(i).name);
                        if(i!=0)
                            InfoData = InfoDataSwap(InfoData, i);
                    }
                    else
                    {
                        Log.v("차애", InfoData.get(i).name);
                        subCount++;
                    }
                }
                Log.v("성공", "성공");
                ApplicationController.getInstance().setInfoData(InfoData);
                ApplicationController.getInstance().setSubSingerCount(subCount);
            }
            @Override
            public void onFailure(Call<InfoResult> call, Throwable t) {

            }

        });
    }
    public ArrayList<InfoList> InfoDataSwap(ArrayList<InfoList> InfoData, int i)
    {
        String tempName = "";
        int tempIndex = 0;
        String tempMost = "";

        //이 밑은 현재 최애에 관한거. i번째에 대한거 저장.
        tempName = InfoData.get(i).name;
        tempIndex = i;
        tempMost = InfoData.get(i).is_most;

        //i번째 인덱스에 0번째꺼 넣어 줌.(맨 앞을 최애로 맞추기 위함)
        InfoData.get(i).name = InfoData.get(0).name;
        InfoData.get(i).is_most = InfoData.get(0).is_most;

        //0번째에는 i번째에 있던거 넣어줌.
        InfoData.get(0).name = tempName;
        InfoData.get(0).is_most = tempMost;


        return InfoData;
    }


    public void ChangeComplete(final int main_id)
    {
            //TODO : id 저장해야 함.
            //Log.v("name", name);
            int id = 2;
            int id2 = 0;
            final int main = main_id;
            Call<ChangeResult> changeMainSinger = service.changeMainSinger(ApplicationController.getInstance().getM_id(),main_id);//TODO : name은 곧 s_id로 바뀌어야 함. s_id는 따로 테이블을 받을 예정.
            changeMainSinger.enqueue(new Callback<ChangeResult>() {
            @Override
            public void onResponse(Call<ChangeResult> call, Response<ChangeResult> response) {
                if(response.body().result.equals("change")) {
                    ApplicationController.getInstance().setS_id(main);
                    Toast.makeText(getBaseContext(), "변경 되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getBaseContext(), "다시 시도해주세요",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ChangeResult> call, Throwable t) {
            }
        });
    }

    public void AddSubComplete(int s_id)
    {
        //TODO : id 저장해야 함.
        //Log.v("name", name);
        int id = 2;

        if(ApplicationController.getInstance().getSubSingerCount() < 3) {
            Call<AddSubResult> addSubSinger = service.addSubSinger(new AddObject(ApplicationController.getInstance().getM_id(), s_id, "f"));
            addSubSinger.enqueue(new Callback<AddSubResult>() {
                @Override
                public void onResponse(Call<AddSubResult> call, Response<AddSubResult> response) {
                    if (response.body().result.equals("create"))
                        Toast.makeText(getBaseContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                        //ApplicationController.getInstance().setSubSingerCount(ApplicationController.getInstance().getSubSingerCount());
                    else
                        Toast.makeText(getBaseContext(), "추가 실패", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<AddSubResult> call, Throwable t) {
                }
            });
        }
        else
            Toast.makeText(getBaseContext(), "3명 초과", Toast.LENGTH_SHORT).show();
    }

//    public ImageView.OnClickListener clickEvent = new ImageView.OnClickListener() {
//        public void onClick(View v) {
//            if(v == v.findViewById(R.id.img_most2)) {
//                itemPosition = recyclerView.getChildPosition(v);
//
//                //이 메소드에 view가 전달되면 이 아이템이 몇 번째인지 알 수 있다.
//                if (onSearch == false) {
//                    //검색중이 아닐 때는 아무것도 안 보여줌.
//                } else {
//                    tempSinger = dataSet.get(itemPosition).imageURL;
//                    tempName = dataSet.get(itemPosition).name;
//                    int tempMost = dataSet.get(itemPosition).most;
//
//                    dataSet.remove(itemPosition);
//                    dataSet.add(itemPosition, new MyPageItemData(tempSinger, tempName, R.drawable.delicious2, R.drawable.ic_hobby_mypage));
//                }
//                recyclerView.setAdapter(adapter);
//                String dialogtitle = tempName + "를\n 최애 가수로\n변경하시겠습니까?";
//                String dialogcontext = "앱을 켤 때 가장 먼저 최대 가수의\n투표 목록이 보이게 됩니다.";
//
//
//                customDialogActivity = new CustomDialogActivity(context,
//                        dialogtitle, // 제목
//                        dialogcontext, // 내용
//                        leftListener, // 왼쪽 버튼 이벤트
//                        rightListener); // 오른쪽 버튼 이벤트
//                customDialogActivity.show();
//            }
//            else if(v == v.findViewById(R.id.img_add2))
//            {//차애 추가하는 부분
//                //TODO : 차애 추가한 것을 서버로 보내주는 것 필요함.
//
//
//                //추가되면 추가 add, most 이미지 안 보임. 역할도 못함.
//
//
//            }
//        }
//        private View.OnClickListener leftListener = new View.OnClickListener() {
//            //TODO : 여기서부터는 최애에 대한 부분을 다룸.
//            @Override
//            public void onClick(View v) {
//                //TODO : 최애 바뀐 것을 서버로 보내줘야 함.
//
//                customDialogActivity.dismiss();
//            }
//        };
//
//        //이 밑은 취소.
//        private View.OnClickListener rightListener = new View.OnClickListener() {
//            public void onClick(View v) {
//                if(onSearch == false) {
//                    //암것도 없음 검색 중이 아니었으면 특별히 할건 없음.
//                }
//                else
//                {
//                    //검색중이 었다면 원상태로 복구.
//                    dataSet.remove(itemPosition);
//                    dataSet.add(itemPosition, new MyPageItemData(tempSinger, tempName, R.drawable.pikapika, R.drawable.ic_hobby_mypage));
//                }
//                // SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent, dataSet2);
//                recyclerView.setAdapter(adapter);
//                customDialogActivity.dismiss();
//                // mCustomDialog.
//            }
//        };
//    };

}