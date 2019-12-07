package com.example.consumerapp;

import com.example.consumerapp.model.ListMovie;
import com.example.consumerapp.model.ListTvShow;

import java.util.ArrayList;

public interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<ListMovie> movies);
    void postExecuteTv(ArrayList<ListTvShow> tvShows);
}
