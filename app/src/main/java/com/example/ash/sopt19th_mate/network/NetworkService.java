package com.example.ash.sopt19th_mate.network;

import com.example.ash.sopt19th_mate.allsingerinfo.InfoResult;
import com.example.ash.sopt19th_mate.board.BoardResult;
import com.example.ash.sopt19th_mate.board.DeleteBoardResult;
import com.example.ash.sopt19th_mate.board.LikeData;
import com.example.ash.sopt19th_mate.board.LikeResult;
import com.example.ash.sopt19th_mate.board.RegisterResult2;
import com.example.ash.sopt19th_mate.board.detail.ComentResult;
import com.example.ash.sopt19th_mate.board.detail.CommentRegisterData;
import com.example.ash.sopt19th_mate.board.detail.CommentRegisterResult;
import com.example.ash.sopt19th_mate.home.DrawerResult;
import com.example.ash.sopt19th_mate.home.MainResult2;
import com.example.ash.sopt19th_mate.login.LoginObject;
import com.example.ash.sopt19th_mate.login.LoginResult2;
import com.example.ash.sopt19th_mate.mypage.AddObject;
import com.example.ash.sopt19th_mate.mypage.AddSubResult;
import com.example.ash.sopt19th_mate.mypage.ChangeResult;
import com.example.ash.sopt19th_mate.mypage.MyPageSearchResult;
import com.example.ash.sopt19th_mate.mypage.SubList;
import com.example.ash.sopt19th_mate.mypageinit.MyPageResult;
import com.example.ash.sopt19th_mate.newsstand.MainResult;
import com.example.ash.sopt19th_mate.newsstand.rankTab.BugsRankResult;
import com.example.ash.sopt19th_mate.newsstand.rankTab.GenieRankResult;
import com.example.ash.sopt19th_mate.newsstand.rankTab.MelonRankResult;
import com.example.ash.sopt19th_mate.newsstand.rankTab.MnetRankResult;
import com.example.ash.sopt19th_mate.newsstand.rankTab.NaverRankResult;
import com.example.ash.sopt19th_mate.newsstand.voteTab.VoteResult;
import com.example.ash.sopt19th_mate.notice.GetObject;
import com.example.ash.sopt19th_mate.notice.NoticeResult;
import com.example.ash.sopt19th_mate.notice.RegisterResult3;
import com.example.ash.sopt19th_mate.setsinger.SetRegisterResult;
import com.example.ash.sopt19th_mate.setsinger.SingerResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by hyeon on 2016-12-26.
 */

public interface NetworkService {
//여기엔 request 방식을 기술.

    //새로운 멤버 등록
//    @Multipart
//    @POST("/membersearch/find_member")
//    Call<MyPageResult> requestRegister(@Part("e-mail")String e_mail,
//                                       @Part("kind_sns") String kind_sns,
//                                       @Part("nickname") String nickname,
//                                       @Part("profile_img") String profile_img);

    //로그인 했을 때
//    @Multipart
//    @POST("/member/find_member")
//    Call<LoginResult2> loginrequestRegister(@Part("email") String email,
//                                            @Part("kind_sns") String kind_sns);
    @POST("/member/find_member")
    Call<LoginResult2> loginrequestRegister(@Body LoginObject loginObject);

    //가수 검색 목록 불러오기
    @GET("/singer/singer_allview")
    Call<SingerResult> singerResult();


    @GET("/singer/singer_allview")
    Call<MyPageSearchResult> searchResult();
    //이 밑은 추천 목록.

    //이 밑은 닉네임 체크
    @GET("/member/n_check/{nickname}")
    Call<MyPageResult> requestRegister(@Path("nickname") String nickname);

    //차트받기

    @GET("mp_send/bugs")
    Call<BugsRankResult> getBugsData();
    @GET("mp_send/naver")
    Call<NaverRankResult> getNaverData();
    @GET("mp_send/melon")
    Call<MelonRankResult> getMelonData();
    @GET("mp_send/mnet")
    Call<MnetRankResult> getMnetnData();
    @GET("mp_send/genie")
    Call<GenieRankResult> getGenieData();
//이미지 관련해서 오류 나면 이 밑 변경
    @Multipart
    @POST("/member/member_input")
    Call<SetRegisterResult> requestFinalRegister(
            @Part MultipartBody.Part profile_img,
            @Part("email") RequestBody email,
            @Part("nickname") RequestBody nickname,
            @Part("kind_sns") RequestBody kind_sns,
            @Part("s_id") int s_id);
//    @POST("/member/member_input")
//    Call<RegisterList> requestFinalRegister(@Body UserObject userObject);



    //최애-차애 변경
    @GET("/member/ch_singer/{m_id}/{s_id}")
    Call<ChangeResult> changeMainSinger(@Path("m_id") int m_id,
                                        @Path("s_id") int s_id);

    //차애 추가.
    @POST("/member/myList_input")
    Call<AddSubResult> addSubSinger(@Body AddObject addObject);

    //내 가수 목록 불러오기
    @GET("/member/myList_output/{m_id}")
    Call<InfoResult> infoResult(@Path("m_id") int m_id);


    //name에 맞는 가수 정보 불러오기.(전부 불러옴)
    @GET("/singer/singer_search/{s_id}")
    Call<DrawerResult> singerData(@Path("s_id") int s_id);


//    @POST("member/myList_input")
//    Call<AddSubResult> plusSubSinger(@Body);

    //s_id를 넘겨주면 가수 정보(name, title, mainImg, bgImg)
    @GET("/singer/singerInfo/{s_id}")
    Call<MainResult> getMainData(@Path("s_id") int s_id);

    @GET("/singer/mpInfo/{s_id}/{m_id}")
    Call<VoteResult> getVoteData(@Path("s_id") int s_id,
                                    @Path("m_id") int m_id);

    //s_id를 넘겨주면 가수 정보(name, title, mainImg, bgImg)
    @GET("/singer/singerInfo/{s_id}")
    Call<MainResult2> getMainData2(@Path("s_id") int s_id);

    @POST("/member/ch_notice")
    Call<RegisterResult3> requestRegister2(@Body GetObject getObject);

    @GET("/member/getNotice/{m_id}")
    Call<NoticeResult> getNoticeData(@Path("m_id") int m_id);



    //특정 가수 정보 불러오기
    @GET("/singer/singerInfo/{s_id}")
    Call<SubList> getSingerData(@Path("s_id") int s_id);


//    @POST("/board/addBoard")
//    Call<RegisterResult2> requestRegisterBoard(@Body RegisterObject registerObject);

    @GET("/board/getList/{s_id}/{m_id}")
    Call<BoardResult> getMainData(@Path("s_id") int s_id,
                                  @Path("m_id") int m_id);

    @GET("/comment/getList/{b_id}")
    Call<ComentResult> getComentData(@Path("b_id") int b_id);

    ///comment/addCommnent
    @POST("/comment/addComment")
    Call<CommentRegisterResult> requestCommentRegisterBoard(@Body CommentRegisterData commentRegisterData);

    @Multipart
    @POST("/board/addBoard")
    Call<RegisterResult2> requestRegisterBoard(@Part("s_id") int s_id,
                                               @Part("m_id") int m_id,
                                               @Part("b_content") RequestBody b_content,
                                               @Part("b_time") RequestBody b_time,
                                               @Part MultipartBody.Part img);

    @POST("/board/like_status")
    Call<LikeResult> requestLike(@Body LikeData likeData);

    @GET("/board/deleteBoard/{b_id}")
    Call<DeleteBoardResult> requestDeleteBoard(@Path("b_id") int b_id);

}