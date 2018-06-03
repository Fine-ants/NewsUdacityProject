package com.jordanhuus.www.newsudacityproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 5/19/2018.
 */

public class CategoriesFragment extends Fragment {

    private MainActivity mainActivity;
    private String chosenCategoryTag;

    public static CategoriesFragment newInstance(){
        return new CategoriesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.categories, container, false);

        // Set onClickListener for Search button
        Button searchNewsKeyword = root.findViewById(R.id.search_button);
        searchNewsKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // @param view represents the button object; find parent view in order to retrieve EditText object
                View root = (View) view.getParent();
                EditText keywordSearch = root.findViewById(R.id.search_news_keyword);
                String keyword = keywordSearch.getText().toString();

                // Confirm device connectivity
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getActiveNetworkInfo()==null || !connectivityManager.getActiveNetworkInfo().isConnected()){
                    return;
                }

                //
                if(!keyword.isEmpty()){
                    mainActivity.clickCategory("Articles", keyword, false);
                }
            }
        });

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(getString(R.string.category_tech_tag),getString(R.string.category_tech),R.drawable.baseline_computer_white_18dp));
        categories.add(new Category(getString(R.string.category_science_tag),getString(R.string.category_science),0));
        categories.add(new Category(getString(R.string.category_health_tag),getString(R.string.category_health),0));
        categories.add(new Category(getString(R.string.category_weather_tag),getString(R.string.category_weather),R.drawable.baseline_cloud_white_18dp));
        categories.add(new Category(getString(R.string.category_business_tag),getString(R.string.category_business),0));
        categories.add(new Category(getString(R.string.category_politics_tag),getString(R.string.category_politics),R.drawable.baseline_business_white_18dp));
        categories.add(new Category(getString(R.string.category_sports_tag),getString(R.string.category_sports),R.drawable.baseline_directions_bike_white_18dp));
        categories.add(new Category(getString(R.string.category_world_tag),getString(R.string.category_world),0));
        categories.add(new Category(getString(R.string.category_entertainment_tag),getString(R.string.category_entertainment),0));

        // Setup Categories ListView
        CategoriesAdapter adapter = new CategoriesAdapter(getContext(), R.layout.category_list_item, categories);
        ListView categoriesList = root.findViewById(R.id.categories_list_view);
        categoriesList.setAdapter(adapter);

        return root;
    }


    private class CustomOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            chosenCategoryTag = view.getTag().toString();
            Button button = (Button) view;
            String chosenCategoryTitle = button.getText().toString();


            // Check for network connectivity
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getActiveNetworkInfo()==null || !connectivityManager.getActiveNetworkInfo().isConnected()){

                // Toast - No connection found
                Toast.makeText(mainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass the chosen news category to MainPageFragment
            mainActivity.clickCategory(chosenCategoryTitle, chosenCategoryTag, true);
        }
    }

    // Inner Interface for parent activity (MainActivity)
    public interface OnCategoryClickListener{
        public void clickCategory(String buttonTitle, String categoryName, boolean isNewsCategory);
    }


    // Retrieve and store parent activity (MainActivity)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mainActivity = (MainActivity) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}

