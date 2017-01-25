package com.example.ash.sopt19th_mate.notice;

/**
 * Created by 김민경 on 2017-01-03.
 */

public class NoticeData {

    public String name; // 가수이름
    public String mp_name; // 방송이름
    public String notice; // t,f 여부


    public NoticeData(String name, String mp_name, String notice) {
        this.name = name;
        this.mp_name = mp_name;
        this.notice = notice;
    }
}
