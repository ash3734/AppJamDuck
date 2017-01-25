package com.example.ash.sopt19th_mate.newsstand.voteTab;

import java.io.Serializable;

/**
 * Created by ash on 2017-01-01.
 */

public class VoteListData implements Serializable{
    public String mp_name;
    public String favorite_vote;
    public String sms_vote;
    public String call_vote;
    public String online_vote;
    public String mp_time;
    public String fav_period;
    public String vote_method;
    public String mp_img;
    public String what_week;

    public VoteListData(){}
    public VoteListData(String mp_name, String favorite_vote, String sms_vote, String call_vote, String online_vote, String mp_time, String fav_period, String vote_method, String mp_img, String what_week) {
        this.mp_name = mp_name;
        this.favorite_vote = favorite_vote;
        this.sms_vote = sms_vote;
        this.call_vote = call_vote;
        this.online_vote = online_vote;
        this.mp_time = mp_time;
        this.fav_period = fav_period;
        this.vote_method = vote_method;
        this.mp_img = mp_img;
        this.what_week = what_week;
    }
}
