package com.example.ash.sopt19th_mate.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.setsinger.SetSingerActivity;

public class MyPageMainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_main);

        button = (Button)findViewById(R.id.set_singer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SetSingerActivity.class));
            }
        });
    }
}
