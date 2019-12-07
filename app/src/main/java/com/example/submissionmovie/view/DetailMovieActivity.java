package com.example.submissionmovie.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.submissionmovie.R;
import com.example.submissionmovie.db.FavoriteHelper;
import com.example.submissionmovie.model.ListMovie;
import com.example.submissionmovie.provider.MovieProvider;

import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.IMG;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.MOVIE_ID;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.TITLE;

public class DetailMovieActivity extends AppCompatActivity {

    private TextView judul, description;
    private ImageView gambar;
    public String urlImage = "https://image.tmdb.org/t/p/w154/";
    public static final String EXTRA_MOVIE_FAVORITE = "extra_movie_favorite";
    public boolean isEdit = false;
    private ListMovie movie;
    public MovieProvider movieProvider;
    boolean isFavorite = false;
    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        judul = findViewById(R.id.text_judul);
        description = findViewById(R.id.text_description);
        gambar = findViewById(R.id.gambar);
        //movieProvider = new MovieProvider();
        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        movie = getIntent().getParcelableExtra("MOVIE");
        CekFavorite();
        if (movie != null) {
            Glide.with(getApplicationContext())
                    .load(urlImage + movie.getPic())
                    .into(gambar);
            judul.setText(movie.getTitle());
            description.setText(movie.getOverview());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        if (isFavorite) {
            menu.getItem(0).setIcon(R.drawable.ic_action_close);
        }else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favorite) {

                String name = judul.getText().toString().trim();
                String deskripsi = description.getText().toString().trim();
                String photo = movie.getPic();
                if (isFavorite){
                    Uri id = Uri.parse(CONTENT_URI+"/"+movie.getId());
                    getContentResolver().delete(id,null,null);
                        Toast.makeText(DetailMovieActivity.this,"Berhasil Terhapus",Toast.LENGTH_SHORT).show();
                        isFavorite = false;
                        item.setIcon(R.drawable.ic_favorite);
                        finish();

                }else{
                    movie.setTitle(name);
                    movie.setOverview(deskripsi);
                    movie.setPic(photo);

                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_MOVIE_FAVORITE, movie);

                    if (!isEdit) {
                        ContentValues args = new ContentValues();
                        args.put(MOVIE_ID,movie.getId());
                        args.put(TITLE,movie.getTitle());
                        args.put(DESCRIPTION,movie.getOverview());
                        args.put(IMG,movie.getPic());

                        getContentResolver().insert(CONTENT_URI,args);
                            Toast.makeText(DetailMovieActivity.this, "Berhasil menambah data", Toast.LENGTH_SHORT).show();
                            isFavorite = true;
                            item.setIcon(R.drawable.ic_action_close);
                            finish();
                    }
                }

            }
            return false;
        }

    public void CekFavorite() {
        Cursor cursor = favoriteHelper.queryByIdMovie(movie.getId());
        isFavorite = cursor.moveToFirst();
    }
}
