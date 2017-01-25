package com.example.ash.sopt19th_mate.setsinger;

/**
 * Created by dldud on 2016-12-29.
 */

public class RegisterList {
    //public String result;

//    public Arraylist<MemberData> member;
    public MemberData member;
    class MemberData
    {
        int m_id;
        String email;
        String nickname;
        String profile_img;
    }
    public SingerData list;
    class SingerData
    {
        int s_id;
        String name;
        String bg_img1;
        String is_most;
    }
}