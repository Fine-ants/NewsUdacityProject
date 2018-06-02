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
import android.widget.ProgressBar;
import android.widget.Toast;

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
                    mainActivity.clickCategory(keyword, false);
                }
            }
        });


        // Buttons
        Button weather = root.findViewById(R.id.weather_button);
        Button technology = root.findViewById(R.id.technology_button);
        Button business = root.findViewById(R.id.business_button);
        Button politics = root.findViewById(R.id.politics_button);
        Button world = root.findViewById(R.id.world_button);
        Button sports = root.findViewById(R.id.sports_button);
        Button science = root.findViewById(R.id.science_button);
        Button health = root.findViewById(R.id.health_button);
        Button entertainment = root.findViewById(R.id.entertainment_button);

        // Set news categories onClickListeners
        View.OnClickListener categoryOnClickListener = new CustomOnclickListener();
        technology.setOnClickListener(categoryOnClickListener);
        business.setOnClickListener(categoryOnClickListener);
        politics.setOnClickListener(categoryOnClickListener);
        entertainment.setOnClickListener(categoryOnClickListener);
        world.setOnClickListener(categoryOnClickListener);
        sports.setOnClickListener(categoryOnClickListener);
        science.setOnClickListener(categoryOnClickListener);
        health.setOnClickListener(categoryOnClickListener);
        weather.setOnClickListener(categoryOnClickListener);

        return root;
    }


    private class CustomOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            chosenCategoryTag = view.getTag().toString();

            // Check for network connectivity
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getActiveNetworkInfo()==null || !connectivityManager.getActiveNetworkInfo().isConnected()){

                // Toast - No connection found
                Toast.makeText(mainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass the chosen news category to MainPageFragment
            mainActivity.clickCategory(chosenCategoryTag, true);
        }
    }

    // Inner Interface for parent activity (MainActivity)
    public interface OnCategoryClickListener{
        public void clickCategory(String categoryName, boolean isNewsCategory);
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

