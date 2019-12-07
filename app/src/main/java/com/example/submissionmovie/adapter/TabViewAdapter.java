package com.example.submissionmovie.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.submissionmovie.view.MovieFavoriteFragment;
import com.example.submissionmovie.view.TvShowFavoriteFragment;

public class TabViewAdapter extends FragmentPagerAdapter {
    private Context myContext;
    private int totalTabs;

    public TabViewAdapter(FragmentManager fm, Context myContext, int totalTabs) {
        super(fm);
        this.myContext = myContext;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                MovieFavoriteFragment movieFavoriteFragment = new MovieFavoriteFragment();
                return movieFavoriteFragment;
            case 1:
                TvShowFavoriteFragment tvShowFavoriteFragment = new TvShowFavoriteFragment();
                return tvShowFavoriteFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
