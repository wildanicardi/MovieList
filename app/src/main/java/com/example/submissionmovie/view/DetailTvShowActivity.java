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
import com.example.submissionmovie.db.DatabaseContract;
import com.example.submissionmovie.db.FavoriteHelper;
import com.example.submissionmovie.model.ListTvShow;

import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.IMG;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.submissionmovie.db.DatabaseContract.FavoriteTVColumns.TV_ID;

public class DetailTvShowActivity extends AppCompatActivity {

    private TextView judul, description;
    private ImageView gambar;
    private String urlImage = "https://image.tmdb.org/t/p/w154/";
    public static final String EXTRA_MOVIE_FAVORITE = "extra_movie_favorite";
    private boolean isEdit = false;
    private ListTvShow tvShow;
    boolean isFavorite = false;
    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        judul = findViewById(R.id.text_judul);
        description = findViewById(R.id.text_description);
        gambar = findViewById(R.id.gambar);
        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();
        tvShow = getIntent().getParcelableExtra("TVSHOW");
        CekFavorite();
        if (tvShow != null) {
            Glide.with(getApplicationContext())
                    .load(urlImage + tvShow.getPic())
                    .into(gambar);
            judul.setText(tvShow.getTitle());
            description.setText(tvShow.getOverview());
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        if (isFavorite) {
            menu.getItem(0).setIcon(R.drawable.ic_action_close);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            String name = judul.getText().toString().trim();
            String deskripsi = description.getText().toString().trim();
            String photo = tvShow.getPic();
            if (isFavorite) {
                Uri id = Uri.parse(DatabaseContract.FavoriteTVColumns.CONTENT_URI+"/"+tvShow.getId());
                getContentResolver().delete(id,null,null);
                    Toast.makeText(DetailTvShowActivity.this, "Berhasil Terhapus", Toast.LENGTH_SHORT).show();
                    isFavorite = false;
                    item.setIcon(R.drawable.ic_favorite);
                    finish();

            } else {
                tvShow.setTitle(name);
                tvShow.setOverview(deskripsi);
                tvShow.setPic(photo);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOVIE_FAVORITE, tvShow);

                if (!isEdit) {
                    ContentValues args = new ContentValues();
                    args.put(TV_ID, tvShow.getId());
                    args.put(TITLE, tvShow.getTitle());
                    args.put(DESCRIPTION, tvShow.getOverview());
                    args.put(IMG, tvShow.getPic());

                    getContentResolver().insert(DatabaseContract.FavoriteTVColumns.CONTENT_URI,args);
                    Toast.makeText(DetailTvShowActivity.this, "Berhasil menambah data", Toast.LENGTH_SHORT).show();
                    isFavorite = true;
                    item.setIcon(R.drawable.ic_action_close);
                    finish();
                }
            }
        }
        return false;
    }

    public void CekFavorite() {
        Cursor cursor = favoriteHelper.queryByIdTv(tvShow.getId());
        isFavorite = cursor.moveToFirst() ? true : false;
    }
}
