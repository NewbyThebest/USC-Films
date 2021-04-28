package com.geekb.uscfilms;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

    @GET("/home/mvcurrent")
    Observable<List<VideoData>> getMovieBanners();

    @GET("/home/popularmv")
    Observable<List<VideoData>> getMoviePopular();

    @GET("/home/topratemv")
    Observable<List<VideoData>> getMovieTop();

    @GET("/home/populartv")
    Observable<List<VideoData>> getTVPopular();

    @GET("/home/topratetv")
    Observable<List<VideoData>> getTVTop();

    @GET("/home/trendingtv")
    Observable<List<VideoData>> getTVBanners();

    @GET("/search")
    Observable<List<VideoData>> getSearch(@Query("query") String query);

    @GET("/detail/video")
    Observable<List<VideoDetailData>> getVideo(@Query("type") String type,@Query("id") String id);

    @GET("/cast/crew")
    Observable<List<VideoData>> getCast(@Query("type") String type,@Query("id") String id);

    @GET("/review")
    Observable<List<ReviewData>> getReviews(@Query("type") String type,@Query("id") String id);

    @GET("/similar")
    Observable<List<VideoData>> getSimilar(@Query("type") String type,@Query("id") String id);
}
