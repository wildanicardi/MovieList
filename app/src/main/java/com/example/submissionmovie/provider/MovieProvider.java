package com.example.submissionmovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.submissionmovie.db.DatabaseContract;
import com.example.submissionmovie.db.FavoriteHelper;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV = 3;
    private static final int TV_ID = 4;
    private FavoriteHelper favoriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_FAVORITE,MOVIE);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY,
                DatabaseContract.TABLE_FAVORITE+ "/#",
                MOVIE_ID);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_TVFAVORITE,TV);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY,
                DatabaseContract.TABLE_TVFAVORITE+ "/#",
                TV_ID);
    }
    public MovieProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        Uri tabelUri = DatabaseContract.FavoriteColumns.CONTENT_URI;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = favoriteHelper.deleteMovie(Integer.parseInt(uri.getLastPathSegment()));
                break;
            case TV_ID:
                deleted = favoriteHelper.deleteTv(Integer.parseInt(uri.getLastPathSegment()));
                tabelUri = DatabaseContract.FavoriteTVColumns.CONTENT_URI;
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(tabelUri, null);
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        Uri tabelUri = DatabaseContract.FavoriteColumns.CONTENT_URI;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = favoriteHelper.insertMovie(values);
                break;
            case TV:
                added = favoriteHelper.insertTvshow(values);
                tabelUri = DatabaseContract.FavoriteTVColumns.CONTENT_URI;
                break;
            default:
                added = 0;
                break;
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(tabelUri, null);
        }
        return Uri.parse(DatabaseContract.FavoriteColumns.CONTENT_URI + "/" + added);
    }

    @Override
    public boolean onCreate() {
         favoriteHelper = new FavoriteHelper(getContext());
         favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteHelper.queryAllMovie();
                break;
            case MOVIE_ID:
                cursor = favoriteHelper.queryByIdMovie(Integer.parseInt(uri.getLastPathSegment()));
                break;
            case TV:
                cursor = favoriteHelper.queryAllTv();
                break;
            case TV_ID:
                cursor = favoriteHelper.queryByIdTv(Integer.parseInt(uri.getLastPathSegment()));
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
