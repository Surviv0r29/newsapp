package com.google.newsapp1;

import com.google.newsapp1.Model.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<Headlines> getHeadlines(
         @Query("country")String country,
         @Query("apikey")String api_key
    );

    @GET("everything")
    Call<Headlines> getEverything(
            @Query("q")String country,
            @Query("apikey")String api_key
    );

}
