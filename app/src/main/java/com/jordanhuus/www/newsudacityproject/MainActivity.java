package com.jordanhuus.www.newsudacityproject;

import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{
    private static final int NEWS_LOADER_ID = 1;
    private String newsCategory;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_pager);

        // Init ViewPager adapter
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());

        // Set ViewPager adapter
        ViewPager viewPager = findViewById(R.id.main_view_pager);
        viewPager.setAdapter(adapter);
    }
}

