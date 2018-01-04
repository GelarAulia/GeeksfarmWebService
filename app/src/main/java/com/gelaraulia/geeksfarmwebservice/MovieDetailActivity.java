package com.gelaraulia.geeksfarmwebservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieDetailActivity extends AppCompatActivity {
    private Integer movieId;
    private String movieTitle;
    private String moviePosterPath;
    private String guestId;

    double rate;

    final String apiKey = "fb49ad5c5cd9525aa1190604a869ebea";

    ImageView iv_poster;
    TextView tv_title, tv_originTitle, tv_rating, tv_overview, tv_genre, tv_date;
    MovieDBAPI movie_api;

    RatingBar rb_rating, rb_setRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        movieId = intent.getIntExtra("id", 0);
        movieTitle = intent.getStringExtra("title");
        moviePosterPath = intent.getStringExtra("poster");

        setTitle(movieTitle);

        iv_poster = (ImageView)findViewById(R.id.iv_movieDetail);
        tv_title = (TextView)findViewById(R.id.tv_movieTitle);
        tv_originTitle = (TextView)findViewById(R.id.tv_originTitle);
        tv_rating = (TextView)findViewById(R.id.tv_movieRating);
        tv_overview = (TextView)findViewById(R.id.tv_movieOverview);
        rb_rating = (RatingBar)findViewById(R.id.rb_movieRating);
        tv_genre = (TextView)findViewById(R.id.tv_movieGenre);
        tv_date = (TextView)findViewById(R.id.tv_movieDate);

        Gson gsonDetail = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retroDetail = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create(gsonDetail))
                .build();

        movie_api = retroDetail.create(MovieDBAPI.class);
        Call<MovieDB> call = movie_api.getMovie(movieId, apiKey);
        call.enqueue(new Callback<MovieDB>() {
            @Override
            public void onResponse(Response<MovieDB> response, Retrofit retrofit) {
                Picasso.with(MovieDetailActivity.this).load(moviePosterPath).into(iv_poster);
                tv_title.setText(response.body().getTitle());
                tv_originTitle.setText(response.body().getOriginalTitle());
                tv_rating.setText(String.valueOf(response.body().getVoteAverage()));
                tv_overview.setText(response.body().getOverview());
                float a = (float)(response.body().getVoteAverage()/2);
                rb_rating.setRating(a);
                tv_genre.setText(response.body().getGenres().get(0).getName());
                tv_date.setText(response.body().getReleaseDate());
            }

            @Override
            public void onFailure(Throwable t) {
                tv_title.setText("Not Available");
                tv_originTitle.setText("Not Available");
                tv_rating.setText("Not Available");
                tv_overview.setText(String.valueOf(t));
                tv_genre.setText("Not Available");
                tv_date.setText("Not Available");
            }
        });

        rb_setRating = (RatingBar)findViewById(R.id.rb_setRating);

        Button btn_rating = (Button)findViewById(R.id.btn_setRating);
        btn_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate = (double)(rb_setRating.getRating()*2);

                Call<Guest> callGuest = movie_api.guestSession(apiKey);
                callGuest.enqueue(new Callback<Guest>() {
                    @Override
                    public void onResponse(Response<Guest> response, Retrofit retrofit) {
                        guestId = response.body().getGuestSessionId();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(MovieDetailActivity.this, "Couldn't get Guess Session",Toast.LENGTH_SHORT).show();
                    }
                });

                Call<SetRating> callRating = movie_api.rateMovie(rate, apiKey, guestId);
                callRating.enqueue(new Callback<SetRating>() {
                    @Override
                    public void onResponse(Response<SetRating> response, Retrofit retrofit) {
                        Toast.makeText(MovieDetailActivity.this, "Movie rated: "+rate, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(MovieDetailActivity.this, "Failed to rate movie", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
