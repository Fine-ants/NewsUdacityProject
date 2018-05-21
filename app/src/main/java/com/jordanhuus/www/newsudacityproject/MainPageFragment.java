package com.jordanhuus.www.newsudacityproject;

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
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 5/19/2018.
 */

public class MainPageFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<News>>{
    private LoaderManager loaderManager;
    private static final int NEWS_LOADER_ID = 1;
    private String newsCategory;
    private View root;

    public static MainPageFragment newInstance(){
        return new MainPageFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_main, container, false);

        // Retrieve newsCategory from bundle
        newsCategory = "politics"; /* Placeholder */

        // Restart loader
        loaderManager = getActivity().getSupportLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        return root;
    }

    public void chooseNewCategory(String newsCategory){
        this.newsCategory = newsCategory;

        if(getActivity() == null){
            Log.i("debugtag", "parent activity null");
        }

//        try {
//            loaderManager = getActivity().getSupportLoaderManager();
//            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
    }

    private void updateUi(ArrayList<News> articles){
        // List View adapter
        NewsAdapter adapter = new NewsAdapter(getActivity(), R.layout.news_list_item, articles);

        // Set adapter to List View
        ListView newsListView = root.findViewById(R.id.news_items_list_view);
        newsListView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(getActivity(), newsCategory);
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
