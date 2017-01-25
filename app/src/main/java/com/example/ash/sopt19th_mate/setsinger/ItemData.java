package com.example.ash.sopt19th_mate.setsinger;

/**
 * Created by dldud on 2016-11-10.
 */

public class ItemData
{
    String imageURL;
   // int singer;
    //보통 이미지의 리소스는 인트로 저장.
    String name;
    int most;
    //list에 있어야 할 것은 가수 이미지, 가수 이름, most설정 이미지(T/F)이 네 가지만 있으면 된다.



    public ItemData(String imageURL,String name, int most) {
        this.imageURL = imageURL;
       // this.singer = singer;
        this.name = name;
        this.most = most;
    }
    public String getImageURL()
    {
        return imageURL;
    }
    public String getName() {
        return name;
    }
    //생성 시 이미지, 타이틀, 콘텐트 이름을 받음.
}
