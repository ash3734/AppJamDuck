package com.example.ash.sopt19th_mate.board;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.network.NetworkService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ImageView selectImage;
    ImageView enroll;
    TextView textView;

    EditText content;
    final int REQ_CODE_SELECT_IMAGE=100;
    Uri data;
    NetworkService service;

    MultipartBody.Part body;
    int m_id;
    RequestBody b_time;
    RequestBody b_content;
    String imgUrl = "";
    int TextNum = 0;
    LinearLayout linearLayout;
    //long b_time;
    // String b_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        content = (EditText)findViewById(R.id.content);
        service = ApplicationController.getInstance().getNetworkService();
        textView = (TextView)findViewById(R.id.textView2);
        linearLayout = (LinearLayout)findViewById(R.id.layout1);

        selectImage = (ImageView)findViewById(R.id.select);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextNum = s.length();
                textView.setText(String.valueOf(TextNum));
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        enroll = (ImageView)findViewById(R.id.enroll);
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_id = 2;
                //ApplicationController.getInstance().getM_id();
                // 현재시간을 msec 으로 구한다.
                long now = System.currentTimeMillis();
                // 현재시간을 date 변수에 저장한다.
                Date date = new Date(now);
                // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
                SimpleDateFormat sdfNow = new SimpleDateFormat("dd:HH:mm");
                String formatDate = sdfNow.format(date);

                Log.d("ssibal",formatDate);
                b_content = RequestBody.create(MediaType.parse("multipart/form-data"), content.getText().toString());
                b_time = RequestBody.create(MediaType.parse("multipart/form-data"), formatDate);
                //b_content = content.getText().toString();

                if(imgUrl=="")
                {
                    body = null;
                }
                else
                {
                    BitmapFactory.Options options = new BitmapFactory.Options(); //사용자가 보기에 불편하지 않을 정도로 resizing 해준다
//                        options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

                    InputStream in = null; // here, you need to get your context.
                    try {
                        in = getContentResolver().openInputStream(data);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //사용자가 보기에 불편하지 않을 정도로 resizing 해준다(효율을위해!) 카카오톡으로 사진보내면 깨지는 이유!
                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos); // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ),

                    RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

                    File photo = new File(data.toString()); // 그저 블러온 파일의 이름을 알아내려고 사용.
                    // MultipartBody.Part is used to send also the actual file name
                    //이미지 이름을 서버로 보낼 때에에는 아무렇게나 보내줘도된다! 서버l에서 자동변환된다 (보안의문제)
                    body = MultipartBody.Part.createFormData("img", photo.getName(), photoBody);
                    Log.v("body", body.toString());
                }
                //빅뱅 , 로그인유저
                int s_id = ApplicationController.getInstance().getNumberSingerSet().get(ApplicationController.myMainSinger);
                m_id = ApplicationController.getInstance().getM_id();
                Call<RegisterResult2> requestRegisterBoard = service.requestRegisterBoard(s_id,m_id,b_content,b_time,body);
                //요청할 변수들은 저렇게 NetwordService에서 맞춰준 것처럼 해줌.
                requestRegisterBoard.enqueue(new Callback<RegisterResult2>() {
                    @Override
                    public void onResponse(Call<RegisterResult2> call, Response<RegisterResult2> response) {
                        if(response.body().result.equals("success")){
                            Toast.makeText(getApplicationContext(),"등록성공",Toast.LENGTH_SHORT).show();
                            finish();
                            //mProgressDialog.dismiss();
                            //만일 무언가를 받아온다면 response.body를 통해 받아옴.
                        }
                        else
                            Log.v("no","no");
                    }
                    @Override
                    public void onFailure(Call<RegisterResult2> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"등록실패",Toast.LENGTH_SHORT).show();
                        //mProgressDialog.dismiss();
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //data = data2.getData();
        //data2 = data2.getData();
        Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                Log.v("result", "result_well");
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());

                    // 서버에 보낼 imgUrl
                    //profile_name = name_Str;

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView)findViewById(R.id.boardPic);

//                    RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), image_bitmap);
//                    bitmapDrawable.setCornerRadius(Math.max(image_bitmap.getWidth(), image_bitmap.getHeight()) / 2.0f);
//                    bitmapDrawable.setAntiAlias(true);
//
//                    // 배치해놓은 imageview에 동그랗게 set!!!
//                    image.setImageDrawable(bitmapDrawable);
                    //image.setI
                    //배치해놓은 ImageView에 set
                    image.setImageBitmap(image_bitmap);
                    this.data = data.getData();
                    Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
                imgUrl = "";
        }
    }

    /**
     * 선택된 이미지 파일명 가져오기
     */
    public String getImageNameToUri(Uri data){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        //profile_name = imgPath;
        imgUrl = imgPath;
        return imgName;
    }
}
