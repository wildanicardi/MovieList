package com.example.consumerapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.consumerapp.R;
import com.example.consumerapp.model.ListTvShow;
import com.example.consumerapp.view.DetailTvShowActivity;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.MyViewHolder> {
    private ArrayList<ListTvShow> listTvShows = new ArrayList<>();
    private Activity activity;

    public TvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListTvShows(ArrayList<ListTvShow> listTvShows) {

        this.listTvShows = listTvShows;
        notifyDataSetChanged();
    }

    public TvShowAdapter(ArrayList<ListTvShow> listTvShows) {
        this.listTvShows = listTvShows;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final ListTvShow tvShow = listTvShows.get(i);
        String urlImage = "https://image.tmdb.org/t/p/w92/";

        myViewHolder.tv_judul.setText(tvShow.getTitle());
        myViewHolder.tv_deskripsi.setText(tvShow.getOverview());
        Glide.with(myViewHolder.itemView.getContext())
                .load(urlImage+tvShow.getPic())
                .into(myViewHolder.img_photo);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myViewHolder.itemView.getContext(), DetailTvShowActivity.class);
                intent.putExtra("TVSHOW",tvShow);
                myViewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShows.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_judul,tv_deskripsi;
        private ImageView img_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_deskripsi = itemView.findViewById(R.id.tv_deskripsi);
            img_photo = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
