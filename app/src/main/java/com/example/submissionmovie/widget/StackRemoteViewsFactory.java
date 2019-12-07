package com.example.submissionmovie.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.submissionmovie.R;
import com.example.submissionmovie.db.DatabaseContract;
import com.example.submissionmovie.model.ListMovie;

import java.util.ArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ListMovie> listMovies = new ArrayList<>();
    private Cursor cursor1,cursor2;
    private final Context mContext;
    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor1 != null){
            cursor1.close();
        }if (cursor2 != null){
            cursor2.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        cursor1 = mContext.getContentResolver().query(DatabaseContract.FavoriteColumns.CONTENT_URI,null,null,null,null);
        cursor2 = mContext.getContentResolver().query(DatabaseContract.FavoriteTVColumns.CONTENT_URI,
                null, null, null, null);

        Binder.restoreCallingIdentity(identityToken);

        listMovies.clear();
        while (cursor1.moveToNext()){
            int id = cursor1.getInt(cursor1.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_ID));
            String title = cursor1.getString(cursor1.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
            String description = cursor1.getString(cursor1.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DESCRIPTION));
            String pic = cursor1.getString(cursor1.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.IMG));
            listMovies.add(new ListMovie(id,title,description,pic));

        }

        while (cursor2.moveToNext()){
            int id = cursor2.getInt(cursor2.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.TV_ID));
            String title = cursor2.getString(cursor2.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.TITLE));
            String description = cursor2.getString(cursor2.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.DESCRIPTION));
            String pic = cursor2.getString(cursor2.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.IMG));
            listMovies.add(new ListMovie(id,title,description,pic));

        }

    }

    @Override
    public void onDestroy() {
    listMovies.clear();
    }

    @Override
    public int getCount() {
        return listMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(mContext.getApplicationContext())
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185"+ listMovies.get(position).getPic())
                    .submit(512, 512)
                    .get();

            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
