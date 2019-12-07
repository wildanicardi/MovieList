package com.example.consumerapp.view;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerapp.LoadFavoriteCallback;
import com.example.consumerapp.R;
import com.example.consumerapp.adapter.RecyclerViewAdapter;
import com.example.consumerapp.db.DatabaseContract;
import com.example.consumerapp.model.ListMovie;
import com.example.consumerapp.model.ListTvShow;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment  implements View.OnClickListener, LoadFavoriteCallback {
    View MovieFavorite;
    private RecyclerView rvmovie;
    private ProgressBar progressBar;
    private RecyclerViewAdapter recyclerViewAdapter;


    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        rvmovie.setAdapter(recyclerViewAdapter);
        ArrayList<ListMovie> list = new ArrayList<>();

        Cursor cursor = getActivity().getContentResolver().query(DatabaseContract.FavoriteColumns.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DESCRIPTION));
            String pic = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.IMG));
            list.add(new ListMovie(id,title,description,pic));

      }

//        if(cursor != null && !cursor.isClosed()){
//            cursor.close();
//        }
        recyclerViewAdapter.setListMovies(list);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MovieFavorite = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        rvmovie = MovieFavorite.findViewById(R.id.list_movieFavorite);
        rvmovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvmovie.setHasFixedSize(true);

        progressBar = MovieFavorite.findViewById(R.id.progresbarMovie);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity());

        return MovieFavorite;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecuteTv(ArrayList<ListTvShow> tvShows) {

    }

    @Override
    public void postExecute(ArrayList<ListMovie> movies) {

    }


}



