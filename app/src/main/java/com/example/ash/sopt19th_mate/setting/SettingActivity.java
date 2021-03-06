package com.example.ash.sopt19th_mate.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ash.sopt19th_mate.R;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    ArrayList<ListViewItem> datas= new ArrayList<ListViewItem>();
    ListView listview;
    Button logoutBtn;
    Intent intent;
    SharedPreferences.Editor editor;
    SharedPreferences logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        datas.add( new ListViewItem("공지사항",R.drawable.alarm_arrow));
        datas.add( new ListViewItem("덕질메이트 앱 정보",R.drawable.alarm_arrow));
        datas.add( new ListViewItem("탈퇴하기",R.drawable.alarm_arrow));

        listview= (ListView)findViewById(R.id.basicListview);
        SettingAdapter adapter= new SettingAdapter( getLayoutInflater() , datas);
        logoutBtn = (Button)findViewById(R.id.logout);
        listview.setAdapter(adapter);
        intent = new Intent(this,OutActivity.class);// 탈퇴 액티비티로 넘어감

        // sharedpreferences 설정
        logout = getSharedPreferences("loginState",MODE_PRIVATE);
        editor = logout.edit();

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0:
                        Log.d("tag","들어왔음ㅇㅁㄻㅇㄻㅇㄻㅇㄹ!~!~!~!");
                        notice();
                        break;
                    case 1:
                        notice();
                        break;
                    case 2:
                        startActivity(intent);
                        break;
                }
            }
        };
        listview.setOnItemClickListener(listener);


        // 로그아웃 버튼 눌렀을시
        Button.OnClickListener logoutClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*editor.putBoolean("loginState",false);
                view.setBackgroundResource(R.drawable.logout_click);*/
                Toast toast = Toast.makeText(getApplicationContext(), "로그아웃을 준비중입니다.", Toast.LENGTH_SHORT);
            }
        };
        logoutBtn.setOnClickListener(logoutClickListener);

    }

    public void notice(){
        Toast toast = Toast.makeText(this, "서비스 준비중입니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
