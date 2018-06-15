package com.jordanhuus.www.newsudacityproject.Articles;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jordanhuus.www.newsudacityproject.Articles.Model.ArticlesLoader;
import com.jordanhuus.www.newsudacityproject.Articles.Model.News;
import com.jordanhuus.www.newsudacityproject.MainActivity;
import com.jordanhuus.www.newsudacityproject.R;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 5/19/2018.
 */

public class ArticlesFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<News>>{
    private LoaderManager loaderManager;
    private static final int NEWS_LOADER_ID = 1;
    private String newsCategory;
    private boolean isNewsCategory;
    private View root;
    private MainActivity mainActivity;

    public static ArticlesFragment newInstance(){
        return new ArticlesFragment();
    }

    /**
     * Retrieves global MainActivity object
     * @param context from MainActivitys
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainActivity = (MainActivity) context;
        mainActivity.setArticlesFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.articles_list_view, container, false);

        // Init loader manager
        loaderManager = mainActivity.getSupportLoaderManager();

        // Default news search result
        this.chooseNewCategory("us-politics/us-politics", false);

        return root;
    }


    /**
     * Retrieves data fromm chosen category and displays result
     * @param newsCategory global variable; users chosen category from CategoriesFragment; used to fetch JSON
     */
    public void chooseNewCategory(String newsCategory, boolean isNewsCategory){
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

    private void updateUi(final ArrayList<News> articles){
        // List View adapter
        ArticlesAdapter adapter = new ArticlesAdapter(mainActivity, R.layout.news_list_item, articles);

        // Set adapter to List View
        ListView newsListView = root.findViewById(R.id.news_items_list_view);
        newsListView.setAdapter(adapter);

        // Set empty ListView message
        TextView emptyListView = root.findViewById(R.id.no_articles_found);
        emptyListView.setVisibility(View.VISIBLE);
        newsListView.setEmptyView(emptyListView);

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
        return new ArticlesLoader(mainActivity, newsCategory, isNewsCategory);
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
