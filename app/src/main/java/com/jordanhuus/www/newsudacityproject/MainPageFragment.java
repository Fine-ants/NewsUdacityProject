package com.jordanhuus.www.newsudacityproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 5/19/2018.
 */

public class MainPageFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<News>>{
    private LoaderManager loaderManager;
    private static final int NEWS_LOADER_ID = 1;
    private String newsCategory;
    private boolean isNewsCategory;
    private View root;
    private MainActivity mainActivity;

    public static MainPageFragment newInstance(){
        return new MainPageFragment();
    }

    /**
     * Retrieves global MainActivity object
     * @param context from MainActivitys
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainActivity = (MainActivity) context;
        mainActivity.setMainPageFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_main, container, false);

        // Retrieve newsCategory from bundle
        newsCategory = "politics"; /* Placeholder */

        // Check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo()==null || !connectivityManager.getActiveNetworkInfo().isConnected()){
            // Display message that no connection was found
            TextView noConnectionMessage = root.findViewById(R.id.activity_main_no_connection);
            noConnectionMessage.setText("No Internet Connection");

            return root;
        }

        // Restart loader
        loaderManager = mainActivity.getSupportLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        return root;
    }


    /**
     * Retrieves data fromm chosen category and displays result
     * @param newsCategory global variable; users chosen category from CategoriesFragment; used to fetch JSON
     */
    public void chooseNewCategory(String newsCategory, boolean isNewsCategory){
        this.newsCategory = newsCategory;
        this.isNewsCategory = isNewsCategory;

        try {
            loaderManager = mainActivity.getSupportLoaderManager();
            loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void updateUi(final ArrayList<News> articles){
        // List View adapter
        NewsAdapter adapter = new NewsAdapter(mainActivity, R.layout.news_list_item, articles);

        // Set adapter to List View
        ListView newsListView = root.findViewById(R.id.news_items_list_view);
        newsListView.setAdapter(adapter);

        // User clicks List Item(news article)
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Retrieve article URL
                String articleUrl = articles.get(i).getWebUrl();

                // Open Chrome to article URL
                Intent openUrl = new Intent(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(articleUrl));
                startActivity(openUrl);
            }
        });
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(mainActivity, newsCategory, isNewsCategory);
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
