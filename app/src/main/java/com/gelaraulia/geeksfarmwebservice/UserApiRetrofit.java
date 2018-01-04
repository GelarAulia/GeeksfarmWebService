package com.gelaraulia.geeksfarmwebservice;

import retrofit.http.*;
import retrofit.*;

/**
 * Created by G_Aulia on 19 Des 2017.
 */

public interface UserApiRetrofit {
    @GET("/users/get")
    Call<UsersRetrofit> getUsers();

//    @GET("/api/v1/auth{id}")
//    Call<UserRetrofit> getUser(@Path("id") String UserRetrofit_id);
//
//    @PUT("/api/v1/auth/{id}")
//    Call<UserRetrofit> updateUser(@Path("id") int user_id, @Body UserRetrofit user);

    @POST("/users/post")
    Call<UserRetrofit> saveUser(@Body UserRetrofit user);

//    @DELETE("/api/v1/auth/{id}")
//    Call<UserRetrofit> deleteUser(@Path("id") String user_id);
}
