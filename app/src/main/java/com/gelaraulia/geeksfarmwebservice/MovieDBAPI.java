package com.gelaraulia.geeksfarmwebservice;

import retrofit.http.*;
import retrofit.*;

/**
 * Created by G_Aulia on 19 Des 2017.
 */

public interface MovieDBAPI {

//    @GET("/api/v1/auth{id}")
//    Call<UserRetrofit> getUser(@Path("id") String UserRetrofit_id);

//    https://api.themoviedb.org/3/movie/now_playing?api_key=fb49ad5c5cd9525aa1190604a869ebea&language=en-US&page=1
    @GET("/3/movie/now_playing")
    Call<GetMovies> nowPlaying(@Query("api_key") String apiKey, @Query("language") String lang, @Query("page") int page);

    @GET("/3/movie/top_rated")
    Call<GetMovies> topRated(@Query("api_key") String apiKey, @Query("language") String lang, @Query("page") int  page);

    @GET("/3/movie/popular")
    Call<GetMovies> popularMovie(@Query("api_key") String apiKey, @Query("language") String lang, @Query("page") int page);

    @GET("/3/movie/upcoming")
    Call<GetMovies> upcomingMovie(@Query("api_key") String apiKey, @Query("language") String lang, @Query("page") int page);

    @GET("/3/search/movie")
    Call<MoviesDB> searchMovie(@Query("api_key") String apiKey, @Query("query") String movieName);

    @GET("/3/movie/{id}")
	Call<MovieDB> getMovie(@Path("id") int movieId, @Query("api_key") String apiKey);

//    https://api.themoviedb.org/3/authentication/guest_session/new?api_key=fb49ad5c5cd9525aa1190604a869ebea
    @GET("/3/authentication/guest_session/new")
    Call<Guest> guestSession(@Query("api_key") String apiKey);

//    @PUT("/api/v1/auth/{id}")
//    Call<UserRetrofit> updateUser(@Path("id") int user_id, @Body UserRetrofit user);

//    @POST("/users/post")
//    Call<UserRetrofit> saveUser(@Body UserRetrofit user);

//    https://api.themoviedb.org/3/movie/181808/rating?api_key=fb49ad5c5cd9525aa1190604a869ebea&guest_session_id=4b82641070cfe5c8aee7eb621eea4908
    @FormUrlEncoded
    @POST("3/movie/181808/rating")
    Call<SetRating> rateMovie(
            @Field("value") double rating,
            @Query("api_key") String apiKey, @Query("guest_session_id") String guestId);

//    @DELETE("/api/v1/auth/{id}")
//    Call<UserRetrofit> deleteUser(@Path("id") String user_id);
}
