package com.gelaraulia.geeksfarmwebservice;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.*;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.*;

public class MovieDBActivity extends AppCompatActivity {
    TextView tv_get;
    MovieDBAPI movie_api;
    EditText et_search;
    final String apiKey = "fb49ad5c5cd9525aa1190604a869ebea";
    final String posterBaseUrl = "https://image.tmdb.org/t/p/w500";
    final String logoPath = "https://www.themoviedb.org/assets/static_cache/9b3f9c24d9fd5f297ae433eb33d93514/images/v4/logos/408x161-powered-by-rectangle-green.png";

    ArrayList<String> movieDB_poster = new ArrayList<String>();
    ArrayList<String> movieDB_name = new ArrayList<String>();
    ArrayList<Integer> movieDB_id = new ArrayList<Integer>();

    RecyclerView rv_movieDB;
    RecyclerView.LayoutManager rvlm_movieDB;
    RecyclerView.Adapter rva_movieDB;

    int x = 0, y = 0;

    Context mContext;

//    ProgressBar pBar;

    Spinner spin_categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_db);
        //setTitle("The Movie Database");

        ImageView iv_logo = (ImageView)findViewById(R.id.iv_movieDB_logo);
        Picasso.with(this).load(logoPath).into(iv_logo);

        Button btn_get = (Button)findViewById(R.id.btn_movieDB_get);
        tv_get = (TextView)findViewById(R.id.tv_movieDB_get);
        et_search = (EditText)findViewById(R.id.et_movieDB_search);

        mContext = MovieDBActivity.this;

        spin_categories = (Spinner)findViewById(R.id.spin_categories);

        Gson gsonMovie = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retroMovie = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create(gsonMovie))
                .build();

//        rv_movieDB = (RecyclerView)findViewById(R.id.rv_movieDB_search);
//        rv_movieDB.setHasFixedSize(true);
//
//        rvlm_movieDB = new LinearLayoutManager(this);
//        rv_movieDB.setLayoutManager(rvlm_movieDB);
//
//        Log.e("RV INSTANCE","X");
//        rva_movieDB = new MovieDBRecyclerAdapter(mContext, movieDB_poster, movieDB_name);
//        Log.e("RV INSTANCE","SEND TO RECYCLERVIEW ADAPTER");
//        rv_movieDB.setAdapter(rva_movieDB);

        movie_api = retroMovie.create(MovieDBAPI.class);

        rv_movieDB = (RecyclerView)findViewById(R.id.rv_movieDB_search);
        rv_movieDB.setHasFixedSize(true);
        rvlm_movieDB = new GridLayoutManager(mContext,2);
        rv_movieDB.setLayoutManager(rvlm_movieDB);
        rva_movieDB = new MovieDBRecyclerAdapter(mContext, movieDB_poster, movieDB_name, movieDB_id);
        rv_movieDB.setAdapter(rva_movieDB);

