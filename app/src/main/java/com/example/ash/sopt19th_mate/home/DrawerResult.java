package com.example.ash.sopt19th_mate.home;

/**
 * Created by hyeon on 2017-01-01.
 */

public class DrawerResult {
    public DrawerData result;

    class DrawerData{
        public int s_id;
        public String name;
        public String album_name;
        public  String title_song;
        public String main_img;
        public  String bg_img1;
        public  String bg_img2;

        public DrawerData(int s_id, String name, String album_name, String title_song, String main_img, String bg_img1, String bg_img2) {
            this.s_id = s_id;
            this.name = name;
            this.album_name = album_name;
            this.title_song = title_song;
            this.main_img = main_img;
            this.bg_img1 = bg_img1;
            this.bg_img2 = bg_img2;
        }
    }
}
