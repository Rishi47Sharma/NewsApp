package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.adapter.NewsRecyclerViewAdapter;
import com.example.newsapp.controller.MySingleton;
import com.example.newsapp.model.News;
import com.example.newsapp.util.NewsItemClicked;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsItemClicked {
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    // private String url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
    private String url = "https://saurav.tech/NewsAPI/everything/cnn.json";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData();
        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(this);
        recyclerView.setAdapter(newsRecyclerViewAdapter);


    }
    private void fetchData(){
       /* val queue = Volley.newRequestQueue(this)*/
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {

                        JSONArray jsonArray = response.getJSONArray("articles");

                        ArrayList<News> newsArrayList = new ArrayList<>();
                        for(int i =0 ;i<jsonArray.length();i++){
                            JSONObject newsJsonObject= jsonArray.getJSONObject(i);

                            News news = new News(newsJsonObject.getString("title"),newsJsonObject.getString("author"),newsJsonObject.getString("url"),newsJsonObject.getString("urlToImage"));
                            newsArrayList.add(news);

                        }
                        newsRecyclerViewAdapter.update(newsArrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }, error -> {

                    /*@Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["User-Agent"] = "Mozilla/5.0"
                        return params*/


                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }

    @Override
    public void onItemsClicked(News items) {
        String url = items.url;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

    }
}