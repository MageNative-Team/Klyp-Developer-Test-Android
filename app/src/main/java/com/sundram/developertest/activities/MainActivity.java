package com.sundram.developertest.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.sundram.developertest.R;
import com.sundram.developertest.adapter.MovieAdapter;
import com.sundram.developertest.datamodel.Movie;
import com.sundram.developertest.datamodel.MovieResult;
import com.sundram.developertest.moviewapiservice.MovieAPIService;
import com.sundram.developertest.utils.APIClient;
import com.sundram.developertest.utils.ConnectionUtils;
import com.sundram.developertest.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Map<String, Integer> colors;
    private TextInputEditText search_txt_input_et;
    private RecyclerView movie_rv;
    private MovieAdapter adapter;
    private ProgressBar pb;
    List<Movie> data;
    private TextView nothing_found_tv;
    private ImageView search_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            init();
            prepareLocalColors();
            setApiOnImeiActionSearch();
            setWhenBtnClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //initialize the views
    private void init() {
        search_txt_input_et = findViewById(R.id.search_txt_input_et);
        movie_rv = findViewById(R.id.movie_rv);
        nothing_found_tv = findViewById(R.id.nothing_found_tv);
        search_btn = findViewById(R.id.search_iv);
        pb = findViewById(R.id.pb);
        nothing_found_tv.setVisibility(View.VISIBLE);
        nothing_found_tv.setText(R.string.initial_msg);
    }

    private void prepareLocalColors() {
        colors = new HashMap<>();
        colors.put("red", Color.parseColor("#FF0000"));
        colors.put("green", Color.parseColor("#008000"));
        colors.put("yellow", Color.parseColor("#FFFF00"));
        colors.put("blue", Color.parseColor("#0000FF"));
    }

    private void setApiOnImeiActionSearch(){
        try {
            search_txt_input_et.setOnEditorActionListener((v, actionId, keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (v.getText().toString().trim().length() >= 3) {
                        getMovieData(v.getText().toString().trim());
                        return true;
                    } else {
                        Toast.makeText(MainActivity.this,getResources().getString(R.string.mini_length_msg),Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setWhenBtnClick() {
        search_btn.setOnClickListener(view -> {
            try {
                if (search_txt_input_et.getText().toString().trim().length() >= 3) {
                    getMovieData(search_txt_input_et.getText().toString().trim());
                } else {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.mini_length_msg),Toast.LENGTH_SHORT).show();

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void getMovieData(String keyword) {
       try {
           if (ConnectionUtils.ConnectivityCheck(MainActivity.this)) {
               pb.setVisibility(View.VISIBLE);
               movie_rv.setVisibility(View.GONE);
               data = new ArrayList<>();
               MovieAPIService apiService = APIClient.getClient().create(MovieAPIService.class);
               Call<MovieResult> result = apiService.getAllMovieByKeyword(ConstantUtils.API_KEY, ConstantUtils.LANG, keyword);
               result.enqueue(new Callback<MovieResult>() {
                   @Override
                   public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                       List<Movie> data = response.body().getMovie_list();
                       if (data.size()>0){
                           pb.setVisibility(View.GONE);
                           movie_rv.setVisibility(View.VISIBLE);
                           nothing_found_tv.setVisibility(View.GONE);

                           movie_rv.setHasFixedSize(true);
                           movie_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                           adapter = new MovieAdapter();
                           adapter.setData(MainActivity.this, data, colors);
                           movie_rv.setAdapter(adapter);
                           adapter.notifyDataSetChanged();
                       }else{
                           pb.setVisibility(View.GONE);
                           nothing_found_tv.setVisibility(View.VISIBLE);
                           nothing_found_tv.setText(R.string.nothing_found);
                       }
                   }

                   @Override
                   public void onFailure(Call<MovieResult> call, Throwable t) {
                       Log.w("ERROR", t.toString());
                   }
               });
           } else {
               nothing_found_tv.setVisibility(View.GONE);
               Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}