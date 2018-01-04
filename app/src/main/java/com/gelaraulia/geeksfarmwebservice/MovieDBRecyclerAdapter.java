package com.gelaraulia.geeksfarmwebservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by G_Aulia on 20 Des 2017.
 */

public class MovieDBRecyclerAdapter extends RecyclerView.Adapter<MovieDBRecyclerAdapter.ViewHolder> {
    private String[] moviePoster;
    private String[] movieName;

    private ArrayList<String> moviePosterAL = new ArrayList<String>();
    private ArrayList<String> movieNameAL = new ArrayList<String>();
    private ArrayList<Integer> movieIdAL = new ArrayList<Integer>();

    Context mContext;

    public MovieDBRecyclerAdapter(Context context, ArrayList<String> posterUrl, ArrayList<String> movieDBName, ArrayList<Integer> MovieDBId) {
        mContext = context;
        moviePosterAL = posterUrl;
        movieNameAL = movieDBName;
        movieIdAL = MovieDBId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_content;
        public TextView tv_content;
        public TextView tv_noPoster;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_content = (ImageView)itemView.findViewById(R.id.iv_movieDB_content);
            tv_content = (TextView)itemView.findViewById(R.id.tv_movieDB_content);
            tv_noPoster = (TextView)itemView.findViewById(R.id.tv_movieDB_noPoster);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.content_movie_db_search, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //String pathChecker = (String)moviePosterAL.get(position);
//        if(pathChecker!="null"){
        if((String)moviePosterAL.get(position)!= null){
            Picasso.with(mContext).load((String)moviePosterAL.get(position)).into(holder.iv_content);
            holder.tv_noPoster.setText("");
        }else{
            Picasso.with(mContext).load("@drawable/ic_launcher_background").into(holder.iv_content);
            holder.tv_noPoster.setText("No Poster Available");
        }

        holder.tv_content.setText((String)movieNameAL.get(position));

        holder.iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MovieDetailActivity.class);
                intent.putExtra("id", movieIdAL.get(position));
                intent.putExtra("title", movieNameAL.get(position));
                intent.putExtra("poster", moviePosterAL.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MovieDetailActivity.class);
                intent.putExtra("id", movieIdAL.get(position));
                intent.putExtra("title", movieNameAL.get(position));
                intent.putExtra("poster", moviePosterAL.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviePosterAL.size();
    }

//    public void clear(){
//        int size = this.movieIdAL.size();
//        int size2 = this.movieNameAL.size();
//        int size3 = this.moviePosterAL.size();
//        if (size > 0) {
//            for (int i = 0; i< size; i++){
//                this.movieIdAL.remove(0);
//            }
//            this.notifyItemRangeRemoved(0, size);
//        }
//
//        if (size2 > 0) {
//            for (int i = 0; i< size2; i++){
//                this.movieNameAL.remove(0);
//            }
//            this.notifyItemRangeRemoved(0, size2);
//        }
//
//        if (size3 > 0) {
//            for (int i = 0; i< size3; i++){
//                this.moviePosterAL.remove(0);
//            }
//            this.notifyItemRangeRemoved(0, size3);
//        }
//    }
}