package com.example.ash.sopt19th_mate.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.home.HomeDrawerActivity;
import com.example.ash.sopt19th_mate.mypageinit.MyPageMainActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

//import retrofit2.Call;
//import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    LoginButton loginButton;
    String[] userInfo;
    Intent intent, intent_main;
    boolean which_login = true;
    TwitterLoginButton TwitterLoginButton;
    NetworkService service;
    String email;
    String kind_sns;
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences loginState;
    ApplicationController applicationController;

    Button button;
    Button button2;

    TwitterAuthClient twitterAuthClient;


    //RequestBody email;
    //RequestBody kind_sns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        context = this;
        service = ApplicationController.getInstance().getNetworkService();

        callbackManager = CallbackManager.Factory.create();
        //editor = sharedPreferences.edit();
        //loginButton = (LoginButton) findViewById(R.id.login_button);
        //TwitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        intent = new Intent(getApplicationContext(), MyPageMainActivity.class);
        intent_main = new Intent(getApplicationContext(), HomeDrawerActivity.class);
        loginState = getSharedPreferences("loginState", MODE_PRIVATE);
        editor = loginState.edit();

        button = (Button)findViewById(R.id.login);
        button2 = (Button)findViewById(R.id.login2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO : 이 아래는 해당 폰에서 최초로 로그인 했을 때(앱을 처음 깔든, 삭제를 하고 다시 실행하든) 들어오는 부분.
        if (loginState.getBoolean("loginState", false) == false) {
            Log.v("login", "firstLogin");
            //TODO : 로그아웃을 하면 로그인 기록 다 지워야 함. 즉 sharedpreference에 있는 내용 전부 지워야 함.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_login = true;
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                        Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult result) {

                        final GraphRequest graphRequest = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("result", object.toString());
                                Log.v("result1", String.valueOf(object.toString().split(":")));

                                try {
                                    Log.v("email", (String) object.get("email"));
                                    email = (String) object.get("email");
                                    kind_sns = "f";

                                    //TODO : 여기에 쓰인 모든 정보를 sharedpreference에 저장.

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Call<LoginResult2> loginrequestRegister = service.loginrequestRegister(new LoginObject(email, kind_sns));
                                loginrequestRegister.enqueue(new Callback<LoginResult2>() {
                                    @Override
                                    public void onResponse(Call<LoginResult2> call, Response<LoginResult2> response) {
                                            if (response.isSuccessful()) {
                                                //설정 페이지로.
                                                //이게  최초 로그인.
                                                Log.v("Here", "Here");
                                                intent.putExtra("email", email);
                                                intent.putExtra("kind_sns", kind_sns);
                                                //TODO : applicaittionController에 값 넘겨 줌.
                                                // applicationController.na =
                                                //which_login = true;
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                //이제 메인으로
                                                //TODO : 만일 로그아웃을 했고 다시 로긴한거면 이쪽으로.(기존 폰에서!)
//                                                intent_main.putExtra("email", response.body().email);
//                                                intent_main.putExtra("nickname", response.body().nickname);
//                                                intent_main.putExtra("profile_img", response.body().profile_img);
//                                                intent_main.putExtra("m_id", response.body().m_id);

                                                ApplicationController.getInstance().setNickname(response.body().member.get(0).nickname);
                                                ApplicationController.getInstance().setEmail(response.body().member.get(0).email);
                                                ApplicationController.getInstance().setData(Uri.parse(response.body().member.get(0).profile_img));
                                                ApplicationController.getInstance().setM_id(response.body().member.get(0).m_id);

                                                 //Log.v("response2", response.body().result.toString());
                                                //Log.v("TRUE", "Here");
                                                //TODO : 여기서는 메인으로 넘어갑니다.
                                                finish();
                                                startActivity(intent_main);

                                                //startActivity(intent_main);
                                            }
                                    }
                                    @Override
                                    public void onFailure(Call<LoginResult2> call, Throwable t) {
                                        Log.v("fail", "fail");
                                        Toast.makeText(getBaseContext(), "서버 연결 불량",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                        Log.v("Login1", "2");
                    }
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("LoginErr", error.toString());
                    }
                });
            }
        });


