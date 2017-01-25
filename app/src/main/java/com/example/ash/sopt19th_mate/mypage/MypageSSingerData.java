package com.example.ash.sopt19th_mate.mypage;

/**
 * Created by hyeon on 2016-12-30.
 */

public class MypageSSingerData {
    String name;

    public String getMain_img() {
        return main_img;
    }

    public String getBg_img1() {
        return bg_img1;
    }

    String main_img;
    String bg_img1;

    public MypageSSingerData (String name, String main_img, String bg_img1)
    {
        this.name = name;
        this.main_img = main_img;
        this.bg_img1 = bg_img1;
    }


}
