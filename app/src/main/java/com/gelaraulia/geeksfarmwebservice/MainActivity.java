package com.gelaraulia.geeksfarmwebservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_json = (Button)findViewById(R.id.btn_toJson);
        Button btn_async = (Button)findViewById(R.id.btn_toAsyncTask);
        Button btn_http = (Button)findViewById(R.id.btn_toHttpUrlConn);
        Button btn_gson = (Button)findViewById(R.id.btn_toGson);
        Button btn_retrofit =(Button)findViewById(R.id.btn_toRetrofit);
        Button btn_movieDB =(Button)findViewById(R.id.btn_toMovieDB);
        btn_json.setOnClickListener(this);
        btn_async.setOnClickListener(this);
        btn_http.setOnClickListener(this);
        btn_gson.setOnClickListener(this);
        btn_retrofit.setOnClickListener(this);
        btn_movieDB.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_toJson:
                intent = new Intent(MainActivity.this,JSONActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toAsyncTask:
                intent = new Intent(MainActivity.this,AsyncTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toHttpUrlConn:
                intent = new Intent(MainActivity.this,HttpUrlConnActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toGson:
                intent = new Intent(MainActivity.this,GSONActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toRetrofit:
                intent = new Intent(MainActivity.this,RetrofitActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toMovieDB:
                intent = new Intent(MainActivity.this,MovieDBActivity.class);
                startActivity(intent);
                break;
        }
    }
}
