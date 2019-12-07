package com.example.submissionmovie.view;


import android.os.Bundle;
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
import com.example.submissionmovie.adapter.RecyclerViewAdapter;
import com.example.submissionmovie.model.ListMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements SearchView.OnQueryTextListener{

    private View view;
    private ProgressBar loading;
    private RecyclerView recyclerView;
    private ArrayList<ListMovie> list;
    private SearchView searchView;

    private static final String API_URL = "https://api.themoviedb.org/3/discover/movie?api_key=1645bb1963fcc609302208aacc323e34&language=en-US";
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

                        ListMovie movie = new ListMovie(array.getJSONObject(i));

                        JSONObject data = array.getJSONObject(i);
                        movie.setTitle(data.getString("title"));
                        movie.setOverview(data.getString("overview"));
                        movie.setPic(data.getString("poster_path"));

                        list.add(movie);

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(list);
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

         String API_SEARCHING = "https://api.themoviedb.org/3/search/movie?api_key=1645bb1963fcc609302208aacc323e34&language=en-US&query="+text;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_SEARCHING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){

                        ListMovie movie = new ListMovie(array.getJSONObject(i));

                        JSONObject data = array.getJSONObject(i);
                        movie.setTitle(data.getString("title"));
                        movie.setOverview(data.getString("overview"));
                        movie.setPic(data.getString("poster_path"));

                        list.add(movie);

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(list);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    loading.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
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
    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie,container,false);
        recyclerView = view.findViewById(R.id.list_movie);
        loading = view.findViewById(R.id.progresbar);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        list = new ArrayList<>();

        addItem();

        return view;
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
