package com.example.submissionmovie.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_FAVORITE = "favorite";
    public static String TABLE_TVFAVORITE = "tvfavorite";
    public static final String AUTHORITY = "com.example.submissionmovie";
    private static final String SCHEME = "content";

    public static final class FavoriteColumns implements BaseColumns{
        public static String MOVIE_ID = "movie_id";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String IMG = "img";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
    }


    public static final class FavoriteTVColumns implements BaseColumns{
        public static String TV_ID = "tv_id";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String IMG = "img";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TVFAVORITE)
                .build();
    }

}
