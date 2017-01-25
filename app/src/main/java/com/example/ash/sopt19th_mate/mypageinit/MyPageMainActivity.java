package com.example.ash.sopt19th_mate.mypageinit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.login.MainActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;
import com.example.ash.sopt19th_mate.setsinger.SetSingerActivity;
import com.facebook.login.LoginManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageMainActivity extends AppCompatActivity {
    Button button;
    //String user_id;
    String nickname;
    String email;
    String profile_name = "";
    String kind_sns;
    NetworkService service;
    Intent intent_setSinger;
    EditText pre_nickname;
    ImageButton addImgBtn;
    Button logout;
    Uri data;
    MultipartBody.Part body;
    byte[] profile_img;
    //RequestBody nickname;
    SharedPreferences.Editor editor;
    SharedPreferences loginState;
    ApplicationController applicationController;
    ImageView imageViewComplite;
    final int REQ_CODE_SELECT_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_main);
        Bundle extras = getIntent().getExtras();
        imageViewComplite = (ImageView) findViewById(R.id.mypageMain_Next_ImageView_Id);
        addImgBtn = (ImageButton) findViewById(R.id.addImgBtn);
        logout = (Button) findViewById(R.id.Logout);
        pre_nickname = (EditText) findViewById(R.id.nickname);


        email = extras.getString("email");
        kind_sns = extras.getString("kind_sns");
        //e_mail = "aaa";
        //kind_sns = "bbb";
        service = ApplicationController.getInstance().getNetworkService();

        //button = (Button)findViewById(R.id.set_singer);
        intent_setSinger = new Intent(getApplicationContext(), SetSingerActivity.class);

        //nickname = pre_nickname.getText().toString();
        loginState = getSharedPreferences("loginState", MODE_PRIVATE);
        editor = loginState.edit();

        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사진 갤러리 호출
                // 아래의 코드는 스마트폰의 앨범에서 이미지를 가져오는 부분입니다.
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginState만 false로 바꾸고 나머진 냅둬야 함.
                editor.clear();
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
        imageViewComplite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //data = data2.getData();
        //data2 = data2.getData();
        //Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.v("result", "result_well");
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());

                    // 서버에 보낼 imgUrl
                    profile_name = name_Str;

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView) findViewById(R.id.imageView1);

                    RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), image_bitmap);
                    bitmapDrawable.setCornerRadius(Math.max(image_bitmap.getWidth(), image_bitmap.getHeight()) / 2.0f);
                    bitmapDrawable.setAntiAlias(true);

                    // 배치해놓은 imageview에 동그랗게 set!!!
                    image.setImageDrawable(bitmapDrawable);
                    //image.setI
                    //배치해놓은 ImageView에 set
                    //image.setImageBitmap(image_bitmap);
                    this.data = data.getData();
                    //.makeText(getBaseContext(), "name_Str : " + name_Str, Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 선택된 이미지 파일명 가져오기
     */
    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        profile_name = imgPath;

        return imgName;
    }
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
*/

    public void goNext() {


        nickname = pre_nickname.getText().toString();

        if (nickname.length() == 0) {
            Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {

            if (profile_name == "") {
                body = null;
                Toast.makeText(getApplicationContext(), "이미지를 첨부해주세요.", Toast.LENGTH_SHORT).show();
            } else {

                /**
                 * 비트맵 관련한 자료는 아래의 링크에서 참고
                 * http://mainia.tistory.com/468
                 */
                /**
                 * 아래의 부분은 이미지 리사이징하는 부분입니다
                 * 왜?? 이미지를 리사이징해서 보낼까요?
                 * 안드로이드는 기본적으로 JVM Heap Memory가 얼마되지 않습니다.
                 * 구글에서는 최소 16MByte로 정하고 있으나, 제조사 별로 디바이스별로 Heap영역의 크기는 다르게 정하여 사용하고 있습니다.
                 * 또한, 이미지를 서버에 업로드할 때 이미지크기가 크면 그만큼 시간이 걸리고 데이터 소모가 되겠죠!
                 */
                BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

                InputStream in = null; // here, you need to get your context.
                try {
                    in = getContentResolver().openInputStream(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos); // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ),

                profile_img = baos.toByteArray();


                Call<MyPageResult> requestRegister = service.requestRegister(nickname);
                requestRegister.enqueue(new Callback<MyPageResult>() {
                    @Override
                    public void onResponse(Call<MyPageResult> call, Response<MyPageResult> response) {
                        if (response.body().result.equals("true")) {
                            Toast.makeText(getApplicationContext(), "중복", Toast.LENGTH_SHORT).show();
                            //if(response.body().nickname))
                        } else {
                            Toast.makeText(getApplicationContext(), "등록성공", Toast.LENGTH_SHORT).show();
                            //이메일, 프로필, 닉네임, sns유형 넘겨 줌.
//                            Log.v("e_mail", email);
//                            Log.v("profile_name", profile_name);
//                            Log.v("nickname", nickname);
//                            Log.v("kind_sns", kind_sns);
//                            Log.v("Uri", data.toString());

                            intent_setSinger.putExtra("e_mail", email);
                            intent_setSinger.putExtra("profile_name", profile_name);
                            intent_setSinger.putExtra("nickname", nickname);
                            intent_setSinger.putExtra("kind_sns", kind_sns);
                            intent_setSinger.putExtra("Uri", data);
                            //intent_setSinger.put
                            ApplicationController.getInstance().setData(data);

                            startActivity(intent_setSinger);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPageResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "등록실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.completeBtn) {

            nickname = pre_nickname.getText().toString();

            if (nickname.length() == 0) {
                Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {

                if (profile_name == "") {
                    body = null;
                    Toast.makeText(getApplicationContext(), "이미지를 첨부해주세요.", Toast.LENGTH_SHORT).show();
                } else {

                    /**
                     * 비트맵 관련한 자료는 아래의 링크에서 참고
                     * http://mainia.tistory.com/468
                     */
                    /**
                     * 아래의 부분은 이미지 리사이징하는 부분입니다
                     * 왜?? 이미지를 리사이징해서 보낼까요?
                     * 안드로이드는 기본적으로 JVM Heap Memory가 얼마되지 않습니다.
                     * 구글에서는 최소 16MByte로 정하고 있으나, 제조사 별로 디바이스별로 Heap영역의 크기는 다르게 정하여 사용하고 있습니다.
                     * 또한, 이미지를 서버에 업로드할 때 이미지크기가 크면 그만큼 시간이 걸리고 데이터 소모가 되겠죠!
                     */
                    BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

                    InputStream in = null; // here, you need to get your context.
                    try {
                        in = getContentResolver().openInputStream(data);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos); // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ),

                    profile_img = baos.toByteArray();


                    Call<MyPageResult> requestRegister = service.requestRegister(nickname);
                    requestRegister.enqueue(new Callback<MyPageResult>() {
                        @Override
                        public void onResponse(Call<MyPageResult> call, Response<MyPageResult> response) {
                            if (response.body().result.equals("true")) {
                                Toast.makeText(getApplicationContext(), "중복", Toast.LENGTH_SHORT).show();
                                //if(response.body().nickname))
                            } else {
                                Toast.makeText(getApplicationContext(), "등록성공", Toast.LENGTH_SHORT).show();
                                //이메일, 프로필, 닉네임, sns유형 넘겨 줌.
//                            Log.v("e_mail", email);
//                            Log.v("profile_name", profile_name);
//                            Log.v("nickname", nickname);
//                            Log.v("kind_sns", kind_sns);
//                            Log.v("Uri", data.toString());

                                intent_setSinger.putExtra("e_mail", email);
                                intent_setSinger.putExtra("profile_name", profile_name);
                                intent_setSinger.putExtra("nickname", nickname);
                                intent_setSinger.putExtra("kind_sns", kind_sns);
                                intent_setSinger.putExtra("Uri", data);
                                //intent_setSinger.put
                                ApplicationController.getInstance().setData(data);

                                startActivity(intent_setSinger);
                            }
                        }

                        @Override
                        public void onFailure(Call<MyPageResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "등록실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}