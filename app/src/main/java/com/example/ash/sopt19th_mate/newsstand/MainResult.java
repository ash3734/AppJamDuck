package com.example.ash.sopt19th_mate.newsstand;

/**
 * Created by ash on 2017-01-01.
 */

public class MainResult {
    public MainData result;
    class MainData {
        String album_name;
        String title_song;
        String main_img;
        String bg_img1;

        public MainData(String album_name, String title_song, String main_img, String bg_img1) {
            this.album_name = album_name;
            this.title_song = title_song;
            this.main_img = main_img;
            this.bg_img1 = bg_img1;
        }
    }
}
