package com.sundram.developertest.moviewapiservice;

import com.sundram.developertest.datamodel.Movie;
import com.sundram.developertest.datamodel.MovieResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPIService {

    @GET("movie")
    Call<MovieResult> getAllMovieByKeyword(@Query("api_key") String api_key, @Query("language") String language, @Query("query") String movie_name);
}
