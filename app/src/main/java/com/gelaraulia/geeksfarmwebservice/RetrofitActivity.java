package com.gelaraulia.geeksfarmwebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.*;

import retrofit.*;

public class RetrofitActivity extends AppCompatActivity {
    TextView tv_respond, tv_result_api, tv_result_api_2;
    UserApiRetrofit user_api;
    UserRetrofit uRetro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        tv_respond = (TextView)findViewById(R.id.tv_respond_retrofit);
        tv_result_api = (TextView)findViewById(R.id.tv_result_api_retrofit);
        tv_result_api_2 = (TextView)findViewById(R.id.tv_result_api_retrofit_2);

        Button btn_retro_post = (Button)findViewById(R.id.btn_result_api_retrofit_post);
        Button btn_retro_get = (Button)findViewById(R.id.btn_result_api_retrofit_get);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demo5171800.mockable.io")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //UserApiRetrofit user_api = retrofit.create(UserApiRetrofit.class);
        user_api = retrofit.create(UserApiRetrofit.class);
        // // implement interface for get all user
        btn_retro_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<UsersRetrofit> call = user_api.getUsers();
                call.enqueue(new Callback<UsersRetrofit>() {
                    @Override
                    public void onResponse(Response<UsersRetrofit> response, Retrofit retrofit) {
                        int status = response.code();
                        tv_respond.setText(String.valueOf(status));
                        //this extract data from retrofit with for() loop
                        for(UserRetrofit user : response.body().getUsers()) {
                            tv_result_api.append(
                                    "Id = " + String.valueOf(user.getId()) +
                                            System.getProperty("line.separator") +
                                            "Email = " + user.getEmail() +
                                            System.getProperty("line.separator") +
                                            "Password = " + user.getPassword() +
                                            System.getProperty("line.separator") +
                                            "Token Auth = " + user.getTokenAuth() +
                                            System.getProperty("line.separator") +
                                            "Created at = " + user.getCreatedAt() +
                                            System.getProperty("line.separator") +
                                            "Updated at = " + user.getUpdatedAt() +
                                            System.getProperty("line.separator") +
                                            System.getProperty("line.separator")
                            );
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        tv_respond.setText(String.valueOf(t));
                    }
                });
            }
        });

        btn_retro_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uRetro = new UserRetrofit(10,"qwerty","zxcvbnm");
                Call<UserRetrofit> callPost = user_api.saveUser(uRetro);
                callPost.enqueue(new Callback<UserRetrofit>() {
                    @Override
                    public void onResponse(Response<UserRetrofit> response, Retrofit retrofit) {
                        int status = response.code();
                        tv_result_api_2.setText(String.valueOf(status));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        tv_result_api_2.setText(String.valueOf(t));
                    }
                });
            }
        });
    }
}