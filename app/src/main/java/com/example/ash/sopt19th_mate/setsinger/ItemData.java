package com.example.ash.sopt19th_mate.setsinger;

/**
 * Created by dldud on 2016-11-10.
 */

public class ItemData
{

    int singer;
    //보통 이미지의 리소스는 인트로 저장.
    String name;
    int most;


    public ItemData(int singer, String name, int most) {
        this.singer = singer;
        this.name = name;
        this.most = most;
    }

    public String getName() {
        return name;
    }
    //생성 시 이미지, 타이틀, 콘텐트 이름을 받음.
}
