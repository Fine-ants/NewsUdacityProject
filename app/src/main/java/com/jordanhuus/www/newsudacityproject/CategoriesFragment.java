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

        // Set onClickListener
        Button technology = root.findViewById(R.id.technology_button);
        Button business = root.findViewById(R.id.business_button);
        Button politics = root.findViewById(R.id.politics_button);
        Button local = root.findViewById(R.id.local_button);
        Button world = root.findViewById(R.id.world_button);
        Button usa = root.findViewById(R.id.usa_button);
        technology.setOnClickListener(new CustomOnclickListener());
        business.setOnClickListener(new CustomOnclickListener());
        politics.setOnClickListener(new CustomOnclickListener());
        local.setOnClickListener(new CustomOnclickListener());
        world.setOnClickListener(new CustomOnclickListener());
        usa.setOnClickListener(new CustomOnclickListener());

        return root;
    }

    private class CustomOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            chosenCategoryTag = view.getTag().toString();
            Log.i("debugtag", chosenCategoryTag);

            // Check for network connectivity
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getActiveNetworkInfo()==null || !connectivityManager.getActiveNetworkInfo().isConnected()){

                // Toast - No connection found
                Toast.makeText(mainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return;
            }

            mainActivity.clickCategory(chosenCategoryTag);
        }
    }

    // Inner Interface for parent activity (MainActivity)
    public interface OnCategoryClickListener{
        public void clickCategory(String categoryName);
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

