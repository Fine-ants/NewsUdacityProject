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
import com.jordanhuus.www.newsudacityproject.Articles.Presenter.ArticlesPresenter;
import com.jordanhuus.www.newsudacityproject.Articles.View.ArticlesView;
import com.jordanhuus.www.newsudacityproject.MainActivity;
import com.jordanhuus.www.newsudacityproject.R;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 5/19/2018.
 */

public class ArticlesFragment extends Fragment implements ArticlesView{
    private MainActivity mainActivity;
    private ArticlesPresenter presenter;
    private View root;

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

        // Ensure ArticlesPresenter init
        if(presenter==null){
            presenter = new ArticlesPresenter(this, mainActivity);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.articles_list_view, container, false);

        // Inflate Articles view
        presenter.inflateArticlesView(root);

        return root;
    }


    /**
     * Retrieves data fromm chosen category and displays result
     * @param newsCategory global variable; users chosen category from CategoriesFragment; used to fetch JSON
     */
    public void chooseNewCategory(String newsCategory, boolean isNewsCategory){
        presenter.chooseNewsCategory(newsCategory, isNewsCategory);
    }

    @Override
    public void updateUi(ArrayList<News> articles){
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
                News newsItem = (News) adapterView.getAdapter().getItem(i);
                String articleUrl = newsItem.getWebUrl();

                // Open Chrome to article URL
                Intent openUrl = new Intent(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(articleUrl));
                startActivity(openUrl);
            }
        });
    }
}
