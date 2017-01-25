package com.example.ash.sopt19th_mate.setsinger;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by dldud on 2017-01-05.
 */

public class UserObject {

    MultipartBody.Part profile_img;
    RequestBody email;
    RequestBody nickname;
    RequestBody kind_sns;
    int s_id;

    public UserObject(MultipartBody.Part profile_img, RequestBody email, RequestBody nickname, RequestBody kind_sns, int s_id)
    {
        this.profile_img = profile_img;
        this.email = email;
        this.nickname = nickname;
        this.kind_sns = kind_sns;
        this.s_id = s_id;
    }
}
