package com.example.ash.sopt19th_mate.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ash.sopt19th_mate.R;

public class OutActivity extends AppCompatActivity {

    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);

        deleteBtn = (Button)findViewById(R.id.delete);

        // 탈퇴하기 버튼 눌렀을시
        Button.OnClickListener deleteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice();
            }
        };
        deleteBtn.setOnClickListener(deleteClickListener);
    }

    public void notice(){
        Toast toast = Toast.makeText(this, "서비스 준비중입니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
