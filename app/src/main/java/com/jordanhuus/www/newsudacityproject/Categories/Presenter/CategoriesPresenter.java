package com.jordanhuus.www.newsudacityproject.Categories.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jordanhuus.www.newsudacityproject.Categories.CategoriesAdapter;
import com.jordanhuus.www.newsudacityproject.Categories.CategoriesFragment;
import com.jordanhuus.www.newsudacityproject.Categories.Model.Category;
import com.jordanhuus.www.newsudacityproject.MainActivity;
import com.jordanhuus.www.newsudacityproject.R;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 6/15/2018.
 */

public class CategoriesPresenter {
    private CategoriesFragment categoriesFragment;
    private MainActivity mainActivity;
    private ArrayList<Category> categories;

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

    public View inflateCategoriesView(View rootView){
        // Set onClickListener for Search button
        Button searchNewsKeyword = rootView.findViewById(R.id.search_button);
        searchNewsKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // @param view represents the button object; find parent view in order to retrieve EditText object
                View root = (View) view.getParent();
                EditText keywordSearch = root.findViewById(R.id.search_news_keyword);
                String keyword = keywordSearch.getText().toString();

                // Confirm device connectivity
                ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getActiveNetworkInfo()==null || !connectivityManager.getActiveNetworkInfo().isConnected()){
                    return;
                }

                //
                if(!keyword.isEmpty()){
                    mainActivity.clickCategory("Articles", keyword, false);
                }
            }
        });

        categories = new ArrayList<>();
        categories.add(new Category(mainActivity.getString(R.string.category_tech_tag),mainActivity.getString(R.string.category_tech),R.drawable.baseline_memory_white_18dp));
        categories.add(new Category(mainActivity.getString(R.string.category_science_tag),mainActivity.getString(R.string.category_science),R.drawable.science_white_icon));
        categories.add(new Category(mainActivity.getString(R.string.category_health_tag),mainActivity.getString(R.string.category_health),R.drawable.baseline_fitness_center_white_18dp));
        categories.add(new Category(mainActivity.getString(R.string.category_weather_tag),mainActivity.getString(R.string.category_weather),R.drawable.baseline_cloud_white_18dp));
        categories.add(new Category(mainActivity.getString(R.string.category_business_tag),mainActivity.getString(R.string.category_business),R.drawable.baseline_business_white_18dp));
        categories.add(new Category(mainActivity.getString(R.string.category_politics_tag),mainActivity.getString(R.string.category_politics),R.drawable.baseline_account_balance_white_18dp));
        categories.add(new Category(mainActivity.getString(R.string.category_sports_tag),mainActivity.getString(R.string.category_sports),R.drawable.baseline_directions_bike_white_18dp));
        categories.add(new Category(mainActivity.getString(R.string.category_world_tag),mainActivity.getString(R.string.category_world),R.drawable.baseline_public_white_18dp));
        categories.add(new Category(mainActivity.getString(R.string.category_entertainment_tag),mainActivity.getString(R.string.category_entertainment),R.drawable.baseline_local_movies_white_18dp));

        // Setup Categories ListView
        CategoriesAdapter adapter = new CategoriesAdapter(mainActivity, R.layout.category_list_item, categories);
        ListView categoriesList = rootView.findViewById(R.id.categories_list_view);
        categoriesList.setAdapter(adapter);
        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Retrieve the chosen category title, api category tag
                String chosenCategoryTitle = categories.get(i).getCategoryTitle();
                String chosenCategoryTag = categories.get(i).getCategoryApiTag();

                // Pass parameters to ArticlesFragment
                mainActivity.clickCategory(chosenCategoryTitle, chosenCategoryTag, true);
            }
        });

        return rootView;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
