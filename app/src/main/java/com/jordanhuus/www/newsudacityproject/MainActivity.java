package com.jordanhuus.www.newsudacityproject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>>{
    private static final int NEWS_LOADER_ID = 1;
    private String newsCategory;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init LoaderManager
        loaderManager = getSupportLoaderManager();
        if(savedInstanceState!=null){
            loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
        }

        // Setup category onClick
        Button technology = findViewById(R.id.technology_button);
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsCategory = "technology";
                loaderManager.restartLoader(NEWS_LOADER_ID, null, MainActivity.this);
            }
        });

        Button politics = findViewById(R.id.politics_button);
        politics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsCategory = "politics";
                loaderManager.restartLoader(NEWS_LOADER_ID, null, MainActivity.this);
            }
        });

        Button business = findViewById(R.id.business_button);
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsCategory = "business";
                loaderManager.restartLoader(NEWS_LOADER_ID, null, MainActivity.this);
            }
        });

    }

    private void updateUi(ArrayList<News> articles){
        // List View adapter
        NewsAdapter adapter = new NewsAdapter(this, R.layout.news_list_item, articles);

        // Set adapter to List View
        ListView newsListView = findViewById(R.id.news_items_list_view);
        newsListView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(this, newsCategory);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        if(data!=null) {
            updateUi(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        updateUi(new ArrayList<News>());
    }
}

