package com.example.ash.sopt19th_mate.newsstand.voteTab;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.dialog.CustomDialogActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ash on 2016-12-29.
 */

public class PageFragment extends Fragment {

    private ArrayList<VoteListData> mDatas;
    private int position;
    private ImageView imageView;
    private TextView textViewProgramName;
    private TextView textViewPeriod;
    private TextView textViewDDay;
    private TextView textViewPeriodDetail;
    private TextView textViewVoteMethod;
    private ImageView imageView2;
    private String programName;
    //private Bitmap bitmapImage;
    CustomDialogActivity customDialogActivity;
    //Context context;
    //BitmapFactory.Options options;

    public static PageFragment create(ArrayList<VoteListData> datas,int position) {

        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putSerializable("datas", datas);
        args.putInt("position",position);
        fragment.setArguments(args);
        //context = this;
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatas = (ArrayList<VoteListData>) getArguments().getSerializable("datas");
        position = getArguments().getInt("position");
        String str = mDatas.get(position).mp_img;
        Glide.with(this).load(str).into(imageView);
        textViewProgramName.setText(mDatas.get(position).mp_name);
        textViewPeriod.setText(mDatas.get(position).what_week);
        textViewPeriodDetail.setText(mDatas.get(position).fav_period);
        textViewDDay.setText("D-1");
        textViewVoteMethod.setText(mDatas.get(position).vote_method);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.dialog_fragment, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.dialog_imageview);
        textViewProgramName = (TextView) rootView.findViewById(R.id.dialog_textview_program);
        textViewPeriod = (TextView) rootView.findViewById(R.id.dialog_textview_week);
        textViewDDay = (TextView) rootView.findViewById(R.id.dialog_textview_dday);
        textViewPeriodDetail = (TextView) rootView.findViewById(R.id.dialog_textview_voteday);
        textViewVoteMethod = (TextView) rootView.findViewById(R.id.dialog_textview_voteway);
        imageView2 = (ImageView) rootView.findViewById(R.id.dialog_button_govote);
        /*
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        bitmapImage = BitmapFactory.decodeResource(rootView.getResources(), image, options);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
        imageView.setImageBitmap(bitmapImage);*/

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programName = textViewProgramName.getText().toString();
                Log.v("여기", textViewProgramName.getText().toString());
                if(programName.equals("인기가요"))
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-3643-9714"));
                    startActivity(intent);
                    //전화연결
                }
                else if(programName.equals("엠카운트다운"))
                {
                    Log.v(programName, programName);
                    String dialogtitle = "확인을 누르면 문자로 \n 취소를 누르면 링크로 연결됩니다.";
                    String dialogcontext = " ";

                    customDialogActivity = new CustomDialogActivity(ApplicationController.getInstance().getContext(),
                            dialogtitle, // 제목
                            dialogcontext, // 내용
                            leftListener, // 왼쪽 버튼 이벤트
                            rightListener); // 오른쪽 버튼 이벤트
                    customDialogActivity.show();
                }
                else
                {
                    openApp();

                }
            }
        });
        return rootView;
    }

    @JavascriptInterface
    public void openApp()
    {
        boolean isExist = false;

        PackageManager packageManager = ApplicationController.getInstance().getContext().getPackageManager();
        List<ResolveInfo> mApps;
        Intent mIntent = new Intent(Intent.ACTION_MAIN, null);
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = packageManager.queryIntentActivities(mIntent, 0);

        try {
            for (int i = 0; i < mApps.size(); i++) {
                if(mApps.get(i).activityInfo.packageName.startsWith("com.nwz.ichampclient")){
                    isExist = true;
                    break;
                }
            }
        } catch (Exception e) {
            isExist = false;
        }
        // 설치되어 있으면
        if(isExist){
            Intent intent = ApplicationController.getInstance().getContext().getPackageManager().getLaunchIntentForPackage("com.nwz.ichampclient");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ApplicationController.getInstance().getContext().startActivity(intent);
        }else{
            Intent marketLaunch = new Intent(Intent.ACTION_VIEW);
            marketLaunch.setData(Uri.parse("market://details?id=com.nwz.ichampclient"));
            ApplicationController.getInstance().getContext().startActivity(marketLaunch);
        }
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            String phoneNumber = "01036439714";
            String message = "치킨 먹자";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            customDialogActivity.dismiss();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            // SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent, dataSet2);
            Uri uri = Uri.parse("http://mcountdown.mnet.interest.me/vote/preVote");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            customDialogActivity.dismiss();
            // mCustomDialog.
        }
    };
}
