package com.example.ash.sopt19th_mate.setsinger;

/**
 * Created by dldud on 2016-12-29.
 */

public class SingerList {

    int s_id;
    String name;
    String album_name;
    String title_song;
    String main_img;
    String bg_img1;
    String bg_img2;
    int most;

    public SingerList(String album_name, String bg_img1, String bg_img2, String main_img, String name, int s_id, String title_song) {
        this.album_name = album_name;
        this.bg_img1 = bg_img1;
        this.bg_img2 = bg_img2;
        this.main_img = main_img;
        this.name = name;
        this.s_id = s_id;
        this.title_song = title_song;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public String getBg_img1() {
        return bg_img1;
    }

    public String getBg_img2() {
        return bg_img2;
    }

    public String getMain_img() {
        return main_img;
    }

    public String getName() {
        return name;
    }

    public int getS_id() {
        return s_id;
    }

    public String getTitle_song() {
        return title_song;
    }
}
