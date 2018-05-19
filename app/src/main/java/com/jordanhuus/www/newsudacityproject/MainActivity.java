package com.jordanhuus.www.newsudacityproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Retrieve new articles
        ArrayList<News> articles = Utils.fetchNewsData("Politics");

        // List View adapter
        NewsAdapter adapter = new NewsAdapter(this, R.layout.news_list_item, articles);

        // Set adapter to List View
        ListView newsListView = findViewById(R.id.news_items_list_view);
        newsListView.setAdapter(adapter);
    }
}

