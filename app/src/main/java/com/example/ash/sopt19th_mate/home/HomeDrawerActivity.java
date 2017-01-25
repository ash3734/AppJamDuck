package com.example.ash.sopt19th_mate.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ash.sopt19th_mate.R;
import com.example.ash.sopt19th_mate.allsingerinfo.InfoList;
import com.example.ash.sopt19th_mate.allsingerinfo.InfoResult;
import com.example.ash.sopt19th_mate.application.ApplicationController;
import com.example.ash.sopt19th_mate.astuetz.PagerSlidingTabStrip;
import com.example.ash.sopt19th_mate.board.BoardActivity;
import com.example.ash.sopt19th_mate.mypage.MyMainActivity;
import com.example.ash.sopt19th_mate.network.NetworkService;
import com.example.ash.sopt19th_mate.newsstand.ScrollTabHolder;
import com.example.ash.sopt19th_mate.newsstand.ScrollTabHolderFragment;
import com.example.ash.sopt19th_mate.newsstand.voteTab.IdGen;
import com.example.ash.sopt19th_mate.newsstand.voteTab.VoteFragment;
import com.example.ash.sopt19th_mate.notice.NoticeActivity;
import com.example.ash.sopt19th_mate.setsinger.SingerList;
import com.example.ash.sopt19th_mate.setting.SettingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDrawerActivity extends ActionBarActivity implements ScrollTabHolder, ViewPager.OnPageChangeListener{

    NetworkService service;
    ActionBarDrawerToggle drawerToggle;
    // String [] drawer_str={"메인 가수","서브 가수 1","서브 가수 2","서브 가수 3"};

    DrawerLayout drawerLayout;
    ImageView DrawerBgImegeView;
    ImageView UserImageView;
    ImageView settingImage;


    TextView NicknameTextView;

    TextView MSingerTextView;
    TextView SSinger1TextView;
    TextView SSinger2TextView;
    TextView SSinger3TextView;
    // recyclerView;

    TextView AlarmTextView;
    TextView MypageTextView;

    ArrayList<SingerList> singerList;
    ArrayList<InfoList> InfoData;
    Button button;


    ApplicationController applicationController;
    Uri data;
    LinearLayoutManager linearLayoutManager;

    ListAdapter adapter;
    SingerNumberList singerNumberList;

    LinearLayout underLayout1;
    LinearLayout underLayout2;
    LinearLayout underLayout3;
    ImageView imageViewTopbar;

    int subCount = 0;
    int totalCount = 0;
    int itemPosition = 0;

    ////////////////////이 밑은 newsstand
    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;

    private View mHeader;

    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;

    private TextView info;
    private int mLastY;


    private ImageView imageviewBG;
    private ImageView imageviewMain;
    private TextView textviewSingerName;
    ArrayList<SingerList> SingerData;
    ArrayList<ListData> listData;
    ImageView imageViewHeart;
    ImageView imageViewSibal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        imageViewTopbar = (ImageView)findViewById(R.id.homedrawer_imageview_logo);
        imageViewHeart = (ImageView)findViewById(R.id.homedrawer_imageview_heart);
        imageViewSibal =(ImageView)findViewById(R.id.drawer_Background_ImageView_Id);
        Glide.with(this).load(R.drawable.hamburger_top).into(imageViewSibal);
        Glide.with(this).load(R.drawable.main_maingasu).into(imageViewHeart);
        Glide.with(this).load(R.drawable.main_logo).into(imageViewTopbar);
        //ListView listView=(ListView)findViewById(R.id.drawer);

        //ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,drawer_str);
        //listView.setAdapter(adapter);


        listData = new ArrayList<ListData>();
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerBgImegeView=(ImageView)findViewById(R.id.drawer_Background_ImageView_Id);
        UserImageView=(ImageView)findViewById(R.id.drawer_UserImageView_Id);
        NicknameTextView=(TextView)findViewById(R.id.drawer_Nickname_TextView_Id);


        AlarmTextView=(TextView)findViewById(R.id.drawer_Alarm_TextView_Id);
        MypageTextView=(TextView)findViewById(R.id.drawer_Mypage_TextView_Id);

        service= ApplicationController.getInstance().getNetworkService();

        data = ApplicationController.getInstance().getData();
        NicknameTextView.setText(ApplicationController.getInstance().getNickname());

        MSingerTextView = (TextView)findViewById(R.id.drawer_MS_TextView_Id);
        SSinger1TextView = (TextView)findViewById(R.id.drawer_SS1_TextView_Id);
        SSinger2TextView = (TextView)findViewById(R.id.drawer_SS2_TextView_Id);
        SSinger3TextView = (TextView)findViewById(R.id.drawer_SS3_TextView_Id);

        underLayout1 = (LinearLayout)findViewById(R.id.setAlarm);
        underLayout2 = (LinearLayout)findViewById(R.id.toMyPage);
        underLayout3 = (LinearLayout)findViewById(R.id.toSet);

//        Bitmap image_bitmap = null;
//        try {
//            image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), image_bitmap);
//        bitmapDrawable.setCornerRadius(Math.max(image_bitmap.getWidth(), image_bitmap.getHeight()) / 2.0f);
//        bitmapDrawable.setAntiAlias(true);
        Glide.with(getApplicationContext()).load(data).into(UserImageView);
        //UserImageView.setImageURI(data);
        // 배치해놓은 imageview에 동그랗게 set!!!
        //UserImageView.setImageDrawable(bitmapDrawable);

        //ecyclerView = (RecyclerView) findViewById(R.id.recycle2);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.get
//        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        //recyclerView.setLayoutManager(linearLayoutManager);


        Log.v("들어는 옴", "들어는 옴");

        init();

        //TODO : 이 아래에 newsstand에 관한 것.

        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;

        mHeader = findViewById(R.id.header);
        info = (TextView) findViewById(R.id.info);
        imageviewBG = (ImageView) findViewById(R.id.main_imageview_bg);
        imageviewMain = (ImageView) findViewById(R.id.main_imageview_main);
        textviewSingerName = (TextView) findViewById(R.id.main_textview_singer);
        textviewSingerName.setText(ApplicationController.getInstance().getS_name());
        imageviewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationController.getInstance().setBoardS_id(ApplicationController.getInstance().getS_id());
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        Log.v("s_id", String.valueOf(ApplicationController.getInstance().getMain_id()));
        setCurrentSingerview(ApplicationController.getInstance().getS_id());
        //메인에 띄울 가수 지정.
        //TODO : 아직 가수 아이디와 이름 매치 되는 테이블이 없으니까 나중에 설정할 것을 염두.


        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setId(IdGen.generateViewId());
        mViewPager.setOffscreenPageLimit(2);


        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setTabHolderScrollingContent(this);

        mViewPager.setAdapter(mPagerAdapter);

        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(this);
        mLastY = 0;

        underLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(),MyMainActivity.class));
            }
        });

        //settingImage = (ImageView)findViewById(R.id.setting);
        underLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),SettingActivity.class));
            }
        });

        underLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NoticeActivity.class));
            }
        });

        MSingerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp_sid = ApplicationController.getInstance().getNumberSingerSet().get(MSingerTextView.getText().toString());
                setCurrentSingerview(temp_sid);
                ApplicationController.getInstance().setS_id(temp_sid);
                textviewSingerName.setText(MSingerTextView.getText().toString());
                ApplicationController.getInstance().setS_name(MSingerTextView.getText().toString());
                startActivity(new Intent(getApplication(), HomeDrawerActivity.class));
            }
        });
        SSinger1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO : 아무것도 없을 때에 예외처리.
                if(SSinger1TextView.getText().length()!=0) {
                    int temp_sid = ApplicationController.getInstance().getNumberSingerSet().get(SSinger1TextView.getText().toString());
                    setCurrentSingerview(temp_sid);
                    ApplicationController.getInstance().setS_id(temp_sid);
                    textviewSingerName.setText(SSinger1TextView.getText().toString());
                    ApplicationController.getInstance().setS_name(SSinger1TextView.getText().toString());
                    startActivity(new Intent(getApplication(), HomeDrawerActivity.class));
                }
                else;
            }
        });
        SSinger2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SSinger2TextView.getText().length()!=0) {

                    int temp_sid = ApplicationController.getInstance().getNumberSingerSet().get(SSinger2TextView.getText().toString());
                    Log.v("라붐?", String.valueOf(temp_sid));
                    setCurrentSingerview(temp_sid);
                    ApplicationController.getInstance().setS_id(temp_sid);
                    textviewSingerName.setText(SSinger2TextView.getText().toString());
                    ApplicationController.getInstance().setS_name(SSinger2TextView.getText().toString());
                    startActivity(new Intent(getApplication(), HomeDrawerActivity.class));
                }
                else;
            }
        });
        SSinger3TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SSinger3TextView.getText().length()!=0) {

                    int temp_sid = ApplicationController.getInstance().getNumberSingerSet().get(SSinger3TextView.getText().toString());
                    setCurrentSingerview(temp_sid);
                    ApplicationController.getInstance().setS_id(temp_sid);
                    textviewSingerName.setText(SSinger3TextView.getText().toString());
                    ApplicationController.getInstance().setS_name(SSinger3TextView.getText().toString());
                    startActivity(new Intent(getApplication(), HomeDrawerActivity.class));
                }
                else;
            }
        });
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        drawerLayout.closeDrawer(GravityCompat.START);
        super.onRestart();
    }
    public void init(){
        //TODO : 네비게이션에서 맨 위에 띄울 가수를 지정해주어야 함.

        //TODO : 가수 정보 전체적으로 불러오기.

        Call<InfoResult> infoResult = service.infoResult(ApplicationController.getInstance().getM_id());
        infoResult.enqueue(new Callback<InfoResult>() {
            @Override
            public void onResponse(Call<InfoResult> call, Response<InfoResult> response) {
                if(response.isSuccessful())
                    InfoData = response.body().result;
                ApplicationController.getInstance().setTotalSingerCount(InfoData.size());
                ApplicationController.getInstance().setInfoData(InfoData);
                ApplicationController.getInstance().setTotalSingerCount(InfoData.size());
                for (int i = 0; i<InfoData.size(); i++) {
                    if(InfoData.get(i).is_most.equals("t")){
                        ApplicationController.getInstance().setMain_id(ApplicationController.getInstance().getNumberSingerSet().get(InfoData.get(i).name));
                        //Log.d("MyID",data.name);
                    }
                }

                switch(InfoData.size())
                {
                    case 1:
                        MSingerTextView.setText(InfoData.get(0).name);
                        break;
                    case 2:
                        for(int i = 0; i<2; i++)
                        {
                            if(InfoData.get(i).is_most.equals("t"))
                                MSingerTextView.setText(InfoData.get(i).name);
                            else
                                SSinger1TextView.setText(InfoData.get(i).name);
                        }
                        break;
                    case 3:
                        for(int i = 0; i<3; i++)
                        {
                            if(InfoData.get(i).is_most.equals("t"))
                                MSingerTextView.setText(InfoData.get(i).name);
                            else
                            {
                                if(SSinger1TextView.getText().equals(""))
                                    SSinger1TextView.setText(InfoData.get(i).name);
                                else
                                    SSinger2TextView.setText((InfoData).get(i).name);
                            }
                        }
                        break;
                    case 4:
                        for(int i = 0; i<4; i++)
                        {
                            if(InfoData.get(i).is_most.equals("t"))
                                MSingerTextView.setText(InfoData.get(i).name);
                            else
                            {
                                if(SSinger1TextView.getText().equals(""))
                                    SSinger1TextView.setText(InfoData.get(i).name);
                                else if(SSinger2TextView.getText().equals(""))
                                    SSinger2TextView.setText((InfoData).get(i).name);
                                else
                                    SSinger3TextView.setText((InfoData.get(i)).name);
                            }
                        }
                        break;
                }
               // adapter = new ListAdapter(listData, clickEvent, linearLayoutManager);
               // recyclerView.setAdapter(adapter);
                //Log.v(InfoData.get(0).name, InfoData.get(0).name);
                Log.v("성공", "성공");
                ApplicationController.getInstance().setSubSingerCount(subCount);
                ApplicationController.getInstance().setInfoData(InfoData);
            }
            @Override
            public void onFailure(Call<InfoResult> call, Throwable t) {

            }
        });

    }
    public ArrayList<InfoList> InfoDataSwap(ArrayList<InfoList> InfoData)
    {
        for(int i = 0;i< InfoData.size(); i++)
        {
            if(InfoData.get(i).is_most.equals("t"))
            {
                if(i == 0)
                    ;
                else
                {
                    String tempName = "";
                    String tempMost = "";
                    int tempIndex = i;

                    tempName = InfoData.get(0).name;
                    tempMost = InfoData.get(0).is_most;

                    InfoData.get(0).name = InfoData.get(tempIndex).name;
                    InfoData.get(0).is_most = InfoData.get(0).is_most;

                    InfoData.get(tempIndex).name = tempName;
                    InfoData.get(tempIndex).is_most = tempMost;

                    Log.v(InfoData.get(0).name, InfoData.get(0).name);
                    Log.v(InfoData.get(tempIndex).name, InfoData.get(tempIndex).name);




                }
            }

        }
        return InfoData;
    }



