package com.example.ash.sopt19th_mate.board.detail;

/**
 * Created by ash on 2017-01-05.
 */

public class ComentListData{
    String nickname;
    String c_content;
    String time;

    public ComentListData(String nickname, String c_content, String time) {
        this.nickname = nickname;
        this.c_content = c_content;
        this.time = time;
    }

    public String getNickname() {

        return nickname;
    }

    public String getC_content() {
        return c_content;
    }

    public String getTime() {
        return time;
    }
}
