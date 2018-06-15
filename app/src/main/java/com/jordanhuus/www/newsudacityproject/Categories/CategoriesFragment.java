package com.jordanhuus.www.newsudacityproject.Categories;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jordanhuus.www.newsudacityproject.Categories.Model.Category;
import com.jordanhuus.www.newsudacityproject.Categories.Presenter.CategoriesPresenter;
import com.jordanhuus.www.newsudacityproject.MainActivity;
import com.jordanhuus.www.newsudacityproject.R;

import java.util.ArrayList;

/**
 * Created by jordanhuus on 5/19/2018.
 */

public class CategoriesFragment extends Fragment {

    private MainActivity mainActivity;
    private String chosenCategoryTag;
    private CategoriesPresenter presenter;

    public static CategoriesFragment newInstance(){
        return new CategoriesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.categories, container, false);

        presenter.inflateCategoriesView(root);

        return root;
    }


    private class CustomOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // Retrieve chosen category title and it's tag
            chosenCategoryTag = view.getTag().toString();
            Button button = (Button) view;
            String chosenCategoryTitle = button.getText().toString();

            // Display the chosen news category
            presenter.clickNewsCategory(chosenCategoryTitle, chosenCategoryTag);
        }
    }

    // Inner Interface for parent activity (MainActivity)
    public interface OnCategoryClickListener{
        public void clickCategory(String buttonTitle, String categoryName, boolean isNewsCategory);
    }


    /**
     * onAttach is used to retrieve MainActivity object
     * onAttach is called before onCreateView
     * @param context parent activity(MainActivity) context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            // Retrieve and store parent activity (MainActivity)
            mainActivity = (MainActivity) context;

            // Ensure CategoriesPresenter init
            if(presenter==null) {
                presenter = new CategoriesPresenter(this);
            }

            // Pass MainActivity to CategoriesPresenter
            presenter.setMainActivity(mainActivity);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}

