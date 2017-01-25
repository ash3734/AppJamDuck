package com.example.ash.sopt19th_mate.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ash.sopt19th_mate.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class AndroidTwitterExampleInit extends AppCompatActivity {

    private static final String TWITTER_KEY = "RWkVc71OOHGbQZY3p8k4dtCCt ";
    private static final String TWITTER_SECRET = "7DlJCOKaC1fmvqYdJAkequAPFdstuhqxZINWErmYvv4MDFUveh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_twitter_example_init);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        startLoginActivity();
    }
//TODO : sket8993@naver.com
    private void startLoginActivity() {
        Log.v("여기 실행", "여기 실행");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
