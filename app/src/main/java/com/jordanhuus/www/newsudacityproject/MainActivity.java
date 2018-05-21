package com.jordanhuus.www.newsudacityproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CategoriesFragment.OnCategoryClickListener{
    private MainPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_pager);

        // Init ViewPager adapter
        adapter = new MainPagerAdapter(getSupportFragmentManager());

        // Set ViewPager adapter
        ViewPager viewPager = findViewById(R.id.main_view_pager);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void clickCategory(String categoryName) {
        Fragment currentDisplayFragment = adapter.getItem(0);
        try {
            MainPageFragment mainPageFragment = (MainPageFragment) currentDisplayFragment;
            mainPageFragment.chooseNewCategory(categoryName);
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }
}

