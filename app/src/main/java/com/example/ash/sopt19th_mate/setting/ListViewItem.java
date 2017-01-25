package com.example.ash.sopt19th_mate.setting;

/**
 * Created by 김민경 on 2016-12-30.
 */

public class ListViewItem {
    String title;
    int icon;

    public ListViewItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
