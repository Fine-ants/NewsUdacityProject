package com.jordanhuus.www.newsudacityproject.Articles.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import com.jordanhuus.www.newsudacityproject.Articles.ArticlesFragment;
import com.jordanhuus.www.newsudacityproject.Articles.Model.ArticlesLoader;
import com.jordanhuus.www.newsudacityproject.Articles.Model.News;
import com.jordanhuus.www.newsudacityproject.MainActivity;
import com.jordanhuus.www.newsudacityproject.R;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 6/15/2018.
 */

public class ArticlesPresenter implements LoaderManager.LoaderCallbacks<ArrayList<News>>{

    private static final int NEWS_LOADER_ID = 1;
    private MainActivity mainActivity;
    private String newsCategory;
    private boolean isNewsCategory;
    private View root;
    private LoaderManager loaderManager;
    private ArticlesFragment articlesFragment;


    public ArticlesPresenter(ArticlesFragment articlesFragment, MainActivity mainActivity) {
        this.articlesFragment = articlesFragment;
        this.mainActivity = mainActivity;
    }

    public View inflateArticlesView(View root){
        this.root = root;

        // Init loader manager
        loaderManager = mainActivity.getSupportLoaderManager();

        // Default news search result
        this.chooseNewsCategory("us-politics/us-politics", false);

        return root;
    }


    /**
     * @param newsCategory is the user-chosen category
     */
    public void chooseNewsCategory(String newsCategory, boolean isCategory){
        this.newsCategory = newsCategory;
        this.isNewsCategory = isNewsCategory;

        // Check for network connectivity
        TextView noConnectionMessage= root.findViewById(R.id.activity_main_no_connection);
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo()==null || !connectivityManager.getActiveNetworkInfo().isConnected()){
            // Display message that no connection was found
            TextView emptyListView = root.findViewById(R.id.no_articles_found);
            emptyListView.setVisibility(View.INVISIBLE);
            noConnectionMessage.setText("No Internet Connection");

            return;
        }else{
            noConnectionMessage.setText("");
        }

        try {
            loaderManager = mainActivity.getSupportLoaderManager();
            loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ArticlesLoader(mainActivity, newsCategory, isNewsCategory);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        if(data!=null) {
            articlesFragment.updateUi(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        articlesFragment.updateUi(new ArrayList<News>());
    }
}


