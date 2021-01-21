package com.google.newsapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.newsapp1.Model.Articles;
import com.google.newsapp1.Model.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    EditText etQuerry;
    Button btnSrch;
    final String API_KEY ="e1cbd96486414fad92cbad669d97a3f7";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        recyclerView = findViewById(R.id.recycleview);
        progressBar = findViewById(R.id.loader);
        etQuerry=findViewById(R.id.etQuery);
        btnSrch = findViewById(R.id.btnSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //progressBar.isEnabled();
        final String country = getCountry();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJSON("",country ,API_KEY);
            }
        });
        retrieveJSON("",country ,API_KEY);

        btnSrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   if(!etQuerry.getText().toString().equals("")){
                       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                           @Override
                           public void onRefresh() {
                               retrieveJSON(etQuerry.getText().toString(),country,API_KEY);
                           }
                       });
                       retrieveJSON(etQuerry.getText().toString(),country,API_KEY);
                   }else {
                       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                           @Override
                           public void onRefresh() {
                               retrieveJSON("",country,API_KEY);
                           }
                       });
                       retrieveJSON("",country,API_KEY);
                   }
            }
        });
    }

    public void retrieveJSON(String querry,String country , String apikey){

        swipeRefreshLayout.setRefreshing(true);
        Call<Headlines> call;
        if(!etQuerry.getText().toString().equals("")){
            call = ApiClient.getInstance().getApi().getEverything(querry,apikey);
        }else{
            call = ApiClient.getInstance().getApi().getHeadlines(country,apikey);
        }
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && Objects.requireNonNull(response.body()).getArticles()!=null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

                Toast.makeText(MainActivity.this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

}