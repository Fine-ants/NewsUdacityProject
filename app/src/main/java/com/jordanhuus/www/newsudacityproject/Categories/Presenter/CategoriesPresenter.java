package com.jordanhuus.www.newsudacityproject.Categories.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.jordanhuus.www.newsudacityproject.Categories.CategoriesFragment;
import com.jordanhuus.www.newsudacityproject.MainActivity;

/**
 * Created by jordanhuus on 6/15/2018.
 */

public class CategoriesPresenter {
    private CategoriesFragment categoriesFragment;
    private MainActivity mainActivity;

    public CategoriesPresenter(CategoriesFragment categoriesFragment) {
        this.categoriesFragment = categoriesFragment;
    }

    public void clickNewsCategory(String chosenCategoryTitle, String chosenCategoryTag){

        // Check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo()==null || !connectivityManager.getActiveNetworkInfo().isConnected()){

            // Toast - No connection found
            Toast.makeText(mainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass the chosen news category to ArticlesFragment
        mainActivity.clickCategory(chosenCategoryTitle, chosenCategoryTag, true);
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
