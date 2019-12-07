package com.example.submissionmovie.db;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submissionmovie.R;
import com.example.submissionmovie.model.ListMovie;
import com.example.submissionmovie.widget.ImageBannerWidget;

import java.util.ArrayList;

import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.IMG;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.MOVIE_ID;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteTVColumns.TV_ID;
import static com.example.submissionmovie.db.DatabaseContract.TABLE_FAVORITE;
import static com.example.submissionmovie.db.DatabaseContract.TABLE_TVFAVORITE;


public class FavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static final String DATABASE_TABLE_TV = TABLE_TVFAVORITE;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;

    private static SQLiteDatabase database;
    private Context context;
    public FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public static FavoriteHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }
    public void close(){
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    private void updateWidget(){
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int appWidgetIds[]=manager.getAppWidgetIds(new ComponentName(context, ImageBannerWidget.class));
        manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
    }
    public ArrayList<ListMovie> getallMovies(){
        ArrayList<ListMovie> arrayList = new ArrayList<>();

        Cursor cursor  = database.query(DATABASE_TABLE, null,null,
                null,
                null,
                null,
                MOVIE_ID + " ASC",
                null);
        cursor.moveToFirst();
        ListMovie listMovie;

        if (cursor.getCount() > 0){
            do {
                listMovie = new ListMovie();
                listMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                listMovie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                listMovie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                listMovie.setPic(cursor.getString(cursor.getColumnIndexOrThrow(IMG)));

                arrayList.add(listMovie);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        //cursor.close();
        return arrayList;
    }
    public Cursor queryAllMovie(){

        //open();
        Cursor cursor  = database.query(DATABASE_TABLE, null,null,
                null,
                null,
                null,
                MOVIE_ID + " ASC",
                null);

        return cursor;
    }

    public Cursor queryAllTv(){
    //open();
        Cursor cursor  = database.query(DATABASE_TABLE_TV, null,null,
                null,
                null,
                null,
                TV_ID + " ASC",
                null);

        return cursor;
    }

    public Cursor queryByIdMovie(int id) {
        //open();
        return database.query(
                DATABASE_TABLE,
                null,
                MOVIE_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

    }
    public Cursor queryByIdTv(int id) {
        //open();
        return database.query(
                DATABASE_TABLE_TV,
                null,
                TV_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
    }

    public long insertMovie(ContentValues contentValues){
        updateWidget();

        return database.insert(DATABASE_TABLE,null,contentValues);
    }
    public long insertTvshow(ContentValues contentValues){
        updateWidget();

        return database.insert(DATABASE_TABLE_TV,null,contentValues);
    }
    public int deleteMovie(int id) {
        updateWidget();
        return database.delete(TABLE_FAVORITE, MOVIE_ID+ " = '" + id + "'", null);
    }
    public int deleteTv(int id) {
        updateWidget();
        return database.delete(TABLE_TVFAVORITE, TV_ID + " = '" + id + "'", null);
    }

}
