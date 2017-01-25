package com.example.ash.sopt19th_mate.board;

import java.io.Serializable;

/**
 * Created by ash on 2017-01-03.
 */

public class BoardListData implements Serializable {
    public int b_id;
    public String b_content;
    public String b_time;
    public String name;
    public String bg_img1;
    public String img;
    public int m_id;
    public String nickname;
    public String profile_img;
    public int c_count;
    public int like_count;
    public int dislike_count;
    public String is_like;


    public BoardListData(int b_id, String b_content, String b_time, String name, String bg_img1, String img, String nickname, String profile_img, int c_count, int like_count, int dislike_count, String is_like) {
        this.b_id = b_id;
        this.b_content = b_content;
        this.b_time = b_time;
        this.name = name;
        this.bg_img1 = bg_img1;
        this.img = img;
        this.nickname = nickname;
        this.profile_img = profile_img;
        this.c_count = c_count;
        this.like_count = like_count;
        this.dislike_count = dislike_count;
        this.is_like = is_like;
    }
}
