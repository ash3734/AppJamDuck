package com.example.ash.sopt19th_mate.setsinger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.allsingerinfo.InfoList;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.dialog.CustomDialogActivity;
import com.example.ash.sopt19th_mate.home.HomeDrawerActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetSingerActivity extends AppCompatActivity {

    // Button button;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;

    LinearLayoutManager linearLayoutManager;

    RequestManager requestManager;

    ArrayList<ItemData> dataSet;
    ArrayList<ItemData> dataSet2;
    ArrayList<SingerList> dataSet3;

    CustomDialogActivity customDialogActivity;
    Activity activity;
    Context context;
    EditText editText;
    int itemPosition;
    //이 메소드에 view가 전달되면 이 아이템이 몇 번째인지 알 수 있다.
    String tempSinger;
    String tempName;
    String profile_name;
    boolean onSearch = false;
    SingerAdapter adapter;

    NetworkService service;
    NetworkService service2;
   // Intent get_intent;
    Intent intent_Home;

    RequestBody email;
    RequestBody nickname;
    RequestBody kind_sns;
    RequestBody name;
    RequestBody s_id;
    MultipartBody.Part profile_img;

    String pre_email;
    String pre_nickname;
    //byte[] profile_img;
    String pre_kind_sns;
    String pre_name;
    Uri data;
    ImageView button;

    ApplicationController applicationController;
    ArrayList<InfoList> InfoData;
    SharedPreferences.Editor editor;
    SharedPreferences loginState;


    boolean setClear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_singer);
        loginState = getSharedPreferences("loginState", MODE_PRIVATE);
        editor = loginState.edit();
        service = ApplicationController.getInstance().getNetworkService();
        service2 = ApplicationController.getInstance().getNetworkService();
        button = (ImageView)findViewById(R.id.setSingerFirst_Next_ImageView_Id);

        requestManager = Glide.with(this);

        dataSet = new ArrayList<ItemData>();
        dataSet2 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Bundle extras = getIntent().getExtras();

        intent_Home = new Intent(getApplicationContext(), HomeDrawerActivity.class);
        pre_email = extras.getString("e_mail");
       // Log.v("e_mail", e_mail);
        profile_name = extras.getString("profile_name");
        pre_nickname = extras.getString("nickname");
        pre_kind_sns = extras.getString("kind_sns");
        data = (Uri)extras.get("Uri");
        //extras.get
        //data = extras.getstring
        Log.v("e_mail", pre_email);
        Log.v("profile_name", profile_name);
        Log.v("nickname", pre_nickname);
        Log.v("kind_sns", pre_kind_sns);
        Log.v("data", data.toString());


        BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream in = null; // here, you need to get your context.
        try {
            in = getContentResolver().openInputStream(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos); // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ),


        File photo = new File(data.toString()); // 그저 블러온 파일의 이름을 알아내려고 사용.
        RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

        // MultipartBody.Part is used to send also the actual file name
        //이미지 이름을 서버로 보낼 때에에는 아무렇게나 보내줘도된다! 서버에서 자동변환된다 (보안의문제)
        profile_img = MultipartBody.Part.createFormData("profile_img", "jpg", photoBody);
        //File file  = File

        Log.v("profile_img", profile_img.toString());

        //TODO : 데이터 목록

        //TODO : 검색을 위해.....뭐였더라. 서버에서 데이터 불러오기.


        Call<SingerResult> singerResult = service.singerResult();
        singerResult.enqueue(new Callback<SingerResult>() {
            @Override
            public void onResponse(Call<SingerResult> call, Response<SingerResult> response) {
                if(response.isSuccessful())
                {
                    dataSet3 = response.body().result;
                    for(int i = 0; i<dataSet3.size(); i++)
                    {
                        dataSet.add(i, new ItemData(dataSet3.get(i).main_img, dataSet3.get(i).name, R.drawable.popup_heart));
                        dataSet2.add(i, new ItemData(dataSet3.get(i).main_img, dataSet3.get(i).name, R.drawable.popup_heart));
                        //dataSet2.add
                        ApplicationController.getInstance().getNumberSingerSet().put(dataSet3.get(i).getName(), dataSet3.get(i).getS_id());
                        //이건 검색.
                        //추천 가수 목록은 아직 없음.
                    }
                }
            }
            @Override
            public void onFailure(Call<SingerResult> call, Throwable t) {
            }
        });
        //requestManager.

        activity = this;
        context = this;
        adapter = new SingerAdapter(dataSet, dataSet2, recyclerView, requestManager, linearLayoutManager, context, SetSingerActivity.this);

        recyclerView.setAdapter(adapter);

        // = RequestBody.create(MediaType.parse("multipart/form-data"), pre_kind_sns);

        editText = (EditText)findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //button.setText("검색 결과");
                adapter.search = true;
                onSearch = true;
                String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                if(text.length() == 0) {
                    //button.setText("추천 가수");
                    adapter.search = false;
                    onSearch = false;
                }
                adapter.filter(text);
            }
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void SetComplete(final int s_id)
    {
        kind_sns = RequestBody.create(MediaType.parse("multipart/form-data"), pre_kind_sns);
        email = RequestBody.create(MediaType.parse("multipart/form-data"), pre_email);
        nickname = RequestBody.create(MediaType.parse("multipart/form-data"), pre_nickname);
        //s_id = RequestBody.create(MediaFormat.)
        //s_id = RequestBody.create(MediaType.parse("multipart/form-data"));
        //RequestBody.
        Log.v("여기", "여기까진 옴.");
        Log.v("nickname", pre_nickname);
        //Log.v("pre_name", pre_name);
        Log.v("넘버", String.valueOf(s_id));

        Log.v("kind_sns", pre_kind_sns);
        Log.v("email", pre_email);

        Log.v("profile_img", profile_img.toString());
        //name = RequestBody.create(MediaType.parse("multipart/form-data"), pre_name);
        Log.v("여기도", "여기까지도 옴.");
        //RequestBody.

        Call<SetRegisterResult> requestFinalRegister = service.requestFinalRegister(profile_img, email, nickname, kind_sns, s_id);
        requestFinalRegister.enqueue(new Callback<SetRegisterResult>() {
            @Override
            public void onResponse(Call<SetRegisterResult> call, Response<SetRegisterResult> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "등록성공", Toast.LENGTH_SHORT).show();
                    Log.v("등록 성공", "등록 성공");
                    //applicationController.setEmail(email.toString());
                    //applicationController.setName(name.toString());
                    //applicationController.setKind_sns(kind_sns.toString());
                    ApplicationController.getInstance().setM_id(response.body().member.get(0).m_id);
                    ApplicationController.getInstance().setEmail(response.body().member.get(0).email);
                    ApplicationController.getInstance().setNickname(response.body().member.get(0).nickname);
                    ApplicationController.getInstance().setProfile_name(response.body().member.get(0).profile_img);
                    //TODO : name을 받으면 그거에 맞는 아이디를 테이블에서 id를 넘겨 줌.
                    //ApplicationController.getInstance().setS_id(s_id);//s_id는 메인에 띄울 가수의 아이디.
                    //ApplicationController.getInstance().setMain_id(s_id);
                    //s_id는 메인(홈화면)에 띄울 가수의 아이디이고, main_id는 최애 가수의 아이디.
                    //ApplicationController.getInstance().setName(response.body().list.get(0).name);
                    //ApplicationController.getInstance().semo(response.body().list.get(0).is_most);
                    ApplicationController.getInstance().setS_id(response.body().list.get(0).s_id);
                    ApplicationController.getInstance().setName(response.body().list.get(0).name);
                    ApplicationController.getInstance().setS_name(response.body().list.get(0).name);

                    editor.putBoolean("loginState", true);
                    editor.putString("e_mail", pre_email);
                    editor.putString("kind_sns", pre_kind_sns);
                    editor.commit();
                    //applicationController.
                    startActivity(new Intent(getApplicationContext(), HomeDrawerActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "등록실패1", Toast.LENGTH_SHORT).show();
                    Log.v("등록 실패1", "등록 실패1");
                    //TODO : member객체에
                }
            }
            @Override
            public void onFailure(Call<SetRegisterResult> call, Throwable t) {
                Log.v("profile_img", profile_img.toString());
                Toast.makeText(getApplicationContext(), "등록실패2", Toast.LENGTH_SHORT).show();
                Log.v("등록 실패2", "등록 실패2");
            }
        });
    }
}
