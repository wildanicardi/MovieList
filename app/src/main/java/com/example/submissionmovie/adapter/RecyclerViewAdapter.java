package com.example.submissionmovie.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submissionmovie.R;
import com.example.submissionmovie.model.ListMovie;
import com.example.submissionmovie.view.DetailMovieActivity;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<ListMovie> listMovies = new ArrayList<>();

    public void setListMovies(ArrayList<ListMovie> listMovies) {
        this.listMovies = listMovies;
        notifyDataSetChanged();
    }


    public RecyclerViewAdapter(ArrayList<ListMovie> listMovies) {
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie,viewGroup,false);
        MyViewHolder viewHolder  = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final ListMovie movie = listMovies.get(i);
        final String urlImage = "http://image.tmdb.org/t/p/w92";

        myViewHolder.tv_judul.setText(movie.getTitle());
        myViewHolder.tv_deskripsi.setText(movie.getOverview());
        Glide.with(myViewHolder.itemView.getContext())
                .load(urlImage+movie.getPic())
                .into(myViewHolder.img_photo);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myViewHolder.itemView.getContext(), DetailMovieActivity.class);
                intent.putExtra("MOVIE",movie);
                myViewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_judul,tv_deskripsi;
        private ImageView img_photo;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_deskripsi = itemView.findViewById(R.id.tv_deskripsi);
            img_photo = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
