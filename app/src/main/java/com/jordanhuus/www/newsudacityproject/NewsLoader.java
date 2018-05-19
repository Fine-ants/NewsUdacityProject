package com.jordanhuus.www.newsudacityproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 5/18/2018.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String newsCategory;

    public NewsLoader(@NonNull Context context, @NonNull String newsCategory) {
        super(context);
        this.newsCategory = newsCategory;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        return Utils.fetchNewsData(newsCategory);
    }
}
