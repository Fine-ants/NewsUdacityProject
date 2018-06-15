package com.jordanhuus.www.newsudacityproject.Articles.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.jordanhuus.www.newsudacityproject.Articles.Model.News;
import com.jordanhuus.www.newsudacityproject.Utils;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 5/18/2018.
 */

public class ArticlesLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String newsCategory;
    private boolean isNewsCategory;

    public ArticlesLoader(@NonNull Context context, @NonNull String newsCategory, @NonNull boolean isNewsCategory) {
        super(context);
        this.newsCategory = newsCategory;
        this.isNewsCategory = isNewsCategory;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        return Utils.fetchNewsData(newsCategory, isNewsCategory);
    }
}
