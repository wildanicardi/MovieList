package com.example.submissionmovie.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.submissionmovie.R;
import com.example.submissionmovie.adapter.TvShowAdapter;
import com.example.submissionmovie.model.ListTvShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements SearchView.OnQueryTextListener{

    View tvShow;
    private RecyclerView recyclerView;
    private ProgressBar loading;
    private ArrayList<ListTvShow> list;
    private SearchView searchView;
    private static final String API_URL = "https://api.themoviedb.org/3/discover/tv?api_key=1645bb1963fcc609302208aacc323e34&language=en-US";

    private void addItem() {
        list.removeAll(list);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){

                        ListTvShow tvShow = new ListTvShow(array.getJSONObject(i));

                        JSONObject data = array.getJSONObject(i);
                        tvShow.setTitle(data.getString("name"));
                        tvShow.setOverview(data.getString("overview"));
                        tvShow.setPic(data.getString("poster_path"));

                        list.add(tvShow);

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    TvShowAdapter recyclerViewAdapter = new TvShowAdapter(list);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                addItem();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void searchView(String text){
        list.removeAll(list);

        String API_SEARCHING = "https://api.themoviedb.org/3/search/tv?api_key=1645bb1963fcc609302208aacc323e34&language=en-US&query="+text;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_SEARCHING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){

                        ListTvShow tvShow = new ListTvShow(array.getJSONObject(i));

                        JSONObject data = array.getJSONObject(i);
                        tvShow.setTitle(data.getString("name"));
                        tvShow.setOverview(data.getString("overview"));
                        tvShow.setPic(data.getString("poster_path"));
                        Log.i("title tv",data.getString("name"));

                        list.add(tvShow);

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    TvShowAdapter recyclerViewAdapter = new TvShowAdapter(list);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                addItem();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tvShow = inflater.inflate(R.layout.fragment_tv_show, container, false);
        recyclerView = tvShow.findViewById(R.id.list_tvshow);
        loading = tvShow.findViewById(R.id.progresbar);
        searchView = tvShow.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
        list = new ArrayList<>();

        addItem();
        return tvShow;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length()>2){
            recyclerView.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            searchView(newText);
        }else if (newText.length() == 0){
            recyclerView.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            addItem();
        }
        return false;
    }
}
