package com.example.consumerapp.view;


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
import com.example.consumerapp.adapter.TvShowAdapter;
import com.example.consumerapp.model.ListMovie;
import com.example.consumerapp.model.ListTvShow;

import java.util.ArrayList;

;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment implements View.OnClickListener, LoadFavoriteCallback {

    View TvFavorite;
    private RecyclerView rvtvshow;
    private ProgressBar progressBar;
    private TvShowAdapter tvShowAdapter;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<ListTvShow> list = new ArrayList<>();
//        Cursor cursor = getActivity().getContentResolver().query(DatabaseContract.FavoriteTVColumns.CONTENT_URI, null, null, null, null);
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.TV_ID));
//            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.TITLE));
//            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.DESCRIPTION));
//            String pic = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.IMG));
//            list.add(new ListTvShow(id, title, description, pic));
//        }

        tvShowAdapter.setListTvShows(list);
        rvtvshow.setAdapter(tvShowAdapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TvFavorite = inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
        rvtvshow = TvFavorite.findViewById(R.id.list_tvFavorite);
        rvtvshow.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvtvshow.setHasFixedSize(true);
;
        progressBar = TvFavorite.findViewById(R.id.progresbarTv);

        tvShowAdapter = new TvShowAdapter(getActivity());
        return TvFavorite;
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
    public void postExecute(ArrayList<ListMovie> movies) {

    }

    @Override
    public void postExecuteTv(ArrayList<ListTvShow> tvShows) {
        progressBar.setVisibility(View.INVISIBLE);
        tvShowAdapter.setListTvShows(tvShows);
    }
}
