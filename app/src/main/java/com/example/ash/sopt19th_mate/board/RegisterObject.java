package com.example.ash.sopt19th_mate.board;

/**
 * Created by dldud on 2017-01-04.
 */

public class RegisterObject {
    int s_id;
    int m_id;
    String b_content;
    long b_time;
    String img;

    public RegisterObject(int s_id, int m_id, String b_content, long b_time, String img)
    {
        this.s_id = s_id;
        this.m_id = m_id;
        this.b_content = b_content;
        this.b_time = b_time;
        this.img = img;
    }
}
