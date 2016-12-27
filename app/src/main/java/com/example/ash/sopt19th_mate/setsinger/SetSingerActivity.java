package com.example.ash.sopt19th_mate.setsinger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ash.sopt19th_mate.HomeActivity;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.dialog.CustomDialogActivity;

import java.util.ArrayList;

public class SetSingerActivity extends AppCompatActivity {

    // Button button;
    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;
    ArrayList<ItemData> dataSet;
    CustomDialogActivity customDialogActivity;
    Activity activity;
    Context context;
    int itemPosition;
    //이 메소드에 view가 전달되면 이 아이템이 몇 번째인지 알 수 있다.

    int tempSinger;
    String tempName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_singer);


        dataSet = new ArrayList<ItemData>();

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataSet.add(new ItemData(R.drawable.pikapika, "트와이스", R.drawable.pikapika));
        dataSet.add(new ItemData(R.drawable.pikapika, "아이오아이", R.drawable.pikapika));
        dataSet.add(new ItemData(R.drawable.pikapika, "여자친구", R.drawable.pikapika));
        dataSet.add(new ItemData(R.drawable.pikapika, "에이핑크", R.drawable.pikapika));
        dataSet.add(new ItemData(R.drawable.pikapika, "포켓몬스터", R.drawable.pikapika));
        dataSet.add(new ItemData(R.drawable.pikapika, "피카츄", R.drawable.pikapika));

        SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent);
        recyclerView.setAdapter(adapter);
        activity = this;
        context = this;

    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            itemPosition = recyclerView.getChildPosition(v);
            //이 메소드에 view가 전달되면 이 아이템이 몇 번째인지 알 수 있다.

            tempSinger = dataSet.get(itemPosition).singer;
            tempName = dataSet.get(itemPosition).name;
            int tempMost = dataSet.get(itemPosition).most;


            dataSet.remove(itemPosition);
            dataSet.add(itemPosition, new ItemData(tempSinger, tempName, R.drawable.ic_hobby_mypage));

            SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent);
            recyclerView.setAdapter(adapter);
            String dialogtitle = tempName + "를 최애 가수로\n설정하시겠습니까?";
            String dialogcontext = "앱을 켤 때 가장 먼저 최대 가수의\n투표 목록이 보이게 됩니다.";

            customDialogActivity = new CustomDialogActivity(context,
                    dialogtitle, // 제목
                    dialogcontext, // 내용
                    leftListener, // 왼쪽 버튼 이벤트
                    rightListener); // 오른쪽 버튼 이벤트
            customDialogActivity.show();

        }
        private View.OnClickListener leftListener = new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                customDialogActivity.dismiss();
            }
        };

        private View.OnClickListener rightListener = new View.OnClickListener() {
            public void onClick(View v) {
                dataSet.remove(itemPosition);
                dataSet.add(itemPosition, new ItemData(tempSinger, tempName, R.drawable.pikapika));

                SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent);
                recyclerView.setAdapter(adapter);
                customDialogActivity.dismiss();
               // mCustomDialog.
            }
        };
    };
}