//
//        public View.OnClickListener clickEvent = new View.OnClickListener() {
//            public void onClick(View v) {
//               // itemPosition = recyclerView.getChildPosition(v);
//                String temp_name = ApplicationController.getInstance().getInfoData().get(itemPosition).name;
//                Log.v("name", temp_name);
//                int temp_id = ApplicationController.getInstance().getNumberSingerSet().get(temp_name);
//                //onCreate();
//                //setCurrentSingerview(temp_id);
//            }
//        };


    //TODO : 이 아래가 newsstand에 관한 것.
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // nothing
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffsetPixels > 0) {
            int currentItem = mViewPager.getCurrentItem();

            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
            ScrollTabHolder currentHolder;

            if (position < currentItem) {
                currentHolder = scrollTabHolders.valueAt(position);
            } else {
                currentHolder = scrollTabHolders.valueAt(position + 1);
            }

            if (NEEDS_PROXY) {
                // TODO is not good
                currentHolder.adjustScroll(mHeader.getHeight() - mLastY);
                mHeader.postInvalidate();
            } else {
                currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        if (NEEDS_PROXY) {
            //TODO is not good
            currentHolder.adjustScroll(mHeader.getHeight() - mLastY);
            mHeader.postInvalidate();
        } else {
            currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            int scrollY = getScrollY(view);
            if (NEEDS_PROXY) {
                //TODO is not good
                mLastY = -Math.max(-scrollY, mMinHeaderTranslation);
                info.setText(String.valueOf(scrollY));
                mHeader.scrollTo(0, mLastY);
                mHeader.postInvalidate();
            } else {
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
            }
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        // nothing
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private final String[] TITLES = {"투표", "차트"};
        private ScrollTabHolder mListener;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) VoteFragment.newInstance(position);
                    mScrollTabHolders.put(position, fragment);
                    fragment.setScrollTabHolder(mListener);
                    return fragment;
                case 1:
                    ScrollTabHolderFragment fragment1 = (ScrollTabHolderFragment) com.example.ash.sopt19th_mate.newsstand.rankTab.RankFragment.newInstance(position);
                    mScrollTabHolders.put(position, fragment1);
                    fragment1.setScrollTabHolder(mListener);
                    return fragment1;
                default:
                    return null;
            }
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }

    }
    public void setCurrentSingerview(final int s_id)
    {

        Call<MainResult2> requestMainData = service.getMainData2(s_id);
        Log.v("s_id", String.valueOf(s_id));
        requestMainData.enqueue(new Callback<MainResult2>() {
            @Override
            public void onResponse(Call<MainResult2> call, Response<MainResult2> response) {
                //Log.v("이름", response.body().result.title_song);
                Glide.with(getApplicationContext()).load(response.body().result.main_img).into(imageviewMain);
                Glide.with(getApplicationContext()).load(response.body().result.bg_img1).into(imageviewBG);


                //response.body().result.
            }
            @Override
            public void onFailure(Call<MainResult2> call, Throwable t) {
            }
        });
    }
}