//        Call<GetMovies> callNP = movie_api.nowPlaying(apiKey, "en-US", 1);
//        callNP.enqueue(new Callback<GetMovies>() {
//            @Override
//            public void onResponse(Response<GetMovies> response, Retrofit retrofit) {
//                rv_movieDB = (RecyclerView)findViewById(R.id.rv_movieDB_search);
//                rv_movieDB.setHasFixedSize(true);
//                //rvlm_movieDB = new LinearLayoutManager(this);
//                rvlm_movieDB = new GridLayoutManager(mContext,2);
//                rv_movieDB.setLayoutManager(rvlm_movieDB);
//
//                for (Result movies : response.body().getResults()){
//                    if(movies.getPosterPath()!=null){
//                        movieDB_poster.add(posterBaseUrl+movies.getPosterPath());
//                    }else{
//                        movieDB_poster.add(movies.getPosterPath());
//                    }
//
//                    movieDB_name.add(movies.getTitle());
//                    movieDB_id.add(movies.getId());
//                }
//
//                rva_movieDB = new MovieDBRecyclerAdapter(mContext, movieDB_poster, movieDB_name, movieDB_id);
//                rv_movieDB.setAdapter(rva_movieDB);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                tv_get.setText(String.valueOf(t));
//            }
//        });

        spin_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String choices = adapterView.getItemAtPosition(pos).toString();
                switch (choices){
                    case "Now Playing":
                        Call<GetMovies> callNP = movie_api.nowPlaying(apiKey, "en-US", 1);
                        callNP.enqueue(new Callback<GetMovies>() {
                            @Override
                            public void onResponse(Response<GetMovies> response, Retrofit retrofit) {
                                movieDB_poster.clear();
                                movieDB_name.clear();
                                movieDB_id.clear();
                                rva_movieDB.notifyDataSetChanged();

                                for (Result movies : response.body().getResults()){
                                    if(movies.getPosterPath()!=null){
                                        movieDB_poster.add(posterBaseUrl+movies.getPosterPath());
                                    }else{
                                        movieDB_poster.add(movies.getPosterPath());
                                    }

                                    movieDB_name.add(movies.getTitle());
                                    movieDB_id.add(movies.getId());
                                }

                                rva_movieDB = new MovieDBRecyclerAdapter(mContext, movieDB_poster, movieDB_name, movieDB_id);
                                rv_movieDB.setAdapter(rva_movieDB);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                tv_get.setText(String.valueOf(t));
                            }
                        });
                        break;
                    case "Top Rated Movies":
                        Call<GetMovies> callTR = movie_api.topRated(apiKey, "en-US", 1);
                        callTR.enqueue(new Callback<GetMovies>() {
                            @Override
                            public void onResponse(Response<GetMovies> response, Retrofit retrofit) {
                                movieDB_poster.clear();
                                movieDB_name.clear();
                                movieDB_id.clear();
                                rva_movieDB.notifyDataSetChanged();

                                for (Result movies : response.body().getResults()){
                                    if(movies.getPosterPath()!=null){
                                        movieDB_poster.add(posterBaseUrl+movies.getPosterPath());
                                    }else{
                                        movieDB_poster.add(movies.getPosterPath());
                                    }

                                    movieDB_name.add(movies.getTitle());
                                    movieDB_id.add(movies.getId());
                                }

                                rva_movieDB = new MovieDBRecyclerAdapter(mContext, movieDB_poster, movieDB_name, movieDB_id);
                                rv_movieDB.setAdapter(rva_movieDB);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                tv_get.setText(String.valueOf(t));
                            }
                        });
                        break;
                    case "Popular Movies":
                        Call<GetMovies> callPM = movie_api.popularMovie(apiKey, "en-US", 1);
                        callPM.enqueue(new Callback<GetMovies>() {
                            @Override
                            public void onResponse(Response<GetMovies> response, Retrofit retrofit) {
                                movieDB_poster.clear();
                                movieDB_name.clear();
                                movieDB_id.clear();
                                rva_movieDB.notifyDataSetChanged();

                                for (Result movies : response.body().getResults()){
                                    if(movies.getPosterPath()!=null){
                                        movieDB_poster.add(posterBaseUrl+movies.getPosterPath());
                                    }else{
                                        movieDB_poster.add(movies.getPosterPath());
                                    }

                                    movieDB_name.add(movies.getTitle());
                                    movieDB_id.add(movies.getId());
                                }

                                rva_movieDB = new MovieDBRecyclerAdapter(mContext, movieDB_poster, movieDB_name, movieDB_id);
                                rv_movieDB.setAdapter(rva_movieDB);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                tv_get.setText(String.valueOf(t));
                            }
                        });
                        break;
                    case "Upcoming Movies":
                        Call<GetMovies> callUM = movie_api.upcomingMovie(apiKey, "en-US", 1);
                        callUM.enqueue(new Callback<GetMovies>() {
                            @Override
                            public void onResponse(Response<GetMovies> response, Retrofit retrofit) {
                                movieDB_poster.clear();
                                movieDB_name.clear();
                                movieDB_id.clear();
                                rva_movieDB.notifyDataSetChanged();

                                for (Result movies : response.body().getResults()){
                                    if(movies.getPosterPath()!=null){
                                        movieDB_poster.add(posterBaseUrl+movies.getPosterPath());
                                    }else{
                                        movieDB_poster.add(movies.getPosterPath());
                                    }

                                    movieDB_name.add(movies.getTitle());
                                    movieDB_id.add(movies.getId());
                                }

                                rva_movieDB = new MovieDBRecyclerAdapter(mContext, movieDB_poster, movieDB_name, movieDB_id);
                                rv_movieDB.setAdapter(rva_movieDB);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                tv_get.setText(String.valueOf(t));
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String search_query = et_search.getText().toString();
                String query = search_query.replaceAll(" ","+");

                Call<MoviesDB> call = movie_api.searchMovie(apiKey, query);
                call.enqueue(new Callback<MoviesDB>() {
                    @Override
                    public void onResponse(Response<MoviesDB> response, Retrofit retrofit) {

//                        Log.e("RV INSTANCE","SETRV");
//                        rv_movieDB = (RecyclerView)findViewById(R.id.rv_movieDB_search);
//                        rv_movieDB.setHasFixedSize(true);
//                        Log.e("RV INSTANCE","SETLM");
//                        //rvlm_movieDB = new LinearLayoutManager(this);
//                        rvlm_movieDB = new GridLayoutManager(mContext,2);
//                        rv_movieDB.setLayoutManager(rvlm_movieDB);
//
//                        rva_movieDB = new MovieDBRecyclerAdapter(mContext, movieDB_poster, movieDB_name, movieDB_id);
//                        rv_movieDB.setAdapter(rva_movieDB);

                        movieDB_poster.clear();
                        movieDB_name.clear();
                        movieDB_id.clear();
                        rva_movieDB.notifyDataSetChanged();

//                        for (Result movies : response.body().getResults()){
//                            tv_get.append(
//                                    "Id = " + String.valueOf(movies.getId()) +
//                                            System.getProperty("line.separator") +
//                                            "\nTitle = " + movies.getTitle() +
//                                            System.getProperty("line.separator") +
//                                            "\nOriginal Title = " + movies.getOriginalTitle() +
//                                            System.getProperty("line.separator") +
//                                            "\nRelease Date = " + movies.getReleaseDate() +
//                                            System.getProperty("line.separator") +
//                                            System.getProperty("line.separator")
//                            );
//                        }

                        for (Result movies : response.body().getResults()){
                            if(movies.getPosterPath()!=null){
                                movieDB_poster.add(posterBaseUrl+movies.getPosterPath());
                            }else{
                                movieDB_poster.add(movies.getPosterPath());
                            }

                            movieDB_name.add(movies.getTitle());
                            movieDB_id.add(movies.getId());
                        }

						showRecyclerViewContent(mContext, movieDB_poster, movieDB_name, movieDB_id);
//                        pBar = new ProgressBar();
//                        pBar.execute();
                    }
                    @Override
                    public void onFailure(Throwable t  ) {
                        tv_get.setText(String.valueOf(t));
                    }
                });
            }
        });
    }
	
	private void showRecyclerViewContent(Context context, ArrayList<String> poster, ArrayList<String> name, ArrayList<Integer> id) {
//        rv_movieDB = (RecyclerView)findViewById(R.id.rv_movieDB_search);
//        rv_movieDB.setHasFixedSize(true);
//        Log.e("RV INSTANCE","SETLM");
//        //rvlm_movieDB = new LinearLayoutManager(this);
//        rvlm_movieDB = new GridLayoutManager(context,2);
//        rv_movieDB.setLayoutManager(rvlm_movieDB);
//
//        poster.clear();
//        name.clear();
//        id.clear();
//        rva_movieDB.notifyDataSetChanged();
//

        rva_movieDB = new MovieDBRecyclerAdapter(context, poster, name, id);
        rv_movieDB.setAdapter(rva_movieDB);
	}

//	class ProgressBar extends AsyncTask<Void, Void, Void>{
//        ProgressDialog pDialog = new ProgressDialog(MovieDBActivity.this);
//        @Override
//        protected void onPreExecute(){
//            pDialog.setTitle("Searching for movies");
//            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            pDialog.setProgress(0);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            showRecyclerViewContent(mContext, movieDB_poster, movieDB_name);
//            try{
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if(isCancelled()){
//                pBar.cancel(true);
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result){
//            pDialog.dismiss();
//        }
//    }
}