//            LoginManager.getInstance().logOut();
//            //LoginManager.getInstance().
//            loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
//            //.pu
//            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//                @Override
//                public void onSuccess(final LoginResult loginResult) {
//                    Log.v("Login1", "1");
//                    GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            //loginResult.getAccessToken().getToken().get
//                            //if(loginResult.getAccessToken()
//                            //loginResult.getAccessToken().;
//                            Log.v("result", object.toString());
//                            Log.v("result1", String.valueOf(object.toString().split(":")));
//                            try {
//                                Log.v("email", (String) object.get("email"));
//
//                                intent.putExtra("email", (String) object.get("email"));
//                                intent.putExtra("facebook", "f");
//
//                                email = (String) object.get("email");
//                                kind_sns = "f";
//                                //email = RequestBody.create(MediaType.parse("multipart/form-data"), pre_email);
//                                //kind_sns = RequestBody.create(MediaType.parse("multipart/form-data"), pre_kind_sns);
//                                //Log.v("kind_sns", kind_sns);
//                                //TODO : 여기에 쓰인 모든 정보를 sharedpreference에 저장.
//                                editor.putBoolean("loginState", true);
//                                editor.putString("e_mail", email);
//                                editor.putString("kind_sns", kind_sns);
//                                editor.commit();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Call<LoginResult2> loginrequestRegister = service.loginrequestRegister(new LoginObject(email, kind_sns));
//                            loginrequestRegister.enqueue(new Callback<LoginResult2>() {
//                                @Override
//                                public void onResponse(Call<LoginResult2> call, Response<LoginResult2> response) {
//                                    if (response.isSuccessful()) {
//                                        if (response.body().result.equals("false")) {
//                                            //설정 페이지로.
//                                            //이게  최초 로그인.
//                                            Log.v("Here", "Here");
//                                           // Log.v("response", response.body().result.toString());
//                                            //TODO : applicaittionController에 값 넘겨 줌.
//                                           // applicationController.na =
//                                            startActivity(intent);
//                                        } else {
//                                            //이제 메인으로
//                                            //TODO : 만일 로그아웃을 했고 다시 로긴한거면 이쪽으로.(기존 폰에서!)
////                                            intent_main.putExtra("e_mail", response.body().result.e_mail);
////                                            intent_main.putExtra("nickname", response.body().result.nickname);
////                                            intent_main.putExtra("profile_img", response.body().result.profile_img);
////                                            intent_main.putExtra("m_id", response.body().result.m_id);
//                                           // Log.v("response2", response.body().result.toString());
//                                            Log.v("TRUE", "Here");
//
//                                            //TODO : 여기서는 메인으로 넘어갑니다.
//                                            startActivity(intent);
//
//                                            //startActivity(intent_main);
//                                        }
//                                    }
//                                }
//                                @Override
//                                public void onFailure(Call<LoginResult2> call, Throwable t) {
//                                    //Toast.makeText(getApplicationContext(),"등록실패",Toast.LENGTH_SHORT).show();
//                                    // mProgressDialog.dismiss();
//                                    Log.v("TT", "TT");
//                                }
//                            });
//                        }
//                    });
//
//                    loginButton.setVisibility(GONE);
//                    Bundle parameters = new Bundle();
//                    parameters.putString("fields", "id,name,email,gender,birthday");
//                    graphRequest.setParameters(parameters);
//                    graphRequest.executeAsync();
//                    Log.v("Login1", "2");
//                }
//
//                @Override
//                public void onCancel() {
//                }
//
//                @Override
//                public void onError(FacebookException error) {
//                    Log.e("LoginErr", error.toString());
//                }
//            });

            //////이 아래는 트위터.
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    which_login = false;
                    twitterAuthClient = new TwitterAuthClient();
                    twitterAuthClient.authorize(MainActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

                        @Override
                        public void success(Result<TwitterSession> result) {
                            TwitterSession session = result.data;
                            String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                            email = String.valueOf(session.getUserId());
                            kind_sns = "t";


                            Call<LoginResult2> loginrequestRegister = service.loginrequestRegister(new LoginObject(email, kind_sns));
                            loginrequestRegister.enqueue(new Callback<LoginResult2>() {
                                @Override
                                public void onResponse(Call<LoginResult2> call, Response<LoginResult2> response) {
                                    if (response.isSuccessful()) {
                                        //설정 페이지로.0
                                        Log.v("틔위러", "트위러");
                                        //.which_login = false;
                                        Log.v("email", email);
                                        intent.putExtra("email", email);
                                        intent.putExtra("kind_sns", kind_sns);
                                        startActivity(intent);
                                    } else {
                                            //이제 메인으로
//                                    intent_main.putExtra("e_mail", response.body().result.e_mail);
//                                    intent_main.putExtra("nickname", response.body().result.nickname);
//                                    intent_main.putExtra("profile_img", response.body().result.profile_img);
//                                    intent_main.putExtra("m_id", response.body().result.m_id);
                                            //startActivity(intent_main);
                                            startActivity(intent);
                                        }
                                    }

                                @Override
                                public void onFailure(Call<LoginResult2> call, Throwable t) {
                                    //Toast.makeText(getApplicationContext(),"등록실패",Toast.LENGTH_SHORT).show();
                                    // mProgressDialog.dismiss();
                                    Toast.makeText(getBaseContext(), "서버 연결 불량",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void failure(TwitterException exception) {

                        }
                    });
                }
                });
        }
        else
        {
            Log.v("login", "alreadyLogin");
            //
            button.setVisibility(GONE);
            button2.setVisibility(GONE);
            //TODO : 여기서부터는 '이 폰'에서 로그인 한 번이라도 한 적 있음.
            //TODO : 대기 화면으로 넘어가는거 하면 좋을 듯.
            email = loginState.getString("e_mail", "");
            kind_sns = loginState.getString("kind_sns", "");
            Log.v("e_mail", email);
            Log.v("kind_sns", kind_sns);
            Call<LoginResult2> loginrequestRegister = service.loginrequestRegister(new LoginObject(email, kind_sns));
            loginrequestRegister.enqueue(new Callback<LoginResult2>() {
                @Override
                public void onResponse(Call<LoginResult2> call, Response<LoginResult2> response) {
                    if (response.isSuccessful()) {
                        //이 폰에서 로긴한 적 있으면 그 값으로 로긴.
                        //이제 메인으로
                        ApplicationController.getInstance().setM_id(response.body().member.get(0).m_id);//아이디 저장
                        ApplicationController.getInstance().setNickname(response.body().member.get(0).nickname);//닉네임 저장
                        ApplicationController.getInstance().setEmail(response.body().member.get(0).email);//이메일 저장
                        ApplicationController.getInstance().setData(Uri.parse(response.body().member.get(0).profile_img));//이미지 저장
                        ApplicationController.getInstance().setKind_sns(response.body().member.get(0).kind_sns);//sns 저장.

                        //Log.v("response.body().member.get(0).m_id)", String.valueOf(response.body().member.get(0).m_id));
                        for(int i = 0; i<response.body().list.size(); i++)
                        {
                            if(response.body().list.get(i).is_most.equals("t")) {
                                ApplicationController.getInstance().setS_id(response.body().list.get(i).s_id);
                                ApplicationController.getInstance().setName(response.body().list.get(i).name);
                                ApplicationController.getInstance().setS_name(response.body().list.get(i).name);
                            }
                        }//
                        //ApplicationController.getInstance().setS_id(response.body().list.get(0).s_id);
                        //로그인 막 했을 때 화면에 띄울 것은 메인 싱어 아이디.
                        Log.v("로그인 한 적 있엉", "로그인 한 적 있엉");

                        startActivity(intent_main);
                    }
                    else
                        Log.v("로그인 한 적 없엉", "로그인 한 적 없엉");
                        finish();
                }
                @Override
                public void onFailure(Call<LoginResult2> call, Throwable t) {
                    Log.v("failure", "failure");
                    Toast.makeText(getBaseContext(), "서버 연결 불량",Toast.LENGTH_SHORT).show();
                }
                });
            }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(which_login) {
            Log.v("페북", "페북");
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.v("트윗", "트윗");
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
    }
}