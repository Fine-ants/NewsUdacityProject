package com.jordanhuus.www.newsudacityproject;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;


public class MainActivity extends AppCompatActivity implements CategoriesFragment.OnCategoryClickListener{
    private MainPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MainPageFragment mainPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_pager);

        // Init ViewPager adapter
        adapter = new MainPagerAdapter(getSupportFragmentManager());

        // Retrieve
        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        // Set ViewPager adapter
        viewPager.setAdapter(adapter);

        // Setup Tab Layout
        tabLayout.setSelectedTabIndicatorColor(Color.rgb(155,220,62));
        tabLayout.setTabTextColors(Color.rgb(184,191,196), Color.rgb(184,191,196));
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void clickCategory(String buttonTitle, String categoryName, boolean isNewsCategory) {
        // Switch ViewPager to display MainPageFragment
        viewPager.setCurrentItem(0);

        // TabLayout title
        tabLayout.getTabAt(0).setText(buttonTitle);


        // Pass data to MainPageFragment
        if(mainPageFragment!=null){
            mainPageFragment.chooseNewCategory(categoryName, isNewsCategory);
        }
    }

    public void setMainPageFragment(MainPageFragment mainPageFragment) {
        this.mainPageFragment = mainPageFragment;
    }
}

