package com.example.submissionmovie;

import com.example.submissionmovie.model.ListMovie;
import com.example.submissionmovie.model.ListTvShow;

import java.util.ArrayList;

public interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<ListMovie> movies);
    void postExecuteTv(ArrayList<ListTvShow> tvShows);
}
