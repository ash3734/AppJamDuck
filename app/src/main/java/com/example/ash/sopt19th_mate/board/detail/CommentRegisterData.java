package com.example.ash.sopt19th_mate.board.detail;

/**
 * Created by ash on 2017-01-05.
 */

public class CommentRegisterData {
        int b_id;
        int m_id;
        String c_content;
        String time;

        public CommentRegisterData(int b_id, int m_id, String c_content, String time) {
                this.b_id = b_id;
                this.m_id = m_id;
                this.c_content = c_content;
                this.time = time;
        }
}
